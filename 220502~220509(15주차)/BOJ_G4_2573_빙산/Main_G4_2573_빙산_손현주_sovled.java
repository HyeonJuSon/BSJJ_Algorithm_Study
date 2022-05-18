package gold;

import java.io.*;
import java.util.*;

public class bj_2573_G4 {

	static int X, Y, map[][];
	static Queue<int[]> ices = new LinkedList<>(); 				// 얼음 큐 
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };	// 4방 탐색

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		X = Integer.parseInt(st.nextToken());
		Y = Integer.parseInt(st.nextToken());
		map = new int[X][Y];									
		for (int i = 0; i < X; ++i) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < Y; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}													// 입력 끝
		int year = 0;										// 년도 변수
		while (true) {										// 반복시작
			int pcs = findPieces();							// 조각 몇갠 지
			if (pcs>=2) {									// 2개 이상이면 년도 출력
				System.out.println(year);
				break;
			}else if(pcs==0) {								// 안나눠지면 0 출력
				System.out.println(0);
				break;
			}
			findToMelt();									// 녹이는 작업 시작
			year++;											// 년도 증가
		}
	}

	static void findToMelt() { 								// 녹이기 작업
		boolean[][] isVisited = new boolean[X][Y];			// 방문 체크 배열
		for (int i = 1; i < X; ++i) {						// 조건에서 첫,끝 행/열은 0이라함
			for (int j = 1; j < Y; ++j) {
				if (map[i][j] != 0) { 						// 얼음을 찾아서
					ices.add(new int[] { i, j });			// 큐에담고
					isVisited[i][j] = true;					// 얼음이 녹아서 0일때 바다로 오인방지
				}
			}
		}
		while (!ices.isEmpty()) {							// 큐 빌 때 까지
			int[] cur = ices.poll();						// 뽑아주고
			melt(cur[0], cur[1], isVisited); 				// 찐 녹여준다.
		}
	}

	static void melt(int x, int y, boolean[][] isVisited) {
		int count = 0; 										// 물 인접 부위 개수
		for (int d = 0; d < 4; ++d) {
			int nx = x + dir[0][d];
			int ny = y + dir[1][d];							// 4방 탐색.
			if (isBoundary(nx, ny) && map[nx][ny] == 0 && !isVisited[nx][ny]) {
				count++; 								// 물(=기존 얼음이 녹은 물은 제외)이면 카운팅
			}
		}
		int minus = map[x][y] - count;	
		map[x][y] = minus < 0 ? 0 : minus;					// 녹여주되 음수면 0으로 넣는다.
	}

	static int findPieces() { 								// 조각찾기.
		boolean[][] isVisited = new boolean[X][Y];			// 방문 체크 배열.
		int cnt = 0; 										// 나눠진 개수 카운팅
		for (int i = 1; i < X; ++i) {
			for (int j = 1; j < Y; ++j) {
				if (map[i][j] > 0 && !isVisited[i][j]) {	// 얼음이고 방문안된 곳
					cnt++;									// 카운팅
					isVisited[i][j] = true;					// 방문 체크
					pieces(i, j, isVisited);				// 탐색 시작
				}
			}
		}
		return cnt; 										// 조각난 개수 반환
	}

	static void pieces(int x, int y, boolean[][] isVisited) {// dfs로 조각찾음.
		for (int d = 0; d < 4; ++d) {
			int nx = dir[0][d] + x;
			int ny = dir[1][d] + y;
			if (isBoundary(nx, ny) && map[nx][ny] > 0 && !isVisited[nx][ny]) {
				isVisited[nx][ny] = true; 					// 방문체크해주고
				pieces(nx, ny, isVisited); 					// 재탐색
			}
		}
	}

	static boolean isBoundary(int x, int y) { 				// 좌표가 범위 내에 있는지 여부
		return 0 <= x && x < X && 0 <= y && y < Y;
	}
}
