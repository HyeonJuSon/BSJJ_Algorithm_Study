package gold;

import java.io.*;
import java.util.*;

public class bj_1167_G3 {

	static class Node{
		int end, val;
		Node(int end, int val){
			this.end = end;
			this.val =val;
		}
	}
	static int V, dist[];
	static ArrayList<ArrayList<Node>> adj= new ArrayList<ArrayList<Node>>();
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		V = Integer.parseInt(br.readLine());
		for(int i=0;i<V;++i) adj.add(new ArrayList<Node>());
		for(int i=0;i<V;++i) {
			StringTokenizer st =new StringTokenizer(br.readLine()," ");
			int start = Integer.parseInt(st.nextToken())-1;
			while(true) {
				int end = Integer.parseInt(st.nextToken());
				if(end-- == -1) break;
				int val = Integer.parseInt(st.nextToken());
				adj.get(start).add(new Node(end,val));
			}
		}
		int firstIdx = bfs(0);
		int secondIdx = bfs(firstIdx);
		System.out.println(dist[secondIdx]);
	}

	static int bfs(int startIdx) {
		int max = 0;
		dist = new int[V];
		Queue<Node> bfs = new LinkedList<>();
		boolean[] isVisited=new boolean[V];
		bfs.add(new Node(startIdx, 0));
		
		while(!bfs.isEmpty()) {
			Node now = bfs.poll();
			if(isVisited[now.end])continue;
			isVisited[now.end]=true;
			for(Node n : adj.get(now.end)) {
				if(!isVisited[n.end]) {
					bfs.add(n);
					dist[n.end]= dist[now.end]+n.val;
					max = Math.max(max, dist[n.end]);
				}
			}
		}
		
		for(int i=0;i<V;++i) {
			if(dist[i]==max) {
				return i;
			}
		}
		return -1;
	}
}


