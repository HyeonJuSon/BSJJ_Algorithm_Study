import java.io.*;
import java.util.*;

public class Main {
   static int V, E;
   static ArrayList<Node> adjList;
   static int[] parent;
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      StringTokenizer st = null;
      
      st = new StringTokenizer(br.readLine(), " ");
      V = Integer.parseInt(st.nextToken());
      E = Integer.parseInt(st.nextToken());
      
      adjList = new ArrayList<>();
      
      for(int i=0; i<E; i++) {
         st = new StringTokenizer(br.readLine(), " ");
         int start = Integer.parseInt(st.nextToken());
         int end = Integer.parseInt(st.nextToken());
         int weight = Integer.parseInt(st.nextToken());
         adjList.add(new Node(start, end, weight));
      }
      
      parent = new int[V+1];
      for(int i=1; i<parent.length; i++) {
         parent[i] = i;
      }
      
      //간선 크기별로 정렬
      Collections.sort(adjList);
      
      int sum = 0;
      for(int i=0; i<adjList.size(); i++) {
         Node cur = adjList.get(i);
         
         //같은 부모라면? 사이클이 생기는 것 or 이미 연결 됨
         if(!sameParent(cur.start, cur.end)) {
            union(cur.start, cur.end);
            sum += cur.weight;
         }
         
      }
      
      System.out.println(sum);
      
      
   }
   
   public static boolean sameParent(int a, int b) {
      a = find(a);
      b = find(b);
      
      if(a==b) return true;
      return false;
   }
   
   //부모찾기
   public static int find(int a) {
      if(parent[a] == a) {
         return a;
      }
      
      //어딘가에 union 되어 있으면 타고 타고 가서 최상단의 부모를 찾
      return parent[a] = find(parent[a]);
   }
   
   public static void union(int a, int b) {
      a = find(a);
      b = find(b);
      
      //정렬이 되었으니 기준은 앞에 껄로 union 시키는
      if(a!=b) {
         parent[b] = a;
      }
   }
   
   static class Node implements Comparable<Node>{
      int start, end, weight;
      
      public Node(int start, int end, int weight) {
         this.start = start;
         this.end = end;
         this.weight = weight;
      }
      
      @Override
      public int compareTo(Node o) {
         return this.weight - o.weight;
      }
   }
}