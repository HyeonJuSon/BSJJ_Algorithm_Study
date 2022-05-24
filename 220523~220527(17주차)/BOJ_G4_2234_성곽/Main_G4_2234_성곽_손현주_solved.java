package gold;

import java.util.*;
import java.io.*;

public class BJ_2234_G4 {

	static class Room implements Comparable<Room> {
		int idx;
		int sum;

		Room(int idx, int sum) {
			this.idx = idx;
			this.sum = sum;
		}

		@Override
		public int compareTo(Room o) {
			return Integer.compare(o.sum, this.sum);
		}
	}

	static int N, M, map[][][];
	// 하,우,상,좌
	static int[][] dir = { { 1, 0, -1, 0 }, { 0, 1, 0, -1 } };
	static boolean[][] isVisited;
	static PriorityQueue<Room> rooms = new PriorityQueue<>();
	static int[][] roomMap;
	static HashMap<Integer,Integer> info = new HashMap<Integer,Integer>();
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][M][4];
		roomMap=new int[N][M];
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; ++j) {
				int val = Integer.parseInt(st.nextToken());
				markWall(i, j, val);
			}
		}

		process();
	}

	static void process() {
		isVisited = new boolean[N][M];
		int idx = 1;
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				if (!isVisited[i][j]) {
					isVisited[i][j] = true;
					bfs(i, j, idx++);
				}
			}
		}
		// 이 성에 있는 방의 개수
		System.out.println(rooms.size());
		// 가장 넓은 방의 넓이
		System.out.println(rooms.peek().sum);
		// 하나의 벽을 제거하여 얻을 수 있는 가장 넓은 방의 크기
		System.out.println(getLargest());
	}
	
	static int getLargest() {
		int max = 0;
		
		Queue<Room> tmp = new LinkedList<>(rooms);
		
		while(!tmp.isEmpty()) {
			int nowIdx = tmp.peek().idx; // 현재 번호
			HashSet<Integer> hs = new HashSet<Integer>();
			for(int i=0;i<N;++i) {
				for(int j=0;j<M;++j) {
					if(roomMap[i][j]==nowIdx) {
						for(int d=0;d<4;++d) {
							int nx = i+dir[0][d];
							int ny = j+dir[1][d];
							if(isBoundary(nx,ny)&& !hs.contains(roomMap[i][j]) && roomMap[nx][ny]!=nowIdx) {
								hs.add(roomMap[nx][ny]);
								break;
							}
						}
					}
				}
			}
			int nowSum = tmp.poll().sum;
			for(Integer key : hs) {
				max = Math.max(max, nowSum + info.get(key));
			}
				
		}
		
		return max;
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}

	static void bfs(int x, int y, int idx) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { x, y });
		int cnt =0;
		while (!q.isEmpty()) {
			int[] now = q.poll();
			++cnt;
			roomMap[now[0]][now[1]]=idx;
			for (int d = 0; d < 4; ++d) {
				int nx = now[0] + dir[0][d];
				int ny = now[1] + dir[1][d];
				if (!isBoundary(nx, ny))
					continue;
				if (isVisited[nx][ny])
					continue;
				if (map[now[0]][now[1]][d] == 1)
					continue;// 이동하려는 방향에 벽이있으면 패스
				isVisited[nx][ny] = true;
				q.add(new int[] { nx, ny });
			}
		}
		rooms.add(new Room(idx, cnt));
		info.put(idx, cnt);
	}

	static void markWall(int x, int y, int val) {
		String check = String.format("%04d", Integer.parseInt(Integer.toBinaryString(val & 15)));
		// 하,우,상,좌
		int dir = 0;
		for (int i = 0; i < check.length(); ++i) {
			if (check.charAt(i) == '1')
				map[x][y][dir] = 1;
			++dir;
		}
	}
}
