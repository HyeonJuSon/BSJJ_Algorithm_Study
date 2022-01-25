package algorithm.boj;
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

public class Main_G4_11657_타임머신_정태현_solved {
	static final int INF = 500 * 10000;
	static int N, M;
	static int[][] adj;
	static long[] dist;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken()); //정점
		M = Integer.parseInt(st.nextToken()); //간선 수
		
		adj = new int[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(adj[i], INF);
		}
		dist = new long[N];
		Arrays.fill(dist, INF);
		dist[0] = 0;
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			adj[a][b] = Math.min(adj[a][b], d);
		}
		
		if(BF()) {
			for (int i = 1; i < dist.length; i++) {
				if(dist[i]==INF) System.out.println(-1);
				else System.out.println(dist[i]);
			}
		} else {
			System.out.println(-1);
		}
		
	}
	
	public static boolean BF() {
		//Bellman-Ford
		
		//최대 간선 수는 N-1
		boolean flag = false;
		for (int n = 0; n < N-1; n++) {
			flag = false;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					//시작점을 포함하지 않은 음수 사이클은 해당 문제에서 음수사이클이 아님
					//따라서 값을 변경하지 않게 break한다
					//3 2
					//2 3 -2
					//3 2 -2
					if (dist[i] == INF) break;
					if (dist[i] + adj[i][j] < dist[j]) {
                        dist[j] = dist[i] + adj[i][j];
                        flag = true;
                    }
				}
			}
			
			if(!flag) break;
		}
		
		
		//한번 더 수행 (음수사이클 체크)
		if(flag) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					//시작점을 포함하지 않은 음수 사이클
					if (dist[i] == INF) break;
					if (dist[i] + adj[i][j] < dist[j]) return false;
				}
			}
		}
		
		return true;
	}
}
