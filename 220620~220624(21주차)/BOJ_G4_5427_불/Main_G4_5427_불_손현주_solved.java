package gold;

import java.io.*;
import java.util.*;

public class BJ_5427_G4 {

	static int W, H;
	static char[][] map;
	static Queue<int[]> fires;
	static Queue<Node> sanguen;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for (int t = 0; t < T; ++t) {
			// 01. Input
			st = new StringTokenizer(br.readLine(), " ");
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			map = new char[H][W];
			fires = new LinkedList<>();
			sanguen = new LinkedList<>();
			for (int i = 0; i < H; ++i) {
				String now = br.readLine();
				for (int j = 0; j < W; ++j) {
					char ch = now.charAt(j);
					map[i][j] = ch;
					if (ch == '@') {
						sanguen.add(new Node(i, j, 0));
					} else if (ch == '*') {
						fires.add(new int[] { i, j });
					}
				}
			}
			// 02. Find
			System.out.println(bfs());
		}
	}

	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < H && 0 <= y && y < W;
	}

	static String bfs() {
		int answer = -1;
		root: while (true) {
			// fire
			int fireSize = fires.size();
			for (int i = 0; i < fireSize; ++i) {
				int[] now = fires.poll();
				for (int d = 0; d < 4; ++d) {
					int nx = now[0] + dir[0][d];
					int ny = now[1] + dir[1][d];
					if (isBoundary(nx, ny) && (map[nx][ny] == '.' || map[nx][ny] == '@')) {
						map[nx][ny] = '*';
						fires.add(new int[] { nx, ny });
					}
				}
			}
			// sanguen
			int sangSize = sanguen.size();
			for (int i = 0; i < sangSize; ++i) {
				Node now = sanguen.poll();
				for (int d = 0; d < 4; ++d) {
					int nx = now.x + dir[0][d];
					int ny = now.y + dir[1][d];
					if (isBoundary(nx, ny)) {
						if (map[nx][ny] == '.') {
							map[nx][ny]='@';
							sanguen.add(new Node(nx, ny, now.dist + 1));
						}
					} else {
						answer = now.dist + 1;
						break root;
					}
				}
			}
			if (sanguen.isEmpty())
				break;
		}

		return answer == -1 ? "IMPOSSIBLE" : Integer.toString(answer);
	}

	static class Node {
		int x, y, dist;

		public Node(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

	}
}
