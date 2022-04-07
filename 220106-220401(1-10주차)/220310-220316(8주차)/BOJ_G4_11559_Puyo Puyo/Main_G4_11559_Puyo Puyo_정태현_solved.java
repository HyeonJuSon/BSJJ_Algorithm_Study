import java.io.*;
import java.util.*;

public class Main {
	static int[] dr = {-1, 0, 1, 0}; //상우하좌
	static int[] dc = {0, 1, 0, -1};
	static char[][] arr;
	static ArrayList<Pos> list;
	static boolean blockDel;
	static boolean[][] checked;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		arr = new char[12][6];
		//블록 터트리기 위한 list
		list = new ArrayList<>();
		
		//input
		for(int i=0; i<12; i++) {
			String str = br.readLine();
			for(int j=0; j<6; j++) {
				arr[i][j] = str.charAt(j);
			}
		}
		
		int answer = 0;
		while(true) {
			blockDel = false;
			checked = new boolean[12][6];
			for(int i=0; i<12; i++) {
				for(int j=0; j<6; j++) {
					if(!checked[i][j] && arr[i][j]!='.') {
						bfs(i, j, arr[i][j]);
						
					}
				}
			}
			
			//연쇄가 한번씩 일어났음
			if(blockDel) {
				answer++;
				//연쇄 일어났으니 정돈
				blockDown();
			}
			
			//연쇄반응이 일어나지 않았다
			if(!blockDel) break;
		}
		
		System.out.println(answer);
	}
	
	public static void bfs(int r, int c, char color) {
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[12][6];
		
		q.offer(new Pos(r, c));
		list.add(new Pos(r, c));
		visited[r][c] = true;
		checked[r][c] = true;
		
		int cnt = 0;
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			cnt++;
			
			for(int i=0; i<dr.length; i++) {
				int nr = cur.r + dr[i];
				int nc = cur.c + dc[i];
				
				if(nr<0 || nc<0 || nr>=12 || nc>=6) continue;
				if(visited[nr][nc]) continue;
				if(arr[nr][nc] != color) continue;
				
				q.offer(new Pos(nr, nc));
				list.add(new Pos(nr, nc));
				visited[nr][nc] = true;
				checked[nr][nc] = true;
			}
		}
		
		
		if(cnt>=4) { //연쇄가 일어남
			blockDel = true;
			//블럭터트림
			delete();
		}
		
		list.clear();
	}
	
	public static void delete() {
		for(Pos p: list) {
			arr[p.r][p.c] = '.';
		}
	} 
	
	public static void blockDown() {
		Queue<Character> q = new LinkedList<>();
		
		
		for(int i=0; i<6; i++) {
			//아래에서부터 색깔블록들을 큐에 넣는다
			for(int j=11; j>=0; j--) {
				if(arr[j][i] != '.') {
					q.offer(arr[j][i]);
					arr[j][i] = '.';
				}
			}
			
			//맨 밑부터 큐에 하나씩 넣는다
			int idx = 11;
			while(!q.isEmpty()) {
				arr[idx][i] = q.poll();
				idx--;
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
}
