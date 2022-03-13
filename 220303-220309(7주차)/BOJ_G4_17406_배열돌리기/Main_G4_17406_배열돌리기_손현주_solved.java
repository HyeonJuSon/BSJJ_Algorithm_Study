package gold;

import java.io.*;
import java.util.*;

public class bj_17406_G4 {

	static class Order {
		int r, c, s;

		Order(int r, int c, int s) {
			this.r = r;
			this.c = c;
			this.s = s;
		}
	}

	static int N, M, K, map[][], answer = Integer.MAX_VALUE;
	static ArrayList<Order> list = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for (int i = 0; i < K; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			list.add(new Order(r, c, s));
		}
		input = new int[K];
		comb(0, new boolean[K]);
		System.out.println(answer);
	}

	static int[] input;

	static void rotate() {

		int[][] copy = new int[N][M];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < M; ++j) {
				copy[i][j] = map[i][j];
			}
		}
		for (int p = 0; p < K; ++p) {
			// 현재 뽑은 인덱스를 받아들인다.
			int pick = input[p];
			// 해당 인덱스의 r, c, s 를 불러온다.
			int r = list.get(pick).r;
			int c = list.get(pick).c;
			int s = list.get(pick).s;
			final int x = 0, y = 1;
			while (s > 0) {
				int[] start = { r - s, c - s };
				int[] end = { r + s, c + s };
				// 값을 저장해둔다.
				int tmp = copy[start[x]][start[y]];
				// 왼쪽 가장자리
				for (int i = start[x] + 1; i <= end[x]; ++i) {
					copy[i - 1][start[y]] = copy[i][start[y]];
				}
				// 아래쪽 가장자리
				for (int j = start[y] + 1; j <= end[y]; ++j) {
					copy[end[x]][j - 1] = copy[end[x]][j];
				}
				// 오른쪽 가장자리
				for (int i = end[x] - 1; i >= start[x]; --i) {
					copy[i + 1][end[y]] = copy[i][end[y]];
				}
				// 위쪽 가장자리
				for (int j = end[y] - 1; j > start[y]; --j) {
					copy[start[x]][j + 1] = copy[start[x]][j];
				}
				// 맨 처음에 임시로 저장해둔 값을 갱신한다.
				copy[start[x]][start[y] + 1] = tmp;
				// s 값 감소
				--s;
			}
		}
		// 계산
		for(int i=0;i<N;++i) {
			int sum = 0;
			for(int j=0;j<M;++j) {
				sum += copy[i][j];
			}
			answer = Math.min(answer, sum);
		}
	}

	static void comb(int cnt, boolean[] isVisited) {
		if (cnt == K) {
			// 뽑은 순서대로 배열을 회전시킨다.
			rotate();
			return;
		}

		for (int i = 0; i < K; ++i) {
			if (isVisited[i])
				continue;
			isVisited[i] = true;
			input[cnt] = i;
			comb(cnt + 1, isVisited);
			isVisited[i] = false;
		}
	}
}
