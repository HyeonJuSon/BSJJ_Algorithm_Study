import java.io.*;
import java.util.*;

public class Main {
	static final int INF = Integer.MAX_VALUE;
	static int N, M;
	static int[] dist;
	static ArrayList<Node>[] adjList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine()); //정점 수
		M = Integer.parseInt(br.readLine()); //간선 수
		
		adjList = new ArrayList[N];
		for(int i=0; i<N; i++) {
			adjList[i] = new ArrayList<>();
		}
		
		dist = new int[N];
		Arrays.fill(dist, INF);
		
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int s = Integer.parseInt(st.nextToken()) - 1;
			int e = Integer.parseInt(st.nextToken()) - 1;
			int w = Integer.parseInt(st.nextToken());
			adjList[s].add(new Node(e, w));
		}
		st = new StringTokenizer(br.readLine(), " ");
		int start = Integer.parseInt(st.nextToken()) - 1;
		int end = Integer.parseInt(st.nextToken()) - 1;
		
		System.out.println(dijk(start, end));
		
	}
	
	public static int dijk(int start, int end) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[N];
		dist[start] = 0;
		pq.offer(new Node(start, 0));
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if(visited[cur.end]) continue;
			visited[cur.end] = true;
			
			for(Node node: adjList[cur.end]) {
				if(dist[node.end] > dist[cur.end] + node.wei) {
					dist[node.end] = dist[cur.end] + node.wei;
					pq.offer(new Node(node.end, dist[node.end]));
				}
			}
		}
		
		return dist[end];
	}
	
	static class Node implements Comparable<Node>{
		int end, wei;
		
		public Node(int end, int wei) {
			this.end = end;
			this.wei = wei;
		}
		
		@Override
		public int compareTo(Node o) {
			return this.wei - o.wei;
		}
	}
}
