package gold;

import java.io.*;
import java.util.*;

public class bj_11559_G4 {

	static char[][] map = new char[12][6];
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < 12 && 0 <= y && y < 6;
	}

	static void bfs(int x, int y, char color, boolean[][] isVisited, Queue<int[]> willBomb) {
		Queue<int[]> bfs = new LinkedList<>();
		Queue<int[]> save = new LinkedList<>();
		save.add(new int[] {x,y});
		bfs.add(new int[] { x, y });
		while (!bfs.isEmpty()) {
			int[] now = bfs.poll();
			for (int d = 0; d < 4; ++d) {
				int nx = now[0] + dir[0][d];
				int ny = now[1] + dir[1][d];
				if (isBoundary(nx, ny) && !isVisited[nx][ny] && map[nx][ny]==color) {
					isVisited[nx][ny] = true;
					bfs.add(new int[] {nx,ny});
					save.add(new int[] {nx,ny});
				}
			}
		}
	
		if(save.size()>=4) {
			while(!save.isEmpty()) {
				willBomb.add(save.poll());
				++bombCnt;
			}
		}
	}
	
	static void lineDown() {
		for(int i=10;i>=0;--i) {
			for(int j=0;j<6;++j) {
				if(map[i+1][j]=='.'&&map[i][j]!='.') {
					int targetX = i+1;
					while(true) {
						if(targetX +1 >=12 || map[targetX+1][j]!='.') break;
						++targetX;
					}
					map[targetX][j]=map[i][j];
					map[i][j]='.';
				}
			}
		}
	}
	
	static int bombCnt = 0;
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (int i = 0; i < 12; ++i) {
			String now = br.readLine();
			for (int j = 0; j < 6; ++j) {
				map[i][j] = now.charAt(j);
			}
		}

		int answer = 0;

		while (true) {

			boolean isBomb = false;
			boolean[][] isVisited = new boolean[12][6];
			Queue<int[]> willBomb = new LinkedList<>();
			bombCnt = 0; // 폭발예정인 뿌요 개수
			for (int i = 0; i < 12; ++i) {
				for (int j = 0; j < 6; ++j) {
					if (map[i][j] != '.') {
						isVisited[i][j] = true;
						bfs(i, j, map[i][j], isVisited, willBomb);
					}
				}
			}
			
			if(bombCnt > 0) { // 폭발 예정이라면 
				isBomb = true;
				while(!willBomb.isEmpty()) {
					int[] now = willBomb.poll();
					map[now[0]][now[1]]='.';
				}
				++answer;
			}
			
			if(isBomb) { // 폭발할 것이 있으면 
				lineDown(); // 내려준다.
				
			}else break; // 폭발할 것이 없으면 그만둔다.
			
		}
		System.out.println(answer);
	}

}

// 뿌요뿌요
/*
 * 같은 색 뿌요 4개 이상 상하좌우 -> 연쇄 폭발 폭발 되고나면 위에거 아래로 내려줌 또 터질 거있으면 2번째 연쇄 카운트 여러색깔의
 * 뿌요가 다터지고나야 위에서 아래로 내린다. R, G, B, P, Y
 * 
 * 1. 폭발 for문 돌려서 .이 아닌것을 찾는다. 해당 위치에서 bfs를 돌린다. (isVisited 배열은 유지한다. 연결되어있으면 이미
 * 돌았을 거기 때문에) bfs용 큐와, 마지막에 총 몇개를 방문했는지 담을 큐를 만든다. 후자의 큐 개수가 4개이상이면 폭발용 배열에
 * 치크해둔다. 다른 곳까지 다 돌고 폭발시킨다. 2. 내리기 폭발을 했었다는 플래그가 있다면 밑에서 위로 올라가면서 하나씩 내려준다.
 * 
 * 3. 보드 12행 6열로 고정되어있다.
 */
