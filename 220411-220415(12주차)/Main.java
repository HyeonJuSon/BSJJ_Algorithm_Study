import java.io.*;
import java.util.*;

public class Main {
	static int dr[] = {-1, 0, 1, 0}; //상우하좌
	static int dc[] = {0, 1, 0, -1};
	static int N, M;
	static int[][] arr;
	static boolean[][] visited;
	static ArrayList<GroupInfo> list;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][N];
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int answer = 0;
		while(true) {
			visited = new boolean[N][N];
			list = new ArrayList<>();
			
			for(int i=0; i<N; i++) {
				for(int j=0; j<N; j++) {
					//검은 블록 아니면
					//무지개도 일단 타지않는걸로
					//방문안한거
					if(!visited[i][j] && arr[i][j]>0) {
						bfs(arr[i][j], i, j);
					}
				}
			}
			
			//제거 bfs용
			visited = new boolean[N][N];
			remove(list.get(0).rowBase, list.get(0).colBase);
			answer += Math.pow(list.get(0).size, 2);
			
			gravity();
		}
	}
	
	public static void gravity() {
		Queue<Integer> q = new LinkedList<>();
		
		for(int c=0; c<N; c++) {
			int bottom = 0;
			for(int r=N-1; r>=0; r--) {
				if(arr[r][c] == -1) {
					//위층을 맨밑층으로
					bottom = r-1;
				}
				if(arr[r][c] >= 0) {
					q.offer(arr[r][c]);
					arr[r][c] = -2;
				}
			}
			
			for(int r=bottom; r>=0; r--) {
				if(arr[r][c] == -2) {
					arr[r][c] = q.poll();
				}
			}
		}
		
		
	}
	
	public static void bfs(int color, int row, int col) {
		Queue<Pos> q = new LinkedList<>();
		
		q.offer(new Pos(row, col));
		visited[row][col] = true;
		
		int rowPos = 21;
		int colPos = 21;
		int cnt = 0;
		int rainbow = 0;
		
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			cnt++;
			
			for(int i=0; i<dr.length; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr<0 || nc>0 || nr>=N || nc>=N) continue;
				if(visited[nr][nc]) continue;
				if(arr[nr][nc]!=color) continue;
				if(arr[nr][nc]==-1) continue;
				
				
				if(arr[nr][nc]==0) rainbow++;
				q.offer(new Pos(nr, nc));
				
				//무지개가 아닌것중에 기준정함
				//수정해야함
				//자연스럽게 맨위것으로 정해지나??????????
//				if(arr[nr][nc] != 0) {
//					rowPos = Math.min(nr, rowPos);
//					colPos = Math.min(nc, colPos);
//				}
				
				visited[nr][nc] = true;
			}
		}
		
		if(cnt >= 2) list.add(new GroupInfo(cnt, rainbow, row, col));
	}
	
	public static void remove(int row, int col) {
		Queue<Pos> q = new LinkedList<>();
		
		q.offer(new Pos(row, col));
		visited[row][col] = true;
		int color = arr[row][col];
		
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			
			for(int i=0; i<dr.length; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr<0 || nc>0 || nr>=N || nc>=N) continue;
				if(visited[nr][nc]) continue;
				if(arr[nr][nc]!=color) continue;
				if(arr[nr][nc]==-1) continue;
				
				
				q.offer(new Pos(nr, nc));
				visited[nr][nc] = true;
			}
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(visited[i][j]) {
					arr[i][j] = -2;
				}
			}
		}
	}
	
	public static class Pos {
		int r, c;
		
		public Pos(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	
	public static class GroupInfo implements Comparable<GroupInfo> {
		int size, rbCnt, rowBase, colBase;
		
		public GroupInfo (int size, int rbCnt, int rowBase, int colBase) {
			this.size = size;
			this.rbCnt = rbCnt;
			this.rowBase = rowBase;
			this.colBase = colBase;
		}
		
		@Override
		public int compareTo(GroupInfo o) {
			if(this.size == o.size) {
				if(this.rbCnt == o.rbCnt) {
					if(this.rowBase == o.rowBase) {
						return o.colBase - this.colBase;
					}
					return o.rowBase - this.rowBase;
				}
				
				return o.rbCnt - this.rbCnt;
			}
				
			return o.size - this.size;
		}
	}
}
