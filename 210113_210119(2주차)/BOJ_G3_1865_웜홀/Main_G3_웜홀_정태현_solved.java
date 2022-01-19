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

public class Main_G_1_name_정태현 {
	static final int INF = Integer.MAX_VALUE;
	static int N, M, W;
	static int[][] adj;
	static int[] dist;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 0; tc < T; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken()); //정점
			M = Integer.parseInt(st.nextToken()); //양수 간선
			W = Integer.parseInt(st.nextToken()); //음수 간선
			adj = new int[N+1][N+1];
			for (int i = 1; i <= N; i++) {
				Arrays.fill(adj[i], INF);
			}
			
			dist = new int[N + 1];
			Arrays.fill(dist, INF);
			dist[0] = 0;
			dist[1] = 0;
			
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				adj[a][b] = Math.min(adj[a][b], Integer.parseInt(st.nextToken()));
				adj[b][a] = adj[a][b];
			}
			for (int i = 0; i < W; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int a = Integer.parseInt(st.nextToken()) - 1;
				int b = Integer.parseInt(st.nextToken()) - 1;
				adj[a][b] = Integer.parseInt(st.nextToken()) * -1;
			}
			
			
			if(BF()) System.out.println("YES");
			else System.out.println("NO");
		}
	}
	
	public static boolean BF() {
		
		
		//Bellman-Ford
		
		//최대 간선 수는 N-1
		boolean flag = false;
		for (int n = 0; n < N-1; n++) {
			flag = false;
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
//					//갈 수 있으면
//					if(adj[j][k] != INF) {
//						//갱신
//						dist[k] = Math.min(dist[j] + adj[j][k], dist[k]);
//						flag = true;
//					}
					if (dist[i] + adj[i][j] < dist[j]) {
                        dist[j] = dist[i] + adj[i][j];
                        flag = true;
                    }
				}
			}
			
			if(!flag) break;
		}
		
		
		//한번 더 수행
		if(flag) {
			for (int i = 1; i <= N; i++) {
				for (int j = 1; j <= N; j++) {
					if (dist[i] + adj[i][j] < dist[j]) return true;
				}
			}
		}
		
		return false;
	}
}
