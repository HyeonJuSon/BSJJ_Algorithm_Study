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
			if(list.size()==0) break;
			Collections.sort(list);
			
			//제거 bfs용
			visited = new boolean[N][N];
			remove(list.get(0).rowBase, list.get(0).colBase);
			answer += Math.pow(list.get(0).size, 2);
			
			gravity();
			rotate();
			gravity();			
		}
		
		System.out.println(answer);
	}
	
	public static void rotate() {
		Queue<Integer> q = new LinkedList<>();
		
		for(int c=N-1; c>=0; c--) {
			for(int r=0; r<N; r++) {
				q.offer(arr[r][c]);
			}
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				arr[i][j] = q.poll();
			}
		}
		
	}
	
	public static void gravity() {
		Stack<Integer> s = new Stack<>();
		
		for(int c=0; c<N; c++) {
			//검은블록이나 바닥을 만났을때 그 윗블록까지 쌓기 위해 경계선 변수
			int top = 0;
			for(int r=0; r<N; r++) {
				//검은 블록이거나 맨밑이면
				if(arr[r][c] == -1 || r==N-1) {
					for(int i=r; i>=top; i--) {
						if(s.isEmpty()) break;
						//빈칸보이면 쌓기 시작 (블록은 진작에 다 빼고 -2로 빈칸 처리 해두었음)
						if(arr[i][c] == -2) {
							arr[i][c] = s.pop();
						} else if(arr[i][c] == -1 || arr[i][c] >= 0) { //검은 블록 or 기존 색 블록이면 패스
							continue;
						}
					}
					//검은 벽 바로 밑을 top으로 설정해서 그 다음 벽만났을때 top까지만 쌓게함
					top = r+1;
				} else if(arr[r][c] >= 0) {
					s.push(arr[r][c]);
					arr[r][c] = -2;					
				} 
				
			}
			
		}
		
		
	}
	
	public static void bfs(int color, int row, int col) {
		Queue<Pos> q = new LinkedList<>();
		
		q.offer(new Pos(row, col));
		visited[row][col] = true;
		
		int cnt = 0;
		int rainbow = 0;
		boolean isRb = false;
		
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			cnt++;
			
			for(int i=0; i<dr.length; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr<0 || nc<0 || nr>=N || nc>=N) continue;
				if(visited[nr][nc]) continue;
				//색깔블럭 중에 색깔이 다를때
				if(arr[nr][nc]!=0 && arr[nr][nc]!=color) continue;
				if(arr[nr][nc]==-1) continue;
				
				
				if(arr[nr][nc]==0) {
					rainbow++;
					isRb = true;
				}
				q.offer(new Pos(nr, nc));
				
				//무지개일때 visited 처리
				
				visited[nr][nc] = true;
			}
			
		}
		
		if(isRb) rbFalse();
		if(cnt >= 2) list.add(new GroupInfo(cnt, rainbow, row, col));
	}
	
	public static void rbFalse() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(arr[i][j]==0 && visited[i][j]) {
					visited[i][j] = false;
				}
			}
		}
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
				
				if(nr<0 || nc<0 || nr>=N || nc>=N) continue;
				if(visited[nr][nc]) continue;
				//error. 색깔블럭 중에 색깔이 다를때
				if(arr[nr][nc]!=0 && arr[nr][nc]!=color) continue;
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
