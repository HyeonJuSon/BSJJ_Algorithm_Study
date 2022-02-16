import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int[] dr = {-1, 0, 1, 0}; //상우하좌
	static int[] dc = {0, 1, 0, -1};
	static int N, answer;
	static int[][] map;
	static boolean[][] isVisited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		answer = Integer.MAX_VALUE;
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//섬마다 번호 붙이기
		mapNumber();
		
		//bfs
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				isVisited = new boolean[N][N];
				if(map[i][j]!=0 && !isVisited[i][j]) {
					bfs(i, j, map[i][j]);
				}
			}
		}
		
		
		if(answer == Integer.MAX_VALUE) System.out.println(0);
		else System.out.println(answer);
	}
	
	public static void bfs(int r, int c, int mapNum) {
		Queue<Pos> q = new LinkedList<>();
		isVisited[r][c] = true;
		//depth까지 저장
		q.offer(new Pos(r, c, 0));
		
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			
			for(int i=0; i<dr.length; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr<0 || nc<0 || nr>=N || nc>=N) continue;
				if(isVisited[nr][nc]) continue;
				
				//0인 경우만 선택해서 depth에 +1씩하며 다른 섬까지의 최단거리를 체크한다
				if(map[nr][nc]==0) {
					isVisited[nr][nc] = true;
					q.offer(new Pos(nr, nc, cur.depth + 1));
				} 
				//다른 섬에 도착
				else if(map[nr][nc]!=mapNum && !isVisited[nr][nc] && cur.depth > 0) {
					answer = Math.min(answer, cur.depth);
				}
			}
		}
	}
	
	public static void mapNumber() {
		boolean[][] visited = new boolean[N][N];
		Queue<Pos> q = new LinkedList<>();
		//1번부터 붙임
		int num = 1;
		
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				if(visited[r][c] || map[r][c]==0) continue;
				
				//섬에 번호 붙임
				map[r][c] = num;
				visited[r][c] = true;
				q.add(new Pos(r, c));
				
				while(!q.isEmpty()) {
					Pos cur = q.poll();
					
					for(int i=0; i<dr.length; i++) {
						int nr = cur.r + dr[i];
						int nc = cur.c + dc[i];
						
						if(nr<0 || nc<0 || nr>=N || nc>=N) continue;
						if(visited[nr][nc] || map[nr][nc]!=1) continue;
						
						q.offer(new Pos(nr, nc));
						visited[nr][nc] = true;
						map[nr][nc] = num;
					}
				}
				
				num++;
			}
		}
	}
	
	static class Pos {
		int r, c;
		int depth;
		
		//섬 번호 붙이기 단순 위치 저장용
		public Pos(int r, int c) {
			this.r = r;
			this.c = c;
		}
		
		//depth 저장용
		public Pos(int r, int c, int depth) {
			this.r = r;
			this.c = c;
			this.depth = depth;
		}
	}
}
