package gold;

import java.io.*;
import java.util.*;

public class bj_23288_G3 {
	static int N, M, K, map[][];
	static int[][] dice = { { 0, 2, 0 }, { 4, 1, 3 }, { 0, 5, 0 }, { 0, 6, 0 } };
	static int[] nowPos = { 0, 0 }; // 현재 주사위의 위치
	static int nowDir = 0; // 현재 방향, 0,1,2,3 -> 동 남 서 북
	static int[][] dir = { { 0, 1, 0, -1 }, { 1, 0, -1, 0 } };// 동,남,서,북

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int answer = 0;
		// k번 이동 시킨다.
		for (int k = 0; k < K; ++k) {
			// 주사위 이동 방향으로 한 칸
			moveDice();
			// 연속으로 이동할 수 있는 칸의 개수
			answer += getScore(map[nowPos[0]][nowPos[1]]);
		}
		System.out.println(answer);
	}

	static int getScore(int B) {
		// 방문 체크용 배열
		boolean[][] isVisited = new boolean[N][M];
		Queue<int[]> bfs = new LinkedList<>();
		bfs.add(nowPos);
		isVisited[nowPos[0]][nowPos[1]] = true;
		int answer = 0;
		while (!bfs.isEmpty()) {
			int[] current = bfs.poll();
			++answer;
			for (int d = 0; d < 4; ++d) {
				int nx = current[0] + dir[0][d];
				int ny = current[1] + dir[1][d];
				if (isBoundary(nx, ny) && !isVisited[nx][ny] && map[nx][ny] == B) {
					isVisited[nx][ny] = true;
					bfs.offer(new int[] { nx, ny });
				}
			}
		}
		return answer * B;
	}

	static void moveDice() {
		int nx = nowPos[0] + dir[0][nowDir];
		int ny = nowPos[1] + dir[1][nowDir];
		// 이동하는 방향에 칸이 없으면 방향을 전환한다.
		if (!isBoundary(nx, ny)) {
			reverseDirection();
			nx = nowPos[0] + dir[0][nowDir];
			ny = nowPos[1] + dir[1][nowDir];
		}
		// 한 칸 이동 시킨다.
		nowPos[0] = nx;
		nowPos[1] = ny;

		changeDice(); // dice 전개도도 바꾸어준다.
		nowDir = changeDirection(nx, ny); // 이동 후 새로운 방향
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}

	static int changeDirection(int x, int y) {
		int A = dice[3][1]; // 주사위 아랫면의 값
		int B = map[x][y]; // 맵에 적혀있는 값
		int answer = nowDir; // 현재 방향
		if (A > B) // 90도 시계방향 동->남->서->북
			answer = (answer + 1) > 3 ? 0 : answer + 1;
		else if (A < B) // 90도 반시계방향
			answer = (answer - 1) < 0 ? 3 : answer - 1;
		return answer;
	}

	static void reverseDirection() {
		int answer = nowDir;
		answer = (nowDir == 1) ? 3 : Math.abs(answer - 2);
		nowDir = answer;
	}

	static void changeDice() {
		int[][] tmp = new int[4][3];
		if (nowDir == 0 || nowDir == 2) { // 동 or 서
			tmp[0][1] = dice[0][1];
			tmp[2][1] = dice[2][1];
			if (nowDir == 0) {
				tmp[1][0] = dice[3][1];
				tmp[3][1] = dice[1][2];
				for (int i = 1; i < 3; ++i) {
					tmp[1][i] = dice[1][i - 1];
				}
			} else {
				tmp[1][2] = dice[3][1];
				tmp[3][1] = dice[1][0];
				for (int i = 0; i < 2; ++i) {
					tmp[1][i] = dice[1][i + 1];
				}
			}
		} else { // 남 or 북
			tmp[1][0] = dice[1][0];
			tmp[1][2] = dice[1][2];
			if (nowDir == 1) {
				tmp[0][1] = dice[3][1];
				for (int i = 1; i < 4; ++i) {
					tmp[i][1] = dice[i - 1][1];
				}
			} else {
				tmp[3][1] = dice[0][1];
				for (int i = 0; i < 3; ++i) {
					tmp[i][1] = dice[i + 1][1];
				}
			}
		}
		dice = tmp;
	}
}
