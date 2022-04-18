package gold;

import java.io.*;
import java.util.*;

public class bj_17837_G2 {
	static class Horse {
		int dir, x, y;
		Horse(int dir, int x, int y) {
			this.dir = dir;
			this.x = x;
			this.y = y;
		}
	}

	static class Tower {
		int color;
		ArrayList<Integer> list;
		Tower(int color) {
			this.color = color;
			list = new ArrayList<Integer>();
		}
	}

	static int N, K;
	static Tower[][] map;
	static ArrayList<Horse> horses;
	static final int white = 0, red = 1, blue = 2;
	static int[][] direction = { { 0, 0, 0, -1, 1 }, { 0, 1, -1, 0, 0 } }; // 우,좌,상,하

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		map = new Tower[N][N];
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; ++j) {
				map[i][j] = new Tower(Integer.parseInt(st.nextToken()));
			}
		}
		horses = new ArrayList<>(); // 말의 정보를 담아둘 리스트
		for (int i = 0; i < K; ++i) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			horses.add(new Horse(d, x, y));
			map[x][y].list.add(i);
		}
		int answer = 0;
		while (true) {
			if (isClear())
				break;
			if (++answer > 1000)
				break;
			move();
		}
		System.out.println(answer > 1000 ? -1 : answer);
	}

	static void move() {
		// 순서대로 이동한다.
		for (int i = 0; i < K; ++i) {
			Horse now = horses.get(i); // 현재 말
			int nx = now.x + direction[0][now.dir];
			int ny = now.y + direction[1][now.dir];
			ArrayList<Integer> tmp = new ArrayList<>(); // 이동 위치의 갱신 후 리스트
			int nowLen = map[now.x][now.y].list.size();
			int target = map[now.x][now.y].list.indexOf(i); // 현재 말이 리스트의 몇 번째 위치에 들어있는가?
			if (!isBoundary(nx, ny) || map[nx][ny].color == blue) {
				now.dir = getReverseDir(now.dir); // 방향을 바꾸고
				nx = now.x + direction[0][now.dir];
				ny = now.y + direction[1][now.dir];
				if (!isBoundary(nx, ny) || map[nx][ny].color == blue) {
					continue; // 또 이동못하면 유지
				}
				if (map[nx][ny].color == red) {
					tmp = new ArrayList<>(map[now.x][now.y].list.subList(target, nowLen));
					Collections.reverse(tmp);
				} else {
					tmp = new ArrayList<>(map[now.x][now.y].list.subList(target, nowLen));
				}
			} else if (map[nx][ny].color == red) {
				tmp = new ArrayList<>(map[now.x][now.y].list.subList(target, nowLen));
				Collections.reverse(tmp);
			} else if (map[nx][ny].color == white) { // 그냥 바로 이동
				tmp = new ArrayList<>(map[now.x][now.y].list.subList(target, nowLen));
			}
			// 이동 안되는 원소는 유지
			map[now.x][now.y].list = new ArrayList<>(map[now.x][now.y].list.subList(0, target));
			// 이동할 말들을 갱신해준다.
			for (int idx : tmp) {
				map[nx][ny].list.add(idx);
				horses.get(idx).x = nx;
				horses.get(idx).y = ny;
			}
			// 턴 중에 끝날 수 있음
			if (isClear())
				break;
		}
	}

	static int getReverseDir(int d) {
		return d == 1 || d == 3 ? d + 1 : d - 1;
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}

	static boolean isClear() {
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (map[i][j].list.size() >= 4)
					return true;
			}
		}
		return false;
	}
}
