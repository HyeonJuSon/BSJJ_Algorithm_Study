import java.io.*;
import java.util.*;

public class Main {

	static int V, E;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(br.readLine());
		for (int t = 0; t < TC; ++t) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			V = Integer.parseInt(st.nextToken());
			E = Integer.parseInt(st.nextToken());
			// 인접 리스트 생성
			ArrayList<Integer>[] adj = new ArrayList[V];
			for (int i = 0; i < V; ++i) adj[i] = new ArrayList<Integer>();
			for (int i = 0; i < E; ++i) {
				st = new StringTokenizer(br.readLine(), " ");
				int from = Integer.parseInt(st.nextToken()) - 1;
				int to = Integer.parseInt(st.nextToken()) - 1;
				adj[from].add(to);
				adj[to].add(from);
			}

			// 이분 그래프인지 검사
			int[] isVisited = new int[V];
			Queue<Integer> bfsQ = new LinkedList<>();
			boolean isPossible = true;
			
			root: for (int i = 0; i < V; ++i) {
				if (isVisited[i] == 0) { // 아직 방문 전이라면
					bfsQ.offer(i); // 큐에 추가해주고
					isVisited[i] = 1; // 1번으로 마킹해준다.
				}				
				while (!bfsQ.isEmpty()) {
					int current = bfsQ.poll();
					int edgeLength = adj[current].size();
					for (int j = 0; j < edgeLength; ++j) {
						int otherVertex = adj[current].get(j);
						if (isVisited[otherVertex] == 0)
							bfsQ.offer(otherVertex);
						if (isVisited[current] == isVisited[otherVertex]) { // 같은 숫자면 이분 그래프 X
							isPossible = false; // flag 세우고
							break root; // 탈출
						}
						isVisited[otherVertex] = isVisited[current] == 1 ? 2 : 1; // 다른 값으로 마킹해준다
					}
				}
			}
			System.out.println(isPossible ? "YES" : "NO"); // 출력
		}
	}

}
