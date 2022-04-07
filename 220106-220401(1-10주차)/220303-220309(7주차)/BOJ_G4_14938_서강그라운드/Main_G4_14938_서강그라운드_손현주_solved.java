package gold;

import java.io.*;
import java.util.*;

public class bj_14938_G4 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken()); // 수색 범위
		int R = Integer.parseInt(st.nextToken()); // 길의 개수
		// 아이템
		int[] itemArr = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; ++i) // 아이템을 담아준다.
			itemArr[i] = Integer.parseInt(st.nextToken());
		// 길 정보
		int[][] map = new int[N][N];
		final int INF = 987654321;
		for(int i=0;i<N;++i) {
			Arrays.fill(map[i], INF);
		}
		for (int i = 0; i < R; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int val = Integer.parseInt(st.nextToken());
			map[from][to] = map[to][from] = val;
		}
		// fw
		for (int k = 0; k < N; ++k) {
			for (int i = 0; i < N; ++i) {
				for (int j = 0; j < N; ++j) {
					if (i == j || i == k || j == k)
						continue;
					if (map[i][j] > map[i][k] + map[k][j]) {
						map[i][j] = map[i][k] + map[k][j];
					}
				}
			}
		}
		// 1, 2, 3, ..., N에 떨어졋다고 가정
		int answer = -1;
		for(int i=0;i<N;++i) {
			int val = itemArr[i];
			for(int j=0;j<N;++j) {
				if(i!=j && map[i][j]!=INF) {
					if(map[i][j]<=M) {
						val += itemArr[j];
					}
				}
			}
			answer = Math.max(answer, val);
		}
		// answer 
		System.out.println(answer);
	}

}
