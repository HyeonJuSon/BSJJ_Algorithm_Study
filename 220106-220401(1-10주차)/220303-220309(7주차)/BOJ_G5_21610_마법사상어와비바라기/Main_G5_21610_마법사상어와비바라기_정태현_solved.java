import java.io.*;
import java.util.*;

public class Main {
	static int[] dr = {0, 0, -1, -1, -1, 0, 1, 1, 1};
	static int[] dc = {0, -1, -1, 0, 1, 1, 1, 0, -1};
	static int N, M;
	static int[][] arr;
	static boolean[][] cloud, visited;
	static ArrayList<Pos> list;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[N][N];
		cloud = new boolean[N][N];
		visited = new boolean[N][N];
		list = new ArrayList<>();
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//구름 초기설정
		cloud[N-2][0] = true;
		cloud[N-2][1] = true;
		cloud[N-1][0] = true;
		cloud[N-1][1] = true;
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int d = Integer.parseInt(st.nextToken()); //방향
			int s = Integer.parseInt(st.nextToken()); //이동할 칸 수
			
			//단순 true, false => 옮긴 것도 또 옮겨버리는 문제
			//큐만 사용 => 구름이 겹칠때 false 시켜버림
			//visited 사용
			//큐 => 리스트 (계속 활용됨)
			for(int r=0; r<N; r++) {
				for(int c=0; c<N; c++) {
					if(cloud[r][c]) {
						list.add(new Pos(r, c));
					}
				}
			}
			
			moveCloud(d, s);
			//물 1씩 더하는 과정
			for(Pos cur: list) {
				arr[cur.r][cur.c]++;
			}
			
			//물복사과정
			for(Pos cur: list) {
				int cnt = waterCopy(cur.r, cur.c);
				arr[cur.r][cur.c] += cnt;
			}
			
			for(int r=0; r<N; r++) {
				for(int c=0; c<N; c++) {
					//이전에 구름이 있던 위치는 패스
					if(visited[r][c]) continue;
					
					if(arr[r][c] >= 2) {
						cloud[r][c] = true;
						arr[r][c] -= 2;
					}
				}
			}
			
			//이전 구름 리스트 false
			for(Pos cur: list) {
				cloud[cur.r][cur.c] = false;
			}
			
			//초기화
			list.clear();
			visited = new boolean[N][N];
		}
		
		int sum = 0;
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				sum += arr[r][c];
			}
		}
		
		System.out.println(sum);
	}
	
	public static void moveCloud(int d, int s) {
		for(Pos cur: list) {
			//%N으로 1바퀴내에서 처리되게 설정
			int nr = (cur.r + (dr[d] * s)) % N;
			int nc = (cur.c + (dc[d] * s)) % N;
			
			//칸을 벗어날 경우(음수일 경우에 N을 더해준다)
			if(nr<0) nr += N;
			if(nc<0) nc += N;
			
			cloud[nr][nc] = true;
			//구름이 옮겨진 곳, false 처리하면 안됨
			visited[nr][nc] = true;
			
			//구름이 옮겨지지 않은 곳만 false 처리
			if(!visited[cur.r][cur.c]) cloud[cur.r][cur.c] = false;
		}
		
		//구름 리스트 업뎃
		list.clear();
		for(int r=0; r<N; r++) {
			for(int c=0; c<N; c++) {
				if(cloud[r][c]) {
					list.add(new Pos(r, c));
				}
			}
		}
//		while(!list.isEmpty()) {
//			Pos cur = list.poll();
//			
//			int nr = cur.r + (dr[d] * s);
//			int nc = cur.c + (dc[d] * s);
//			
//			//칸을 벗어날 경우
//			if(nr<0) nr = nr + 5;
//			if(nc<0) nc = nc + 5;
//			
//			cloud[nr][nc] = true;
//			//구름이 옮겨진 곳, false 처리하면 안됨
//			visited[nr][nc] = true;
//			
//			//구름이 옮겨지지 않은 곳만 false 처리
//			if(!visited[cur.r][cur.c]) cloud[cur.r][cur.c] = false;
//		}
	}
	
	
	public static int waterCopy(int r, int c) {
		int count = 0;
		for(int i=2; i<=8; i+=2) {
			int nr = r + dr[i];
			int nc = c + dc[i];
			
			if(nr<0 || nc<0 || nr>=N || nc>=N) continue;
			if(arr[nr][nc]==0) continue;
			
			count++;
		}
		
		return count;
	}
	
	
	static class Pos {
		int r, c;
		
		public Pos(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
}
