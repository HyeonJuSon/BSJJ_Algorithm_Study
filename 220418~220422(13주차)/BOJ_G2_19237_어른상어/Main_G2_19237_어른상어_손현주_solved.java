package gold;

import java.io.*;
import java.util.*;

public class bj_19237_G2 {

	static int N, M, K;
	static Smell[][] map;
	static TreeMap<Integer, Shark> sharks;
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		// init
		map = new Smell[N][N];
		sharks = new TreeMap<>();
		// map init
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; ++j) {
				int val = Integer.parseInt(st.nextToken());
				if (val > 0) {
					sharks.put(val, new Shark(val,i, j, -1)); // 상어 위치를 넣어주고
					map[i][j] = new Smell(val, K); // 상어의 냄새를 넣어준다.
				}
			}
		}
		// direction init
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= M; ++i) { // 상어의 최초 방향을 갱신해준다.
			int dir = Integer.parseInt(st.nextToken())-1;
			sharks.put(i, new Shark(i,sharks.get(i).x, sharks.get(i).y, dir));
		}
		// order init
		for (int i = 1; i <= M; ++i) { // 상어 마리수 만큼 상/하/좌/우 우선순위를 받아들인다.
			for (int x = 0; x < 4; ++x) { // 상 / 하 / 좌 / 우
				st = new StringTokenizer(br.readLine(), " ");
				for (int y = 0; y < 4; ++y) { // 0 / 1/ 2/ 3
					sharks.get(i).order[x][y] = Integer.parseInt(st.nextToken()) - 1;
				}
			}
		}
		// process
		int time = 0;
		while (true) {
			if (++time >1000)
				break; // 1000 초 초과시 되면 break;
			// 상어 이동
			Queue<Shark> move = new LinkedList<>();
			for (Integer idx : sharks.keySet()) {
				Shark now = sharks.get(idx); // 현재 상어를 가져온다.
				// 새로운 방향을 찾는다. (냄새가 없는 칸 부터 찾는다.)
				boolean isFind = false;
				for (int d = 0; d < 4; ++d) {
					int nDir = now.order[now.dir][d];// 현재 방향에서 우선순위가 높은순으로 불러온다.
					int nx = now.x + dir[0][nDir];
					int ny = now.y + dir[1][nDir];
					if (!isBoundary(nx, ny))
						continue;
					if (map[nx][ny] == null) { // 냄새가 없다면
						now.dir = nDir; // 방향을 바꿔준다.
						move.add(new Shark(now.num,nx,ny,now.dir)); // 동시 이동을 위해서 담아준다.
						isFind = true;
						break;
					}
				}
				if(isFind) continue; // 찾았으면 패스
				for(int d=0 ; d<4;++d) {
					int nDir = now.order[now.dir][d];
					int nx = now.x  +dir[0][nDir];
					int ny = now.y + dir[1][nDir];
					if(!isBoundary(nx,ny)) continue;
					if(map[nx][ny].num == now.num) { // 내 냄새라면
						now.dir=nDir;
						move.add(new Shark(now.num,nx,ny,now.dir));
						break;
					}
				}
			}
			// 냄새 감소
			minusSmell();
			// 상어 실제 이동
			moveShark(move);
			// 1번 상어만 남았는지?
			if(sharks.size()==1 && sharks.containsKey(1)) break;
		}
		System.out.println(time > 1000 ? -1 : time);
	}
	
	static void moveShark(Queue<Shark> move) {
		while(!move.isEmpty()) {
			Shark now = move.poll();
			if(map[now.x][now.y]==null || map[now.x][now.y].num == now.num) {
				// 위치 갱신
				sharks.get(now.num).x = now.x;
				sharks.get(now.num).y = now.y;
				// 냄새 새김
				map[now.x][now.y]= new Smell(now.num, K);
			}else {
				if(map[now.x][now.y].num > now.num) { // 내가 더 작은 번호라면 우선순위를 가진다.
					sharks.remove(map[now.x][now.y].num); // 해당 위치 상어를 지워준다.
					// 위치 갱신
					sharks.get(now.num).x = now.x;
					sharks.get(now.num).y = now.y;
					// 냄새 새김
					map[now.x][now.y]= new Smell(now.num, K);
				}else sharks.remove(now.num); // 상어를 지워준다.
			}
		}
	}
	
	static void minusSmell() {
		for(int i=0;i<N;++i) {
			for(int j=0;j<N;++j) {
				if(map[i][j]==null) continue;
				if(--map[i][j].cnt <= 0) {
					map[i][j] = null;
				}
			}
		}
	}
	
	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}

	// class ===================
	static class Shark {
		int num;
		int x, y;
		int dir;
		int[][] order = new int[4][4];

		public Shark(int num, int x, int y, int dir) {
			this.num = num;
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}

	static class Smell {
		int num;
		int cnt;

		public Smell(int num, int cnt) {
			this.num = num;
			this.cnt = cnt;
		}
	}
}
