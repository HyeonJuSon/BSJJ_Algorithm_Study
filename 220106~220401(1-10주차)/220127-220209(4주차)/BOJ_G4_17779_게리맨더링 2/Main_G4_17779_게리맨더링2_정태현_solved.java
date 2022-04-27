import java.io.*;
import java.util.*;

public class Main {
   static int N;
   static int[][] city;
   static int totalSum, answer;
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      StringTokenizer st = null;
      
      N = Integer.parseInt(br.readLine());
      
      city = new int[N][N];
      totalSum = 0;
      answer = Integer.MAX_VALUE;
      
      for(int i=0; i<N; i++) {
         st = new StringTokenizer(br.readLine(), " ");
         for(int j=0; j<N; j++) {
            city[i][j] = Integer.parseInt(st.nextToken());
            totalSum += city[i][j];
         }
      }
      
      for(int x=0; x<N; x++) {
         for(int y=0; y<N; y++) {
            for(int d1=1; d1<N; d1++) {
               for(int d2=1; d2<N; d2++) {
                  if(x+d1+d2 >= N) continue;
                  if(y-d1<0 || y+d2>=N) continue;
                  
                  solve(x, y, d1, d2);
               }
            }
         }
      }
      
      System.out.println(answer);
   }
   
   public static void solve(int x, int y, int d1, int d2) {
      int[] dist = new int[5];
      boolean[][] distMap = new boolean[N][N];
      
      //5구역(dist[4])
//      int turn = 0;
//      for(int i=x; i<=x+d1+d2; i++) {
//         int left = x, right = y;
//         
//         if(turn==0) {
//            distMap[left][right] = true;
//            dist[4] += city[left][right];
//            turn++;
//            continue;
//         }
//         
//         if(turn<=d1) left--;
//         else left++;   
//         
//         if(turn<=d2) right++;
//         else right--;
//         
//         for(int j=left; j<=right; j++) {
//            distMap[i][j] = true;
//            dist[4] += city[i][j];
//         }
//         
//         turn++;
//      }
      
      for(int i=0; i<=d1; i++) {
         distMap[x+i][y-i] = true;
         distMap[x+d2+i][y+d2-i] = true;
      }
      
      for(int i=0; i<=d2; i++) {
         distMap[x+i][y+i] = true;
         distMap[x+d1+i][y-d1+i] = true;
      }
      
      //1구역(dist[0])
      for(int i=0; i<x+d1; i++) {
         for(int j=0; j<=y; j++) {
            if(i<0 || j<0 || i>=N || j>=N) continue;
            if(distMap[i][j]) break;
            dist[0] += city[i][j];
         }
      }
      
      //2구역(dist[1])
      for(int i=0; i<=x+d2; i++) {
         for(int j=N-1; j>y; j--) {
            if(i<0 || j<0 || i>=N || j>=N) continue;
            if(distMap[i][j]) break;
            dist[1] += city[i][j];
         }
      }
      
      //3구역(dist[2])
      for(int i=x+d1; i<N; i++) {
         for(int j=0; j<y-d1+d2; j++) {
            if(i<0 || j<0 || i>=N || j>=N) continue;
            if(distMap[i][j]) break;
            dist[2] += city[i][j];
         }
      }
      
      //4구역(dist[3])
      for(int i=x+d2+1; i<N; i++) {
         for(int j=N-1; j>=y-d1+d2; j--) {
            if(i<0 || j<0 || i>=N || j>=N) continue;
            if(distMap[i][j]) break;
            dist[3] += city[i][j];
         }
      }
      
      dist[4] = totalSum;
      for(int i=0; i<4; i++) {
    	  dist[4] -= dist[i];
      }
      
      
      Arrays.sort(dist);
      
      answer = Math.min(answer, dist[4]-dist[0]);
   }
}