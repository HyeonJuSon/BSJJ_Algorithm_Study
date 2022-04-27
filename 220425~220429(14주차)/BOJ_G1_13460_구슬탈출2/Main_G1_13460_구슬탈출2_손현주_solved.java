package gold;

import java.io.*;
import java.util.*;

public class bj_13460_G1 {

	static int N, M, original[][];
	static final int empty = 0, wall = 1, hole = 2, red = 3, blue = 4;
	static final int Left = 0, Right = 1, Up = 2, Down = 3;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		original = new int[N][M];
		int[] red = new int[2];
		int[] blue = new int[2];
		for (int i = 0; i < N; ++i) {
			String now = br.readLine();
			for (int j = 0; j < M; ++j) {
				char ch = now.charAt(j);
				if (ch == '#')
					original[i][j] = wall;
				else if (ch == '.')
					original[i][j] = empty;
				else if (ch == 'O') {
					original[i][j] = hole;
				} else if (ch == 'R') {
					red = new int[] { i, j };
				} else {
					blue = new int[] { i, j };
				}
			}
		}
		// 탐색한다.
		Queue<Node> bfs = new LinkedList<>();
		int answer = Integer.MAX_VALUE;
		bfs.add(new Node(0, red, blue)); // 한번도 안굴린 상태를 넣어준다.
		while (!bfs.isEmpty()) {
			Node now = bfs.poll();
//			for(int i=0;i<N;++i) {
//				for(int j=0;j<M;++j) {
//					System.out.print(now.nowMap[i][j]+" ");
//				}
//				System.out.println();
//			}
//			System.out.println();
			if (now.cnt >= answer || now.cnt > 10) {
				continue;
			}
			for (int d = 0; d < 4; ++d) {
				int[] tmpR= new int[] {now.R[0], now.R[1]};
				int[] tmpB = new int[] {now.B[0], now.B[1]};
				int goalState = turnBall(tmpR, tmpB, d); // 0은 이동 가능, 1은 빨강이 골일때, 2는 파랑이 골에 들어갔을 떄
				if (goalState == 0) {// 더 굴러가야한다면
					bfs.add(new Node(now.cnt + 1, tmpR, tmpB));
				} else if (goalState == 1) {
					answer = Math.min(answer, now.cnt + 1);
					continue;
				} else {
					continue;
				}
			}
		}
		// 출력
		System.out.println(answer == Integer.MAX_VALUE || answer == 11 ? -1 : answer);
	}

	static int turnBall(int[] R, int[] B, int d) {
		int[] holes = new int[2];// 0-red, 1-blue
		// 같이 들어가는 경우
		if (d == Left) { // 좌로 굴릴 때.
			for (int i = 1; i < N - 1; ++i) {
				for (int j = 1; j < M - 1; ++j) {
					int movePos = j;
					if (R[0] == i && R[1] == j) { // 현재 위치가 빨간공이라면
						while (true) {
							if (original[i][movePos - 1] != wall && (B[0]!=i || B[1]!=movePos-1)) {
								if (original[i][movePos - 1] == hole) {// 이동하려는 위치가 구멍이라면
									holes[0] = 1; // 빨간공이 구멍에 들어갔음을 체크하고
									R[1] = -1;
									break; // 구멍에 빨간 공이 들어갔음.
								}
								--movePos;
							} else {
								R[1] = movePos; // 이동위치로 옮겨주고
								break;
							}
						}
					} else if (B[0] == i && B[1] == j) { // 현재 위치가 파란 공이라면
						while (true) {
							if (original[i][movePos - 1] != wall && (R[0]!=i || R[1]!=movePos-1)) {
								if (original[i][movePos - 1] == hole) {
									holes[1] = 1; // 파란공 들어갔음을 체크
									B[1] = -1;
									break; // 구멍에 파란 공이 들어갔음.
								}
								--movePos;
							} else {
								B[1] = movePos;
								break;
							}
						}
					}
				}
			}
		} else if (d == Right) {
			for (int i = 1; i < N - 1; ++i) {
				for (int j = M - 2; j >= 1; --j) {
					int movePos = j;
					if (R[0] == i && R[1] == j) { // 현재 위치가 빨간공이라면
						while (true) {
							if (original[i][movePos + 1] != wall && (B[0]!=i || B[1]!=movePos+1)) {
								if (original[i][movePos + 1] == hole) {// 이동하려는 위치가 구멍이라면
									holes[0] = 1; // 빨간공이 구멍에 들어갔음을 체크하고
									R[1] = -1;
									break; // 구멍에 빨간 공이 들어갔음.
								}
								++movePos;
							} else {
								R[1] = movePos; // 이동위치로 옮겨주고
								break;
							}
						}
					} else if (B[0] == i && B[1] == j) { // 현재 위치가 파란 공이라면
						while (true) {
							if (original[i][movePos + 1] != wall && (R[0]!=i || R[1]!=movePos+1)) {
								if (original[i][movePos + 1] == hole) {
									holes[1] = 1; // 파란공 들어갔음을 체크
									B[1] = -1;
									break; // 구멍에 파란 공이 들어갔음.
								}
								++movePos;
							} else {
								B[1] = movePos;
								break;
							}
						}
					}
				}
			}
		} else if (d == Up) {
			for (int j = 1; j < M - 1; ++j) {
				for (int i = 1; i < N - 1; ++i) {
					int movePos = i;
					if (R[0] == i && R[1] == j) { // 현재 위치가 빨간공이라면
						while (true) {
							if (original[movePos - 1][j] != wall && (B[0]!=movePos-1 || B[1]!=j)) {
								if (original[movePos - 1][j] == hole) {// 이동하려는 위치가 구멍이라면
									holes[0] = 1; // 빨간공이 구멍에 들어갔음을 체크하고
									R[0] = -1;
									break; // 구멍에 빨간 공이 들어갔음.
								}
								--movePos;
							} else {
								R[0] = movePos;// 이동위치로 옮겨주고
								break;
							}
						}
					} else if (B[0] == i && B[1] == j) { // 현재 위치가 파란 공이라면
						while (true) {
							if (original[movePos - 1][j] != wall && (R[0]!=movePos-1 || R[1]!=j)) {
								if (original[movePos - 1][j] == hole) {
									holes[1] = 1; // 파란공 들어갔음을 체크
									B[0] = -1;
									break; // 구멍에 파란 공이 들어갔음.
								}
								--movePos;
							} else {
								B[0] = movePos;
								break;
							}
						}
					}
				}
			}
		} else if (d == Down) {
			for (int j = 1; j < M - 1; ++j) {
				for (int i = N - 2; i >= 1; --i) {
					int movePos = i;
					if (R[0] == i && R[1] == j) { // 현재 위치가 빨간공이라면
						while (true) {
							if (original[movePos + 1][j] != wall && (B[0]!=movePos+1 || B[1]!=j)) {
								if (original[movePos + 1][j] == hole) {// 이동하려는 위치가 구멍이라면
									holes[0] = 1; // 빨간공이 구멍에 들어갔음을 체크하고
									R[0] = -1;
									break; // 구멍에 빨간 공이 들어갔음.
								}
								++movePos;
							} else {
								R[0] = movePos; // 이동위치로 옮겨주고
								break;
							}
						}
					} else if (B[0] == i && B[1] == j) { // 현재 위치가 파란 공이라면
						while (true) {
							if (original[movePos + 1][j] != wall && (R[0]!=movePos+1 || R[1]!=j)) {
								if (original[movePos + 1][j] == hole) {
									holes[1] = 1; // 파란공 들어갔음을 체크
									B[0] = -1;
									break; // 구멍에 파란 공이 들어갔음.
								}
								++movePos;
							} else {
								B[0] = movePos;
								break;
							}
						}
					}
				}
			}
		}
		if (holes[0] == 0 && holes[1] == 0)
			return 0;
		else if (holes[0] == 1 && holes[1] == 0)
			return 1;
		return 2;
	}

	static class Node {
		int cnt;
		int[] R;
		int[] B;

		public Node(int cnt, int[] r, int[] b) {
			this.cnt = cnt;
			R = r;
			B = b;
		}
	}
}
