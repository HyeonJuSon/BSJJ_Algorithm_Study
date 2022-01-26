package gold;

import java.io.*;
import java.util.*;

public class bj_11657_G4 {
	static class Node {
		int u, v, weight;
		Node(int u, int v, int weight) {
			this.u = u;
			this.v = v;
			this.weight = weight;
		}
	}

	static int N, M;
	static long[] dist;
	static Node[] edge;
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		dist = new long[N + 1];
		Arrays.fill(dist, INF);
		edge = new Node[M];
		for (int i = 0; i < M; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			edge[i] = new Node(u, v, weight);
		}
		if (bf(1))
			System.out.println(-1);
		else {
			for(int i=2; i<=N;++i) {
				if(dist[i]==INF) System.out.println(-1);
				else System.out.println(dist[i]);
			}
		}
	}

	static boolean bf(int start) {
		dist[start] = 0;
		for (int i = 1; i <= N; ++i) {
			for (int j = 0; j < M; ++j) {
				int u = edge[j].u;
				int v = edge[j].v;
				int w = edge[j].weight;

				if (dist[u] == INF)
					continue;
				if (dist[v] > dist[u] + w) {
					dist[v] = dist[u]+w;
					if(i==N) return true; // 음수 순환
				}
			}
		}
		return false;
	}
}
