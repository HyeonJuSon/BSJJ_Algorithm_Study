package gold;

import java.io.*;
import java.util.*;

public class bj_9466_G3 {

	static int N, map[], cnt;
	static boolean[] isVisited, isClear;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		while (--TC >= 0) {
			N = Integer.parseInt(br.readLine());
			map = new int[N + 1];
			isVisited = new boolean[N + 1];
			isClear = new boolean[N + 1];
			cnt = 0;

			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1; i <= N; ++i) {
				map[i] = Integer.parseInt(st.nextToken());
			}

			for (int i = 1; i <= N; ++i) {
				if (!isClear[i])
					dfs(i);
			}
			sb.append(N-cnt).append('\n');
		}
		System.out.println(sb.toString());
	}

	static void dfs(int idx) {
		if (isVisited[idx]) {
			isClear[idx] = true;
			++cnt;
		} else
			isVisited[idx] = true;

		int next = map[idx];

		if (!isClear[next])
			dfs(next);

		isVisited[idx] = false;
		isClear[idx] = true;
	}
}
