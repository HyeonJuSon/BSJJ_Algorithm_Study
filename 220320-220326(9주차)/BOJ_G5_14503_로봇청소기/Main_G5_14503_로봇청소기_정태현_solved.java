import java.io.*;
import java.util.*;

public class Main {
	static int[] dr = {-1, 0, 1, 0}; //상우하좌
	static int[] dc = {0, 1, 0, -1};
	static int[][] arr;
	static boolean[][] clean;
	static int N, M, row, col, dir, ans, cleanCnt;
	static boolean isCleaned, back;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N][M];
		clean = new boolean[N][M];
		
		st = new StringTokenizer(br.readLine(), " ");
		row = Integer.parseInt(st.nextToken());
		col = Integer.parseInt(st.nextToken());
		dir = Integer.parseInt(st.nextToken());
		
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				if(arr[i][j]==1) {
					clean[i][j] = true;
				}
			}
		}
		
		
		isCleaned = true;
		back = false;
		cleanCnt = 0;
		ans = 0;
		
		while(true) {
			if(!clean[row][col] && !back) {
				clean[row][col] = true;
				ans++;
			}
			
			process(row, col, dir);
			
			
			if(cleanCnt == 4) {
				int bD = (dir+2) % 4;
				int bR = row + dr[bD];
				int bC = col + dc[bD];
				
				//뒤방향이 벽
				if(arr[bR][bC] == 1) {
					break;
				}
				
				//후진함
				back = true;
				row = bR;
				col = bC;
				cleanCnt = 0;
			} else {
				back = false;
			}
			
			
		}
		
		System.out.println(ans);
	}
	
	public static void process(int r, int c, int d) {
		//왼쪽방향이 0인지 1인지
		d = ((d+4) - 1) % 4;
		
		int nr = r + dr[d];
		int nc = c + dc[d];
		
		//청소하지 않은 공간
		if(!clean[nr][nc]) {
			dir = d;
			//이동까지
			row = nr;
			col = nc;
			//이동했으니 초기화
			cleanCnt = 0;
		} else { //0이나 벽이면 청소할공간 아님
			//방향만 바꾸고 끝
			dir = d;
			cleanCnt++;
		}
		
		return;
	}
}
