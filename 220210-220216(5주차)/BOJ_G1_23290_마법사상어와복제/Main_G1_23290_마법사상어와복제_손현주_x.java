package gold;

import java.io.*;
import java.util.*;

public class bj_23290_G1 {
	static class Fish {
		int x, y, dir;

		Fish(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}

	static int N = 4, M, S;
	static int[]sDir = { 2,0,6,4 };// 사전 순으로 해야해서 상-좌-하-우 순
	// 물고기 회전 방향 순서 ←, ↖, ↑, ↗, →, ↘, ↓, ↙
	static int[][] fDir = { { 0, -1, -1, -1, 0, 1, 1, 1 }, { -1, -1, 0, 1, 1, 1, 0, -1 } };
	static int[][] smell = new int[N][N]; // 물고기 냄새 저장
	static ArrayList<Integer>[][] map = new ArrayList[N][N]; // 물고기 저장용
	static ArrayList<Fish> fishList;
	static Fish shark;
	static Deque<Integer>[][] smells= new LinkedList[N][N];
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				map[i][j] = new ArrayList<>();
				smells[i][j] = new LinkedList<>();
			}
		}
		// 물고기 정보를 입력받는다.
		for (int i = 0; i < M; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;
			map[x][y].add(d);
		}
		// 상어 정보를 입력받는다.
		st = new StringTokenizer(br.readLine(), " ");
		int x = Integer.parseInt(st.nextToken()) - 1;
		int y = Integer.parseInt(st.nextToken()) - 1;
		shark = new Fish(x, y, -1);

		// 시작
		process();
	}

	static void process() {
		for (int s = 0; s < S; ++s) {
			copyMagic(false);
			moveFish();
			moveShark(s);
			removeSmell(s);
			copyMagic(true);
		}
	}

	static void copyMagic(boolean isReady) {
		if (isReady) { // 마법 시전 준비가 완료되었다면
			for (Fish f : fishList) { // 복제한다.
				map[f.x][f.y].add(f.dir);
			}

		} else { // 아직 마법 시전 준비가 덜 되었다면
			fishList = new ArrayList<>(); // 복사만 해둔다.
			for (int i = 0; i < N; ++i) {
				for (int j = 0; j < N; ++j) {
					for (int d : map[i][j]) { // 해당 위치에 있는 물고기가 한 마리가 아닐 수 있음!!
						fishList.add(new Fish(i, j, d));
					}
				}
			}
		}
	}

	static void moveFish() {
		// 임시로 담을 배열 -> 임시 배열을 쓰는 이유? 물고기가 이동했을 때 걔가 또 이동될 수 있음
		ArrayList<Integer>[][] tmp = new ArrayList[N][N];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				tmp[i][j] = new ArrayList<Integer>();
			}
		}

		// 물고기 이동
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				for (int dir : map[i][j]) {
					int nx;
					int ny;
					boolean isPossible = false; // 이동 가능 여부
					for (int d = 0; d < 8; ++d) {
						int nd = ((d - i) + 8) % 8; // 회전
						nx = i + fDir[0][nd];
						ny = j + fDir[1][nd];
						if(!isBoundary(nx,ny) || nx == shark.x && ny == shark.y) continue;
						if(smells[nx][ny].size()!=0) continue;
						tmp[nx][ny].add(nd); // 물고기 이동
						isPossible = true;
						break;
					}
					if(!isPossible) tmp[i][j].add(dir);
				}
			}
		}
		map = tmp;
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}

	static void moveShark(int s) {
		int move =0, maxMove = 0;
		int eat = 0, eatMax = 0;
		int nx = shark.x, ny = shark.y;
		boolean isOver = false;
		boolean[][] isVisited;
		for(int i=0; i< N; ++i) {
			isVisited = new boolean[N][N]; // 초기화
			
			nx += fDir[0][sDir[i]];
			ny += fDir[1][sDir[i]];
			// 범위 초과시 다시 상어 위치로 갱신
			if(!isBoundary(nx,ny)) {
				nx = shark.x;
				ny=shark.y;
				continue;
			}
			
			move = i;
			isVisited[nx][ny] =true;
			eat = map[nx][ny].size();
			for(int j=0;j<N;++j) {
				nx += fDir[0][sDir[j]];
				ny += fDir[1][sDir[j]];
				// 범위 초과시 다시 상어 위치로 갱신
				if(!isBoundary(nx,ny)) {
					nx -= fDir[0][sDir[j]];
					ny -= fDir[1][sDir[j]];
					continue;
				}
				
				move = move * 10 + j;
				if(!isVisited[nx][ny]) eat += map[nx][ny].size(); // 물고기 뚝딱
				
				for(int k=0;k<N;++k) {
					nx += fDir[0][sDir[k]];
					ny += fDir[1][sDir[k]];
					// 범위 초과시 다시 상어 위치로 갱신
					if(!isBoundary(nx,ny)) {
						nx -= fDir[0][sDir[k]];
						ny -= fDir[1][sDir[k]];
						continue;
					}
					
					int finalEat  =0;
					if(!isVisited[nx][ny]) finalEat= eat + map[nx][ny].size();
					
					nx -= fDir[0][sDir[k]];
					ny -= fDir[1][sDir[k]];
					
					if(isOver && eatMax >= finalEat) continue;
					move = move * 10 + k;
					maxMove = move;
					eatMax = finalEat;
					isOver = true;
					move /= 10;
				}

				isVisited[nx][ny] =false;
				nx = shark.x;
				ny = shark.y;
			}
			nx = shark.x;
			ny = shark.y;
			int idx = 2;
			
			while(idx>=0) {
				int base = (int)Math.pow(10, idx--);
				int m = maxMove / base;
				maxMove %= base;
				
				nx += fDir[0][sDir[m]];
				ny += fDir[1][sDir[m]];
				
				if(map[nx][ny].size()==0) continue;
				smells[nx][ny].add(s+2);
				map[nx][ny].clear();
			}
			shark.x = nx;
			shark.y = ny;
		}
	}

	static void removeSmell(int time) {
		for(int i=0;i<N;++i) {
			for(int j=0;j<N;++j) {
				while(!smells[i][j].isEmpty()) {
					int s = smells[i][j].poll();
					if(time-s<0) {
						smells[i][j].addFirst(s);
						break;
					}
				}
			}
		}
		
	}
}