import java.io.*;
import java.util.*;

public class Main {
	static int R, C, T;
	static int[][] arr, temp;
	static int[] dr = {-1, 0, 1, 0}; //상우하좌
	static int[] dc = {0, 1, 0, -1};
	static int[] cleaner;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		arr = new int[R][C];
		cleaner = new int[2];
		
		int idx = 0;
		for(int i=0; i<R; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j=0; j<C; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				if(arr[i][j] == -1) {
					//1열이니까 R값만 저장, 어차피 위부터 저장됨 순서고려X
					cleaner[idx++] = i;
				}
			}
		}
		
		for(int t=0; t<T; t++) {
			temp = new int[R][C];
			
			for(int i=0; i<R; i++) {
				for(int j=0; j<C; j++) {
					//청소기와 먼지없는칸 모두 제외
					if(arr[i][j]>0) {
						spread(i, j);
					}
				}
			}
			
			//temp배열을 arr에 복사
			copy();
			
			clean();
		}

        int sum = 0;
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                if(arr[i][j]==-1) continue;
                sum+=arr[i][j];
            }
        }

        System.out.println(sum);
	}
	
	public static void spread(int r, int c) {
		//먼지 퍼뜨릴 수 있는 칸 수
		int posCnt = 0;
		//퍼뜨릴 먼지양
		int dust = arr[r][c] / 5;
		for(int i=0; i<dr.length; i++) {
			int nr = r + dr[i];
			int nc = c + dc[i];
			
			//벗어나는 칸
			if(nr<0 || nc<0 || nr>=R || nc>=C) continue;
			//청소기
			if(arr[nr][nc]== -1) continue;
			
			temp[nr][nc] += dust;
			posCnt++;
		}
		
		temp[r][c] += arr[r][c] - (dust * posCnt);
	}
	
	public static void copy() {
		for(int i=0; i<R; i++) {
			for(int j=0; j<C; j++) {
				//청소기는 그대로
				if(arr[i][j]==-1) continue;
				
				arr[i][j] = temp[i][j];
			}
		}
	}
	
	public static void clean() {
		int first = cleaner[0];
		int sec = cleaner[1];
        // 위쪽 공기청정기의 바람은 반시계방향 순환,
        // 아래로 당기기
        for (int i = first - 1; i > 0; i--) 
            arr[i][0] = arr[i-1][0];
        // 왼쪽으로 당기기
        for (int i = 0; i < C - 1; i++) 
            arr[0][i] = arr[0][i+1];
        // 위로 당기기
        for (int i = 0; i < first; i++) 
            arr[i][C - 1] = arr[i + 1][C - 1];
        // 오른쪽으로 당기기
        for (int i = C - 1; i > 1; i--) 
            arr[first][i] = arr[first][i-1];
        // 공기청정기에서 부는 바람은 미세먼지가 없는 바람
        arr[first][1] = 0;
        
        // 아래쪽 공기청정기의 바람은 시계방향으로 순환
        // 위로 당기기
        for (int i = sec + 1; i < R - 1; i++) 
            arr[i][0] = arr[i + 1][0];
        // 왼쪽으로 당기기
        for (int i = 0; i < C - 1; i++) 
            arr[R - 1][i] = arr[R - 1][i + 1]; 
        // 아래로 당기기
        for (int i = R - 1; i > sec; i--) 
            arr[i][C - 1] = arr[i - 1][C - 1];
        // 오른쪽으로 당기기
        for (int i = C - 1; i > 1; i--) 
            arr[sec][i] = arr[sec][i - 1];
        // 공기청정기에서 부는 바람은 미세먼지가 없는 바람
        arr[sec][1] = 0;
	}
	
	public static class Pos {
		int r, c;
		
		public Pos (int r, int c){
			this.r = r;
			this.c = c;
		}
	}
}
