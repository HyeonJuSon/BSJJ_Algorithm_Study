import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_G4_1707_이분그래프_정태현_solved {
   static int V, E;
   static int[] color;
   static boolean flag;
   static LinkedList<Integer>[] arr;
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      StringTokenizer st = null;
      
      int T = Integer.parseInt(br.readLine());
      
      for(int tc=0; tc<T; tc++) {
    	 flag = true;
         st = new StringTokenizer(br.readLine(), " ");
         V = Integer.parseInt(st.nextToken()); //정점 수
         E = Integer.parseInt(st.nextToken()); //간선 수
         color = new int[V]; //색깔 체크용
         arr = new LinkedList[V];
         for(int i=0; i<V; i++) {
        	 arr[i] = new LinkedList<>();
         }
         int a = 0, b = 0;
         //간선 표시
         for(int i=0; i<E; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            a = Integer.parseInt(st.nextToken()) - 1;
            b = Integer.parseInt(st.nextToken()) - 1;
            arr[a].add(b);
            arr[b].add(a);
         }
         
         bfs();
         
         if(flag) System.out.println("YES");
         else System.out.println("NO");
      }
      
   }
   
   public static void bfs() {
      Queue<Integer> q = new LinkedList<>();
      
      for(int i=0; i<V; i++) {
    	  //색이 칠해지지않음
    	  if(color[i]==0) {
    		  q.offer(i);
    		  color[i] = 1;
    		  
    		  while(!q.isEmpty()) {
        		  int cur = q.poll();
        		  
        		  for(Integer n : arr[cur]) {
        			  if(color[n]==0) {
        				  q.offer(n);
        				  //다른 색깔 칠하기
        				  if(color[cur]==1) color[n] = 2;
        				  else color[n] = 1;
        			  } else {
        				  //그 색이 지금 cur의 색깔과 같은지 확인
        				  if(color[n]==color[cur]) {
        					  flag = false;
        					  return;
        				  }
        			  }
        		  }
        	  }
    	  }
    	  
      }
   }
   
}