package gold;

import java.io.*;
import java.util.*;

public class bj_1916_G5 {

	static class Node implements Comparable<Node> {
		int to, w;

		Node(int to, int w) {
			this.to = to;
			this.w = w;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.w, o.w);
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		int[] d = new int[N];// 최소 비용
		ArrayList<ArrayList<Node>> adj = new ArrayList<>();
		int INF = 987654321;
		Arrays.fill(d, INF);
		for (int i = 0; i < N; ++i)
			adj.add(new ArrayList<>());
		StringTokenizer st = null;
		for (int i = 0; i < M; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int w = Integer.parseInt(st.nextToken());
			adj.get(from).add(new Node(to, w));
		}

		st = new StringTokenizer(br.readLine(), " ");
		int start = Integer.parseInt(st.nextToken()) - 1;
		int end = Integer.parseInt(st.nextToken()) - 1;

		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[] isVisited = new boolean[N];
		pq.offer(new Node(start, 0));
		d[start] = 0;

		while (!pq.isEmpty()) {
			Node current = pq.poll();
			int idx = current.to;
			if (isVisited[idx])
				continue;
			isVisited[idx] = true;
			for (Node node : adj.get(idx)) {
				if (isVisited[node.to])
					continue;
				
				if (d[node.to] > d[idx] + node.w) {
					d[node.to] = d[idx] + node.w;
					pq.offer(new Node(node.to, d[node.to]));
				}
			}
		}
		System.out.println(d[end]);
	}

}
