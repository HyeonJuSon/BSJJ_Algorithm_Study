import java.io.*;
import java.util.*;

public class Main {

	static int R, C, K, wallCnt;
	static int[][] map;
	static boolean[][][] wallMap;

	static class Hit {
		int x, y, dir;

		Hit(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}

	static class Wind {
		int x, y, dir, degree;

		Wind(int x, int y, int dir, int degree) {
			this.x = x;
			this.y = y;
			this.dir = dir;
			this.degree = degree;
		}
	}

	static ArrayList<Hit> hitter = new ArrayList<>(); // 온풍기의 위치
	static ArrayList<int[]> checkZone = new ArrayList<>(); // 최종적으로 체크해야할 위치
	static int[][][] spreadDir = { { { -1, 0, 1 }, { 1, 1, 1 } }, // 우
			{ { -1, 0, 1 }, { -1, -1, -1 } }, // 좌
			{ { -1, -1, -1 }, { -1, 0, 1 } }, // 상
			{ { 1, 1, 1 }, { -1, 0, 1 } } // 하
	}; // 온풍기가 퍼지는 방향
	static final int East = 0, West = 1, North = 2, South = 3;
	static int[][] dir = { { 0, 0, -1, 1 }, { 1, -1, 0, 0 } }; // 방향별 출발지점

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		// R, C, K 저장
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		// 맵 저장
		map = new int[R][C]; // 온도 체크용 배열
		wallMap = new boolean[R][C][4]; // 벽 체크용 배열
		for (int i = 0; i < R; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < C; ++j) {
				int val = Integer.parseInt(st.nextToken());
				if(1<=val && val <=4) hitter.add(new Hit(i, j, val - 1)); // 온풍기 위치 저장
				else if (val == 5) checkZone.add(new int[] { i, j }); // 조사해야할 위치 저장
			}
		}
		// 벽 위치 저장
		wallCnt = Integer.parseInt(br.readLine());
		for (int i = 0; i < wallCnt; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int t = Integer.parseInt(st.nextToken());
			if (t == 0) {
				wallMap[x][y][North] = true;
				wallMap[x - 1][y][South] = true;
			} else if (t == 1) {
				wallMap[x][y][East] = true;
				wallMap[x][y + 1][West] = true;
			}
		}
		
		// 온풍기 시작
		int chocolate = 0;
		while (true) {
			// 1. 온풍기 1회
			hitting();
			// 2. 온도조절
			generating();
			// 3. 바깥쪽 온도 1감소
			minusBoundary();
			// 4. 초콜릿
			++chocolate;
			// 4-1. 초콜릿이 101이상?
			if(chocolate > 100) break;
			// 5. 특정 위치 온도 K이상?
			if (isOver()) break;
		}
		System.out.println(chocolate);
	}

	// 1. 온풍기 1회
	static void hitting() {
		for (int i = 0; i < hitter.size(); ++i) {
			int direction = hitter.get(i).dir; // 히터가 바람을 쏘는 방향
			int x = hitter.get(i).x + dir[0][direction]; // 방향별 출발점 갱신
			int y = hitter.get(i).y + dir[1][direction];
			
			boolean[][] isVisited = new boolean[R][C];
			isVisited[x][y] = true;
			
			Queue<Wind> q = new LinkedList<>(); // bfs
			q.add(new Wind(x, y, direction, 5));
			map[x][y] += 5; // 시작 지점은 5
			
			while (!q.isEmpty()) { 
				Wind now = q.poll();
				if(now.degree==0) continue; // 온도는 1~5사이임
				for (int d = 0; d < 3; ++d) { // 좌, 중, 우
					// 퍼져나갈 위치
					int nx = now.x + spreadDir[now.dir][0][d];
					int ny = now.y + spreadDir[now.dir][1][d];
					// 위치가 범위 밖이거나 이미 방문이 되었나?
					if (!isBoundary(nx,ny) || isVisited[nx][ny]) continue;
					// 이동할 위치에 벽이 있는가?
					if (isWall(now.x, now.y, nx, ny, now.dir, d)) continue;
					map[nx][ny] += now.degree-1; // 바람 이동(온풍기 2대 이상일 수 있으므로 더해준다)
					isVisited[nx][ny] = true; // 바람이 중복으로 이동되지 않도록 방문처리
					q.add(new Wind(nx, ny, now.dir,now.degree-1)); // 큐에 추가
				}
			}
		}
	}

	static boolean isBoundary(int x, int y) { // 범위 검사 메소드
		return 0 <= x && x < R && 0 <= y && y < C;
	}

	static boolean isWall(int x, int y, int nx, int ny, int d, int idx) { // 벽인지 아닌지 검사하는 메소드
		if (idx == 0) { // left
			if (d == East) {
				if (wallMap[x][y][North] || wallMap[nx][ny][West]) return true;
			} else if (d == West) {
				if (wallMap[x][y][North] || wallMap[nx][ny][East]) return true;
			} else if (d == North) {
				if (wallMap[x][y][West] || wallMap[nx][ny][South]) return true;
			} else {
				if (wallMap[x][y][West] || wallMap[nx][ny][North]) return true;
			}
		} else if (idx == 1) { // mid
			if (wallMap[x][y][d]) return true;
		} else if (idx == 2) { // right
			if (d == East) {
				if (wallMap[x][y][South] || wallMap[nx][ny][West]) return true;
			} else if (d == West) {
				if (wallMap[x][y][South] || wallMap[nx][ny][East]) return true;
			} else if (d == North) { 
				if (wallMap[x][y][East] || wallMap[nx][ny][South]) return true;
			} else {
				if (wallMap[x][y][East] || wallMap[nx][ny][North]) return true;
			}
		}
		return false;
	}

	// 2. 온도 조절
	static void generating() {
		int[][] result = new int[R][C];
		boolean[][] isVisited =new boolean[R][C];
		for (int i = 0; i < R; ++i) {
			for (int j = 0; j < C; ++j) {
				int val = map[i][j];
				isVisited[i][j] = true; // 내 자신의 값은 한 번만 갱신되어야 함
				for (int d = 0; d < 4; ++d) { // 4방향 
					// 이동할 위치로 갱신
					int nx = i + dir[0][d];
					int ny = j + dir[1][d];
					// 범위 밖 or 벽 or 방문이면 
					if (!isBoundary(nx,ny) || wallMap[i][j][d] || isVisited[nx][ny]) continue;
					int newVal = 0;
					if (val > map[nx][ny]) {
						newVal = (val-map[nx][ny])/4;
						result[i][j] -= newVal;
						result[nx][ny] += newVal;
					} else {
						newVal = (map[nx][ny]-val)/4;
						result[i][j] += newVal;
						result[nx][ny] -= newVal;
					}
				}
			}
		}
		// 동시에 갱신해주어야 함
		for (int i = 0; i < R; ++i) {
			for (int j = 0; j < C; ++j) {
					map[i][j] += result[i][j];
			}
		}
	}

	// 3. 바깥쪽 온도 1감소
	static void minusBoundary() {
		boolean[][] isVisited = new boolean[R][C];
		for (int i = 0; i < R; ++i) {
			for (int j = 0; j < C; ++j) {
				// 이미 방문 or map 값이 0 이면
				if (isVisited[i][j]||map[i][j]==0) continue;
				// 바깥쪽이라면
				if (i == 0 || i == R - 1 || j == 0 || j == C - 1) {
					isVisited[i][j] = true;
					--map[i][j];
				}
			}
		}
	}

	// 5. 전체 공간 온도 K이상?
	static boolean isOver() {
		boolean answer = true;
		for (int[] now : checkZone) { // 체크할 위치를 돌면서
			if (map[now[0]][now[1]] < K) { // K보다 작은게 한 개라도 있다면
				answer = false; // 바로 탈락
				break;
			}
		}
		return answer;
	}
}
