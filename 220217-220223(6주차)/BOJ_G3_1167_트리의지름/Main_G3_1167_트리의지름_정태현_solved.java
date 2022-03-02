import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static int N, farNum, answer;
	static ArrayList<Node> adjList[];
	static boolean[] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		
		adjList = new ArrayList[N+1];
		for(int i=0; i<=N; i++) {
			adjList[i] = new ArrayList<>();
		}
		
		
		for(int i=0; i<N; i++) {
			String[] str = br.readLine().split(" ");
			int node = Integer.parseInt(str[0]);
			
			int idx = 1;
			while(true) {
				//-1을 만나면 break
				if(Integer.parseInt(str[idx])==-1) break;
				
				int to = Integer.parseInt(str[idx]);
				int weight = Integer.parseInt(str[idx+1]);
				
				//양방향 체크
				adjList[node].add(new Node(to, weight));
				adjList[to].add(new Node(node, weight));
				
				//2개씩 체크하니까 +2씩
				idx = idx + 2;
			}
			
		}
		
		
		farNum = 0;
		answer = 0;
		
		bfs(1);
		bfs(farNum);
		
		System.out.println(answer);
	}
	
	public static void bfs(int num) {
		Queue<Node> q = new LinkedList<>();
		boolean[] visited = new boolean[N+1];
		
		//현재 노드, sum 값
		q.add(new Node(num, 0));
		
		visited[num] = true;
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			
			//현재 노드에서의 가장 먼 노드, 그 노드 까지의 거리 갱신
			if(cur.weight > answer) {
				farNum = cur.to;
				answer = cur.weight;
			}
			
			for(Node node: adjList[cur.to]) {
				if(visited[node.to]) continue;
				
				//방문하지 않은 노드 하나하나 sum 값 더해 주면서 bfs
				visited[node.to] = true;
				q.offer(new Node(node.to, cur.weight + node.weight));
			}
		}
		
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
