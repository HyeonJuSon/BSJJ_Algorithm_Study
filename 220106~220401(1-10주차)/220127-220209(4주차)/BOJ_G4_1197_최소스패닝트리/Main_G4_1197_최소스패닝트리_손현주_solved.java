package gold;
import java.io.*;
import java.util.*;
public class bj_1197_G4 {
	
	static class Edge implements Comparable<Edge>{
		int from, to, val;
		Edge(int from, int to, int val){
			this.from=from;
			this.to=to;
			this.val=val;
		}
		
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.val, o.val);
		}
	}
	static int[] parents;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine()," ");
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		parents = new int[V+1]; // Union-Find
		Edge[] edges = new Edge[E]; // Edge info
		for(int i=0;i<E;++i) {
			st = new StringTokenizer(br.readLine()," ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int val = Integer.parseInt(st.nextToken());
			edges[i] = new Edge(from,to,val);
		}
		// Kruskal Algorithm
		// sort by edge
		Arrays.sort(edges);
		// make parents
		for(int i=1;i<=V;++i) parents[i] = i;
		// link
		int cnt = 0, answer = 0;
		for(int i=0;i<E;++i) {
			// if no cycle
			if(union(edges[i].from, edges[i].to)) {
				// sum value
				answer += edges[i].val;
				// check
				if(++cnt == V-1) break;
			}
		}
		System.out.println(answer);
	}

	static boolean union(int a, int b) {
		int aParent = getParent(a);
		int bParent = getParent(b);
		if(aParent==bParent) return false;
		parents[bParent] = aParent;
		return true;
	}
	
	static int getParent(int a) {
		if(a == parents[a]) return a;
		return parents[a] = getParent(parents[a]);
	}
}
