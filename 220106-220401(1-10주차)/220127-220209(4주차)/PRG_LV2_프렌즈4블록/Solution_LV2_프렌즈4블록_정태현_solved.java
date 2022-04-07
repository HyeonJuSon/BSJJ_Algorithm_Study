import java.io.*;
import java.util.*;

public class Main {
	static int[] dr = {0, 1, 1}; //우 대각 하
    static int[] dc = {1, 1, 0};
    static char[][] arr;
    static boolean flag;
    public static int solution(int m, int n, String[] board) {
        int answer = 0;
        
        arr = new char[m][n];
        for(int i = 0 ; i < m ; ++i) {
			arr[i] = board[i].toCharArray();
		}
        
        while(true) {
            //더 이상 블럭을 없앨 수 없는지 체크용
            flag = false; 
            //부숴야 할 블럭 체크용
            //같은 블록이 여러 2*2에 포함될 수 있기 때문에 따로 체크
            boolean[][] check = new boolean[m][n];
            
            //check
            for(int i=0; i<m; i++) {
                for(int j=0; j<n; j++) {
                    if(arr[i][j] != '0') {
                        canBreak(i, j, arr[i][j], check);
                    }

                }
            }
            if(!flag) break;
            
            //check boolean배열과 비교하며 블럭 없앰(0으로 표시)
            int cnt = 0;
            for(int i=0; i<arr.length; i++) {
                for(int j=0; j<arr[i].length; j++) {
                    if(check[i][j]) {
                        arr[i][j] = '0';
                        cnt++;
                    }
                }
            }
            //없앤 수 더해줌
            answer += cnt;
            
            //블럭 아래로 정렬, 세로줄마다 체크
            for(int i=0; i<arr[0].length; i++) {
                sort(i);
            }
        }
        
        
        return answer;
    }
    
    public static void canBreak(int r, int c, char cur, boolean[][] check) {
        for(int i=0; i<dr.length; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            
            if(nr<0 || nc<0 || nr>=arr.length || nc>=arr[0].length) return;
            if(arr[nr][nc]!=cur) return;
        }
        
        //return 되지 않았으면 블럭을 없앨 수 있음, true
        flag = true;
        
        //블럭 없앰
        check[r][c] = true;
        for(int i=0; i<dr.length; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            
            check[nr][nc] = true;
        }
    }
    
    public static void sort(int col) {
        Queue<Character> q = new LinkedList<>();
        for(int i=arr.length-1; i>=0; i--) {
            //세로줄에 빈칸 제외 아래서부터 큐에 넣음
            if(arr[i][col]!='0') {
                q.offer(arr[i][col]);
            }
        }
        
        for(int i=arr.length-1; i>=0; i--) {
            if(q.size()!=0) {
                //큐에 있는 것들 아래서부터 차곡차곡 쌓아줌
                arr[i][col] = q.poll();
            } else {
                //큐가 비면 전부 빈 칸 처리
                arr[i][col] = '0';
            }
        }
    }
	
	public static void main(String[] args) throws IOException {
		int m = 4, n = 5;
		String[] board = {"CCBDE", "AAADE", "AAABF", "CCBBF"};
		
		System.out.println(solution(m, n, board));
	}
}
