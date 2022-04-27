package gold;

import java.io.*;
import java.util.*;

public class bj_2146_G3 {

	static int N, min, map[][];

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		min = Integer.MAX_VALUE;
		for (int i = 0; i < N; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// 1. 섬끼리 묶어준다.
		makeLands();
		// 2. 다리를 놓아본다.
		makeBridge();
		// 3. 정답을 출력한다.
		System.out.println(min);
	}

	static void makeLands() {
		int idx = -1; // 섬의 번호 -1, -2, -3
		boolean[][] isVisited = new boolean[N][N];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (map[i][j] == 1) {
					isVisited[i][j] = true;
					map[i][j] = idx;
					bfs(i, j, idx--, isVisited);
				}
			}
		}
	}

	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	static void bfs(int i, int j, int idx, boolean[][] isVisited) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { i, j });
		while (!q.isEmpty()) {
			int[] now = q.poll();
			for (int d = 0; d < 4; ++d) {
				int nx = now[0] + dir[0][d];
				int ny = now[1] + dir[1][d];
				if (!isBoundary(nx, ny) || isVisited[nx][ny] || map[nx][ny] == 0)
					continue;
				isVisited[nx][ny] = true;
				map[nx][ny] = idx;
				q.add(new int[] { nx, ny });
			}
		}
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}

	static void makeBridge() {
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				int idx = map[i][j];
				if (idx < 0) {
					boolean[][] isVisited = new boolean[N][N];
					isVisited[i][j] = true;
					bridge(i, j, idx, isVisited);
				}
			}
		}
	}

	static class Node {
		int x, y, depth;

		Node(int x, int y, int depth) {
			this.x = x;
			this.y = y;
			this.depth = depth;
		}
	}

	static void bridge(int x, int y, int idx, boolean[][] isVisited) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(x, y, 0));
		while (!q.isEmpty()) {
			Node now = q.poll();
			for (int d = 0; d < 4; ++d) {
				int nx = now.x + dir[0][d];
				int ny = now.y + dir[1][d];
				if (!isBoundary(nx, ny) || isVisited[nx][ny] || map[nx][ny] == idx)
					continue;
				isVisited[nx][ny] = true;
				if (map[nx][ny] == 0) {
					q.add(new Node(nx, ny, now.depth + 1));
				} else {
					min = Math.min(now.depth, min);
				}
			}
		}
	}
}
