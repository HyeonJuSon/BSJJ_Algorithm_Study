package gold;

import java.io.*;
import java.util.*;

public class BJ_3055_G4 {

	static int N, M;
	static char[][] map;
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };
	static int[] dochi;
	static final char beaver = 'D', water = '*', rock = 'X', empty = '.';
	static Queue<int[]> waters;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		// input
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new char[N][M];
		waters = new LinkedList<>();
		for (int i = 0; i < N; ++i) {
			String now = br.readLine();
			for (int j = 0; j < M; ++j) {
				map[i][j] = now.charAt(j);
				if (map[i][j] == 'S') {
					dochi = new int[] { i, j };
					map[i][j] = '.';
				} else if (map[i][j] == water) {
					waters.add(new int[] { i, j });
				}
			}
		}

		// process
		process();
	}

	static void process() {

		Queue<DC> bfs = new LinkedList<>();
		bfs.add(new DC(dochi[0], dochi[1], 0));
		boolean isPossible = false;
		boolean[][] isVisited = new boolean[N][M];
		while (true) {
			// 물을 넓힌다.
			water();
			int len = bfs.size();
			for (int i = 0; i < len; ++i) {
				DC now = bfs.poll();
				isVisited[now.x][now.y] = true;
				if (map[now.x][now.y] == beaver) {
					System.out.println(now.dist);
					isPossible = true;
					bfs.clear();
					break;
				}

				// 비버가 이동 가능한 위치를 담는다.
				for (int d = 0; d < 4; ++d) {
					int nx = now.x + dir[0][d];
					int ny = now.y + dir[1][d];
					if (isBoundary(nx, ny) && !isVisited[nx][ny] && isMovable(nx, ny)) {
						bfs.add(new DC(nx, ny, now.dist + 1));
						isVisited[nx][ny] = true;
						if (map[nx][ny] != beaver)
							map[nx][ny] = 'S';
					}
				}
			}

			if (bfs.isEmpty())
				break;
		}
		if (!isPossible)
			System.out.println("KAKTUS");
	}

	static void water() {
		boolean[][] isVisited = new boolean[N][M];
		Queue<int[]> result = new LinkedList<>();
		while (!waters.isEmpty()) {
			int[] now = waters.poll();
			map[now[0]][now[1]] = water;
			for (int d = 0; d < 4; ++d) {
				int nx = now[0] + dir[0][d];
				int ny = now[1] + dir[1][d];
				if (isBoundary(nx, ny) && !isVisited[nx][ny] && map[nx][ny] != rock && map[nx][ny] != beaver) {
					isVisited[nx][ny] = true;
					result.add(new int[] { nx, ny });
					map[nx][ny] = water;
				}
			}
		}
		waters = result;
	}

	static boolean isMovable(int x, int y) {
		return map[x][y] == empty || map[x][y] == beaver;
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}

	static class DC {
		int x, y;
		int dist;

		public DC(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

	}

}
