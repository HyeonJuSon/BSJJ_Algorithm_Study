import java.io.*;
import java.util.*;

public class bj_23291_G1 {

	static int N, K;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		int[] map = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; ++i)
			map[i] = Integer.parseInt(st.nextToken());
		int answer = 0;
		while (true) {
			// 물고기 수 차이가 K 이하가 되면 그만둔다.
			if(getDifference(map)<=K) break;
			// 횟수 카운팅
			++answer;
			// 물고기의 수가 가장 적은 어항에 물고기를 한 마리 넣는다.
			pushFish(map);
			// 가장 왼쪽에 있는 어항을 그 어항의 오른쪽에 있는 어항의 위에 올려 놓는다.
			int[][] leftUp = new int[2][map.length - 1];
			leftToUp(map, leftUp);
			// 2개 이상 쌓여있는 어항을 모두 공중 부양시킨 다음, 전체를 시계방향으로 90도 회전
			// 공중 부양시킨 어항 중 가장 오른쪽에 있는 어항의 아래에 바닥에 있는 어항이 있을때까지 반복한다.
			int[][] rotateMap = rotate90(leftUp);
			// 모든 인접한 두 어항에 대해서, 물고기 수의 차이를 구한다
			manage(rotateMap);
			// 어항을 바닥에 일렬로 놓아야 한다.
			map = oneLine(rotateMap);
			// 가운데를 중심으로 왼쪽 N/2개를 공중 부양시켜 전체를 시계 방향으로 180도 회전
			rotateMap = rotate180(map);
			// 다시 위에서 한 물고기 조절 작업을 수행하고, 바닥에 일렬로 놓는 작업을 수행
			manage(rotateMap);
			map = oneLine(rotateMap);
		}
		System.out.println(answer);
	}

	// 물고기의 수가 가장 적은 어항에 물고기를 한 마리 넣는다.
	static void pushFish(int[] map) {
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < map.length; ++i)
			min = Math.min(min, map[i]);
		for (int i = 0; i < map.length; ++i) {
			if (map[i] == min)
				++map[i];
		}
	}

	// 가장 왼쪽에 있는 어항을 그 어항의 오른쪽에 있는 어항의 위에 올려 놓는다.
	static void leftToUp(int[] map, int[][] leftUp) {
		leftUp[0][0] = map[0];
		for (int j = 0; j < leftUp[0].length; ++j)
			leftUp[1][j] = map[j + 1];
	}

	// 2개 이상 쌓여있는 어항을 모두 공중 부양시킨 다음, 전체를 시계방향으로 90도 회전
	static int[][] rotate90(int[][] leftUp) {
		int[][] answer = leftUp;
		while (true) {
			int targetY = 0;
			// 2개 이상인 열의 마지막 위치를 찾는다.
			for (int j = 0; j < answer[0].length; ++j) {
				if (answer[answer.length - 2][j] != 0)
					++targetY;
				else
					break;
			}
			if (answer.length > answer[0].length - targetY)
				break;
			int newX = targetY + 1;
			int newY = answer[0].length - targetY;
			int[][] tmp = new int[newX][newY];
			int startX = answer.length - 1; // 밑에서 위로
			int startY = 0; //
			boolean isLeftSide = true;
			for (int i = 0; i < newX; ++i) {
				for (int j = 0; j < newY; ++j) {
					if (isLeftSide) {
						if (startX >= 0)
							tmp[i][j] = answer[startX--][startY];
					} else {
						tmp[i][j] = answer[startX][startY++];
					}
				}
				startX = answer.length - 1;
				if (isLeftSide && ++startY >= targetY) {
					isLeftSide = false;
					startY = targetY;
					startX = answer.length - 1;
				}
			}
			answer = tmp;
		}
		return answer;
	}

	// 모든 인접한 두 어항에 대해서, 물고기 수의 차이를 구한다
	static int[][] dir = { { -1, 1, 0, 0 }, { 0, 0, -1, 1 } };
	static void manage(int[][] rotateMap) {
		int x = rotateMap.length, y = rotateMap[0].length;
		int[][] val = new int[x][y];
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				int now = rotateMap[i][j];
				if (now == 0)
					continue;
				for (int d = 0; d < 4; ++d) {
					int nx = i + dir[0][d];
					int ny = j + dir[1][d];
					if (0 <= nx && nx < x && 0 <= ny && ny < y) {
						if (rotateMap[nx][ny] == 0)
							continue;
						int next = rotateMap[nx][ny];
						int D = Math.abs(now - next) / 5;
						if (D > 0) {
							if (now < next) {
								val[i][j] += D;
							} else if (now > next) {
								val[i][j] -= D;
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < x; ++i) {
			for (int j = 0; j < y; ++j) {
				rotateMap[i][j] += val[i][j];
			}
		}
	}

	// 어항을 바닥에 일렬로 놓아야 한다.
	static int[] oneLine(int[][] manageMap) {
		Queue<Integer> line = new LinkedList<>();
		for (int j = 0; j < manageMap[0].length; ++j) {
			for (int i = manageMap.length - 1; i >= 0; --i) {
				if (manageMap[i][j] == 0)
					break;
				line.add(manageMap[i][j]);
			}
		}
		int[] answer = new int[line.size()];
		int idx = 0;
		while (!line.isEmpty())
			answer[idx++] = line.poll();
		return answer;
	}

	// 가운데를 중심으로 왼쪽 N/2개를 공중 부양시켜 전체를 시계 방향으로 180도 회전
	static int[][] rotate180(int[] map) {
		// 첫번째 회전
		int targetY = map.length / 2;
		int newX = 2, newY = map.length - targetY;
		int[][] first = new int[newX][newY];
		boolean isLeft = true;
		for (int i = 0; i < newX; ++i) {
			for (int j = 0; j < newY; ++j) {
				if (isLeft) {
					if (targetY >= 0) {
						first[i][j] = map[--targetY];
					}
				} else {
					first[i][j] = map[targetY++];
				}
			}
			if (targetY <= 0) {
				isLeft = false;
				targetY = map.length / 2;
			}
		}

		// 두 번째 회전
		// 왼쪽 영역의 맨 하단 좌측방향 진행
		int targetX = first.length - 1;
		targetY = first[0].length / 2 - 1;
		newX = first.length * 2; // 현 높이의 2배
		newY = first[0].length - targetY - 1;
		int[][] second = new int[newX][newY];
		isLeft = true;
		for (int i = 0; i < newX; ++i) {
			for (int j = 0; j < newY; ++j) {
				if (isLeft) {
					if (targetY >= 0)
						second[i][j] = first[targetX][targetY--];
				} else {
					if (targetY < first[0].length)
						second[i][j] = first[targetX][targetY++];
				}
			}
			if (isLeft) {
				targetY = first[0].length / 2 - 1;
				if (--targetX < 0) {
					isLeft = false;
					targetX = 0;
					targetY++;
				}
				;
			} else {
				targetX++;
				targetY = first[0].length / 2;

			}
		}
		return second;
	}
	// 물고기 수 차이가 K 이하가 되면 그만둔다.
	static int getDifference(int[] map) {
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for(int i=0;i<map.length;++i) {
			max = Math.max(max, map[i]);
			min = Math.min(min, map[i]);
		}
		return max-min;
	}
}
