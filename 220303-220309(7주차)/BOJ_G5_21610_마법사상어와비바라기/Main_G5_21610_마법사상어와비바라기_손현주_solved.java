package gold;
import java.io.*;
import java.util.*;

public class bj_21610_G5 {

	static class Order { // 명령을 담을 클래스 
		int d, s;

		Order(int d, int s) {
			this.d = d;
			this.s = s;
		}
	}

	static int N, M, map[][];
	static Queue<Order> orders = new LinkedList<>();
	static int[][] dir = { { 0, -1, -1, -1, 0, 1, 1, 1 }, { -1, -1, 0, 1, 1, 1, 0, -1 } }; //8방향

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; ++j) {
				map[i][j] = Integer.parseInt(st.nextToken());// 물의 양을 입력한다.
			}
		}
		for (int i = 0; i < M; ++i) { // 명령 입력 구간
			st = new StringTokenizer(br.readLine(), " ");
			int d = Integer.parseInt(st.nextToken()) - 1; // 거리 배열 0부터라서 1빼준다.
			int s = Integer.parseInt(st.nextToken());
			orders.offer(new Order(d, s));
		} // input 끝
		System.out.println(process());
	}

	static int process() {
		// 1. 첫번 째 비구름은 ( N,1 ), ( N,2 ), ( N-1,1 ), ( N-1,2 )에 생긴다 ->배열값으로생각하면 다 1씩 뺀다.
		Queue<int[]> clouds = new LinkedList<>();
		clouds.offer(new int[] { N - 1, 0 });
		clouds.offer(new int[] { N - 1, 1 });
		clouds.offer(new int[] { N - 2, 0 });
		clouds.offer(new int[] { N - 2, 1 });
		// 2. 명령 수행하기
		while (!orders.isEmpty()) { // 전체 명령을 수행한다.
			Order now = orders.poll();// 현재 명령을 뽑아낸다.
			// 3. 모든 구름이 d방향으로 s칸 이동한다.
			int cloudLength = clouds.size();
			for (int i = 0; i < cloudLength; ++i) {// 구름 개수만큼 돌린다.
				int[] nCloud = clouds.poll();// 구름을 빼서
				for (int s = 0; s < now.s; ++s) { // s 거리만큼
					nCloud[0] += dir[0][now.d]; // x 좌표를 이동 시켜준다.
					nCloud[1] += dir[1][now.d]; // y 좌표를 이동 시켜준다.
					// 보정작업
					nCloud[0] = nCloud[0] < 0 ? nCloud[0] + N : (nCloud[0] > N - 1 ? nCloud[0] - N : nCloud[0]);
					nCloud[1] = nCloud[1] < 0 ? nCloud[1] + N : (nCloud[1] > N - 1 ? nCloud[1] - N : nCloud[1]);
				}
				// 3-1. 다시 큐에 추가
				clouds.offer(nCloud);
			}
			// 4. 비를 내린다.
			Queue<int[]> rains = new LinkedList<>();
			boolean[][] wasCloud = new boolean[N][N]; // 비구름 위치였던 곳을 기억해둘 불린 배열
			while (!clouds.isEmpty()) { // 각 구름위치에서 물을 뿌린다
				int[] nCloud = clouds.poll();// 구름하나를 뽑아서
				wasCloud[nCloud[0]][nCloud[1]] = true;// 비구름이었으면 위치 표시해둔다.
				++map[nCloud[0]][nCloud[1]];
				rains.offer(new int[] {nCloud[0], nCloud[1]});
			}
			// 5. 물 복사 버그
			while (!rains.isEmpty()) {
				int[] nRain = rains.poll();
				map[nRain[0]][nRain[1]] += getWaterCnt(nRain); // 물을 더해준다.
			}
			// 6. 다음 구름을 찾는다.
			for (int i = 0; i < N; ++i) {// 3번에서 구름이 아니었고, 2이상이면 구름이 되고 2를 빼준다.
				for (int j = 0; j < N; ++j) {
					if (map[i][j] >= 2 && !wasCloud[i][j]) {
						clouds.offer(new int[] { i, j });
						map[i][j] -= 2;
					}
				}
			}
		}
		// 7. 합을 반환해준다.
		return getWaterSum();
	}
	
	static int getWaterSum() { // 물의 합을 더해준다.
		int sum = 0;
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				sum += map[i][j];
			}
		}
		return sum;
	}
	
	static int getWaterCnt(int[] nCloud) { // 대각선 길이 -1 에 물이 몇개가 있는지
		int cnt = 0;
		for (int d = 1; d < 8; d += 2) { // 대각선은 1,3,5,7에있음
			int nx = dir[0][d] + nCloud[0];
			int ny = dir[1][d] + nCloud[1];
			if (isBoundary(nx, ny) && map[nx][ny] > 0)
				++cnt; // 범위 안이고 물이있으면 카운팅
		}
		return cnt;
	}

	static boolean isBoundary(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}