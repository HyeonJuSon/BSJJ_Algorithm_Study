package gold;

import java.io.*;
import java.util.*;

public class BJ_14503_G5 {
	// 북 동 남 서
	static int[][] dir = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };
	static int N, M;

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}

	static boolean[][] isVisited;
	static int[][] map;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		st = new StringTokenizer(br.readLine(), " ");
		int[] machine = { Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()) };
		int machineDir = Integer.parseInt(st.nextToken());
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int answer = 0; // 청소 개수
		isVisited = new boolean[N][M];
		boolean isFirstStep = true;
		Root: while (true) {
			if (isFirstStep) { // 1번 프로세스
				// 1. 현재 칸 청소
				if (!isVisited[machine[0]][machine[1]]) {
					++answer;
					isVisited[machine[0]][machine[1]] = true;
				}
				isFirstStep = false;
			} else { // 2번 프로세스
				int nDir = getLeftDir(machineDir);
				int nx = machine[0] + dir[0][nDir];
				int ny = machine[1] + dir[1][nDir];
				if (isBoundary(nx, ny) && !isVisited[nx][ny] && map[nx][ny] == 0) {
					machineDir = nDir;
					machine[0] = nx;
					machine[1] = ny;
					isFirstStep = true;
				} else if (!isBoundary(nx, ny)) {
					machineDir = nDir;
					isFirstStep = false;
				} else if (isBoundary(nx, ny) && (isVisited[nx][ny] || map[nx][ny] == 1)) {
					if (isAllClean(machine, machineDir)) {
						nDir = getBackDir(machineDir);
						nx = machine[0] + dir[0][nDir];
						ny = machine[1] + dir[1][nDir];
						if (map[nx][ny] == 0) {
							machine[0] = nx;
							machine[1] = ny;
							isFirstStep = false;
						} else {
							break Root;
						}
					} else {
						machineDir = nDir;
						isFirstStep = false;
					}
				}
			}
		}
		System.out.println(answer);
	}

	static int getBackDir(int mDir) {
		int nDir = -1;
		nDir = mDir - 2;
		if (nDir < 0)
			nDir += 4;
		return nDir;
	}

	static boolean isAllClean(int[] pos, int mDir) {
		boolean clean = true;
		for (int d = 0; d < 4; ++d) {
			int nx = pos[0] + dir[0][d];
			int ny = pos[1] + dir[1][d];
			if (isBoundary(nx, ny)&&! (isVisited[nx][ny] || map[nx][ny] == 1)) {
				clean = false;
				break;
			}
		}
		return clean;
	}

	// 북 동 남 서
	static int getLeftDir(int nowDir) {
		int nDir = -1;
		nDir = nowDir - 1;
		if (nDir < 0)
			nDir = 3; // 북 -> 서
		return nDir;
	}
}
