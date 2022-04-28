import java.io.*;
import java.util.*;

public class bj_23290_G1 {

	static int M, S, N, maxSum;
	static int[][] smell;
	static ArrayList<Fish> map[][];
	static int[][] sharkDir = { { -1, 0, 1, 0 }, { 0, -1, 0, 1 } }; // 상좌하우
	static int[][] fishDir = { { 0, -1, -1, -1, 0, 1, 1, 1 }, { -1, -1, 0, 1, 1, 1, 0, -1 } };// ←, ↖, ↑, ↗, →, ↘, ↓, ↙
	static ArrayList<Fish> storeFish;
	static Fish shark;
	static Queue<int[]> sharkMove = new LinkedList<>();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		N = 4;
		smell = new int[N][N];
		map = new ArrayList[N][N];
		for (int i = 0; i < N; ++i) {
			Arrays.fill(smell[i], -1);
			for (int j = 0; j < N; ++j)
				map[i][j] = new ArrayList<>();
		}
		// M만큼 물고기 정보를 담는다.
		for (int i = 0; i < M; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;
			map[x][y].add(new Fish(x, y, d));
		}
		// 상어의 위치를 받는다.
		st = new StringTokenizer(br.readLine(), " ");
		int sx = Integer.parseInt(st.nextToken()) - 1;
		int sy = Integer.parseInt(st.nextToken()) - 1;
		shark = new Fish(sx, sy, -1);
		// S만큼 연습한다.
		for (int s = 0; s < S; ++s) {
			maxSum = -1; // 상어가 가장 많이 먹을 수 있는 물고기의 수
			// 1. 예비 복제
			beforeCopy();
			// 2. 모든 물고기가 1칸 이동
			moveFishes();
			// 3. 상어가 연속 3칸으로 이동
			moveShark(s);
			// 4. s-2 번째 냄새 제거
			deleteSmell(s);
			// 5. 실제 복제
			copy();
		}
		// 격자의 물고기 수 .
		int answer = 0;
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				answer += map[i][j].size();
			}
		}
		System.out.println(answer);
	}

	static void deleteSmell(int s) {
		if (s - 2 < 0)
			return;
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (smell[i][j] != -1 && smell[i][j] == s - 2) {
					smell[i][j] = -1;// 지워준다.
				}
			}
		}
	}

	static void sharkDfs(int x, int y, int depth, Queue<int[]> log) {
		if (depth == 3) {
			// 물고기 먹을 수 있는거
			Queue<int[]> l = new LinkedList<>(log);
			boolean[][] eat = new boolean[N][N];
			int sum = 0;
			while (!l.isEmpty()) {
				int[] now = l.poll();
				if (eat[now[0]][now[1]])
					continue;
				eat[now[0]][now[1]] = true;
				sum += map[now[0]][now[1]].size();
			}
			// 비교
			if (maxSum < sum) {
				// 갱신
				maxSum = sum;
				sharkMove = new LinkedList<>(log);
			}
			return;
		}
		for (int d = 0; d < 4; ++d) {
			int nx = x + sharkDir[0][d];
			int ny = y + sharkDir[1][d];
			if (!isBoundary(nx, ny))
				continue;
			Queue<int[]> tmp = new LinkedList<>(log);
			tmp.add(new int[] { nx, ny });
			sharkDfs(nx, ny, depth + 1, tmp);
		}
	}

	static void moveShark(int s) {
		sharkDfs(shark.x, shark.y, 0, new LinkedList<>());
		// 실제로 상어를 움직인다.
		while (!sharkMove.isEmpty()) {
			int[] now = sharkMove.poll();
			if (map[now[0]][now[1]].size() > 0) { // 이동 칸에 물고기가 있으면 모두 제외
				map[now[0]][now[1]].clear(); // 물고기 지움
				smell[now[0]][now[1]] = s;// 냄새를 남김
			}
			if (sharkMove.isEmpty()) {
				shark.x = now[0];
				shark.y = now[1];
			}
		}
	}

	static void moveFishes() {
		Queue<Fish> moveList = new LinkedList<>();
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (map[i][j].size() == 0)
					continue; // 물고기 없으면 패스한다.
				for (int k = 0; k < map[i][j].size(); ++k) {
					Fish f = map[i][j].get(k);
					int nx = f.x + fishDir[0][f.dir];
					int ny = f.y + fishDir[1][f.dir];
					// 이중 하나라도 조건에 걸리면 반시계 회전시킴
					int cnt = 1;
					if (!isBoundary(nx, ny) || smell[nx][ny] != -1 || (shark.x == nx && shark.y == ny)) {
						int nDir = f.dir;
						while (true) {
							nDir = (nDir - 1 + 8) % 8;// 반시계회전한다.
							nx = f.x + fishDir[0][nDir];
							ny = f.y + fishDir[1][nDir];
							if (isBoundary(nx, ny) && smell[nx][ny] == -1 && (nx != shark.x || ny != shark.y)) {
								f.dir = nDir;
								break;
							}
							if (++cnt == 8)
								break;
						}
					}
					// cnt 가 8이면 갱신하지 않는다.
					if (cnt == 8)
						continue;
					// 갱신한다.
					moveList.add(new Fish(nx, ny, f.dir));// 새위치에 추가하고
					map[f.x][f.y].remove(k);// 기존 위치에서는 지워준다.
					--k; // 리스트 사이즈가 줄어들기 때문에 현재 인덱스를 줄인다.
				}
			}
		}
		// 물고기를 이동시킨다.
		while (!moveList.isEmpty()) {
			Fish f = moveList.poll();
			map[f.x][f.y].add(new Fish(f.x, f.y, f.dir));
		}
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}

	static void beforeCopy() {
		storeFish = new ArrayList<>();
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (map[i][j].size() > 0) {
					for (Fish f : map[i][j]) {
						storeFish.add(new Fish(i, j, f.dir));
					}
				}
			}
		}
	}

	static void copy() {
		// 저장해두었던 물고기들을 복제한다.
		for (Fish f : storeFish) {
			map[f.x][f.y].add(new Fish(f.x, f.y, f.dir));
		}
	}

	// class =================================================================
	
	static class Fish {
		int x, y;
		int dir;

		public Fish(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}
}
