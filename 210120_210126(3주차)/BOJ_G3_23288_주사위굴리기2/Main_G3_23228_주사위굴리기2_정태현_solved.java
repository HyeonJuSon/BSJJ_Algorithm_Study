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

public class Main_G3_23228_주사위굴리기2_정태현_solved {
	static int N, M, K, dir, r, c;
	static int[][] arr;
	static int[] dr = {-1, 0, 1, 0}; //상우하좌
	static int[] dc = {0, 1, 0, -1};
	static int[][] dice = { { 0, 2, 0, 0 },
							{ 4, 1, 3, 6 },
							{ 0, 5, 0, 0 },
							{ 0, 6, 0, 0 } };
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken()); //이동횟수
		arr = new int[N][M];
		//맵 구성
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//시작 지점 (0, 0)
		r = 0;
		c = 0;
		dir = 1; //처음에 우방향
		int answer = 0;
		for (int i = 0; i < K; i++) {
			dice(); //주사위 회전 및 연산
			answer += arr[r][c] * bfs();
		}
		
		System.out.println(answer);
	}
	
	public static void dice() {
		//move
		int nr = r + dr[dir];
		int nc = c + dc[dir];
		//갈 수 없다면
		if(nr < 0 || nc < 0 || nr >= N || nc >= M) {
			//반대방향으로
			dir = (dir+2) % 4;
			nr = r + dr[dir];
			nc = c + dc[dir];
		}
		r = nr;
		c = nc;
		
		//주사위 움직임(Solution)
		if (dir == 0) { // 상
			int tmp = dice[0][1];
			for (int i = 0; i < 3; i++) {
				dice[i][1] = dice[i + 1][1];
			}
			dice[3][1] = tmp;
			dice[1][3] = dice[3][1];
		} else if (dir == 1) {// 우
			int tmp = dice[1][3];
			for(int j = 3; j > 0; j--) {
				dice[1][j] = dice[1][j-1];
			}
			dice[1][0] = tmp;
			dice[3][1] = dice[1][3];
		} else if (dir == 2) {// 하
			int tmp = dice[3][1];
			for (int i = 3; i > 0; i--) {
				dice[i][1] = dice[i - 1][1];
			}
			dice[0][1] = tmp;
			dice[1][3] = dice[3][1];
		} else {// 좌
			int tmp = dice[1][0];
			for(int j = 0; j < 3; j++) {
				dice[1][j] = dice[1][j+1];
			}
			dice[1][3] = tmp;
			dice[3][1] = dice[1][3];
		}
		
		//아래면(dice[1][3])
		//방향회전
		if(dice[1][3] > arr[r][c]) dir = (dir + 1) % 4; //시계방향
		else if(dice[1][3] < arr[r][c]) {
			//반시계 회전
			if(dir!=0) dir--;
			else dir = 3;
		}
		//같으면 방향 그대로
	}
	
	public static int bfs() {
		Queue<Pos> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][M];
		visited[r][c] = true;
		int cnt = 0;
		q.offer(new Pos(r, c));
		
		while(!q.isEmpty()) {
			Pos cur = q.poll();
			cnt++; //poll할때 ++
			//사방
			for (int i = 0; i < dr.length; i++) {
				int nr = cur.row + dr[i];
				int nc = cur.col + dc[i];
				
				//갈수 없는 칸
				if(nr < 0 || nc < 0 || nr >= N || nc >= M) {
					continue;
				}
				
				if(!visited[nr][nc] && arr[nr][nc]==arr[r][c]) {
					q.offer(new Pos(nr, nc));
					visited[nr][nc] = true;
				}
			}
		}
		
		return cnt;
	}
	
	static class Pos {
		int row;
		int col;
		
		public Pos(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
}
