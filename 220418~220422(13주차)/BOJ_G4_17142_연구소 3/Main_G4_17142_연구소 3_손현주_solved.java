package gold;

import java.io.*;
import java.util.*;

public class bj_17142_G4 {

	static int N, M, map[][];
	static int answer = Integer.MAX_VALUE;
	static int virusCnt;

	static ArrayList<int[]> viruses = new ArrayList<int[]>();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 2) {
					viruses.add(new int[] { i, j });
				}
			}
		}
		virusCnt = viruses.size();
		// M 만큼 뽑는다.
		comb(0, 0, new boolean[virusCnt], new int[M]); // idx, cnt, isVisited, select
		// 출력한다.
		System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
	}

	static void comb(int start, int cnt, boolean[] isVisited, int[] select) {
		if (cnt == M) { // M 만큼 뽑았으면
			spreadVirus(select);
			return;
		}

		for (int i = start; i < virusCnt; ++i) {
			if (isVisited[i])
				continue;
			isVisited[i] = true;
			select[cnt] = i;
			comb(i + 1, cnt + 1, isVisited, select);
			isVisited[i] = false;
		}
	}

	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };
	static class Virus {
		int x, y;
		int time;
		Virus(int x, int y, int time){
			this.x=x;
			this.y=y;
			this.time=time;
		}
	}
	static void spreadVirus(int[] select) {
		int[][] tmp = copy(); // 복사해서 써야함 원본 보장
		int[][] dist = new int[N][N]; // dist는 사용

		boolean[][] isVisited = new boolean[N][N];// 방문 체크 배열
		Queue<int[]> bfs = new LinkedList<>(); // bfs용 큐

		for (int i = 0; i < M; ++i) { // 뽑은 애들을 active 상태로
			int[] now = viruses.get(select[i]);
			isVisited[now[0]][now[1]] = true; // check
			tmp[now[0]][now[1]] = 3; // active
			bfs.add(now);
		}
		// 퍼뜨린다.
		int time = 0;
		while (!bfs.isEmpty()) {
			int[] current = bfs.poll();

			for (int d = 0; d < 4; ++d) {
				int nx = current[0] + dir[0][d];
				int ny = current[1] + dir[1][d];
				if (!isBoundary(nx, ny)) // 범위 밖이면 패스
					continue;
				if(isVisited[nx][ny]) continue; // 이미 방문했으면 패스
				if(tmp[nx][ny]==0 || tmp[nx][ny]==2) { // 0인 곳으로만 이동 가능하다.
					isVisited[nx][ny]=true;
					dist[nx][ny]=dist[current[0]][current[1]]+1; // time 갱신
					if(tmp[nx][ny]==0) tmp[nx][ny]=3;//바이러스
					bfs.add(new int[] {nx,ny});
				}
			}
		}
		for(int i=0;i<N;++i) {
			for(int j=0;j<N;++j) {
				if(tmp[i][j]!=2) time = Math.max(time, dist[i][j]);
			}
		}
		// 모두다 퍼뜨려졌는지 체크
		if(isAllSpread(tmp)) answer = Math.min(answer, time); // 갱신
	}

	static boolean isAllSpread(int[][] tmp) {
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (tmp[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}

	static int[][] copy() {
		int[][] answer = new int[N][N];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				answer[i][j] = map[i][j];
			}
		}
		return answer;
	}
}
