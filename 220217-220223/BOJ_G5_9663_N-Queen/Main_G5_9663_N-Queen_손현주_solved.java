package gold;

import java.io.*;
import java.util.Arrays;

public class bj_9663_G5 {

	static boolean[][] isVisited;
	static int[][] map;
	static int answer = 0, N;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		isVisited = new boolean[N][N];
		map = new int[N][N];
		dfs(0);
		System.out.println(answer);
	}

	static void dfs(int i) {
		if (i == N) { // 개수만큼 다 놓았으면 return;
			++answer;
			return;
		}

		for (int j = 0; j < N; ++j) { // cnt 기준 열 이동하며 놓아본다
			if (isVisited[i][j])
				continue; // 방문 했으면 패스
			isVisited[i][j] = true;
			map[i][j] = 1;

			queenAttack(i, j);

			dfs(i + 1); // 다음 행 검사

			map[i][j] = 0; // 방금 놓았던 퀸을 빼고
			for(int x=0;x<N;++x)
				Arrays.fill(isVisited[x], false); // 초기화

			for (int x = 0; x < N; ++x) {// 이전위치의 퀸 공격 위치 복원
				for (int y = 0; y < N; ++y) {
					if (map[x][y] == 1)
						queenAttack(x, y);
				}
			}
		}
	}

	// 왼하, 하, 우하 만 보면 됨
	static void queenAttack(int x, int y) {
		// 하
		for (int i = x; i < N; ++i)
			isVisited[i][y] = true;
		// 왼하
		int xx = x, yy = y;
		while (0 <= xx && xx < N && 0 <= yy & yy < N)
			isVisited[xx++][yy--] = true;
		// 우하
		xx = x;
		yy = y;
		while (0 <= xx && xx < N && 0 <= yy & yy < N)
			isVisited[xx++][yy++]=true;
	}
}
