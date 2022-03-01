import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	static int N, farNum, answer;
	static ArrayList<Node> adjList[];
	static boolean[] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		
		for(list s: )
		
		adjList = new ArrayList[N+1];
		for(int i=0; i<=N; i++) {
			adjList[i] = new ArrayList<>();
		}
		
		for(int i=0; i<N-1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int parent = Integer.parseInt(st.nextToken());
			int child = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			//양방향
			adjList[parent].add(new Node(child, weight));
			adjList[child].add(new Node(parent, weight));
		}
		farNum = 0;
		answer = 0;
		
		//최상단 부터 가장 먼 곳을 찾는다
		visited = new boolean[N+1];
		visited[1] = true;
		dfs(1, 0);
		
		//먼곳부터 가장 먼곳을 찾으면 된다
		answer = 0;
		visited = new boolean[N+1];
		visited[farNum] = true;
		dfs(farNum, 0);
		
		System.out.println(answer);
	}
	
	public static void dfs(int num, int sum) {
		if(sum > answer) {
			answer = sum;
			farNum = num;
		}
		
		for(Node node: adjList[num]) {
			if(visited[node.to]) continue;
			
			visited[node.to] = true;
			dfs(node.to, sum + node.weight);
		}
	}
	
	static class Node {
		int to, weight;
		
		public Node(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
	}
}
