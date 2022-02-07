package Programmers;

public class prrg_프렌즈4블록_LV2 {
	// 자신, 우, 하, 하우
	static int[][] dir = { { 0, 0, 1, 1 }, { 0, 1, 0, 1 } };

	static public int solution(int m, int n, String[] board) {
		int answer = 0;
		final char empty = '@'; // 들어오는 값이 'A' ~ 'Z'임
		// board를 2차원 배열로 바꾼다.
		char[][] arr = new char[m][n];
		for (int i = 0; i < m; ++i) {
			String now = board[i];
			for (int j = 0; j < n; ++j) {
				arr[i][j] = now.charAt(j);
			}
		}
		// 더 이상 지울게 없을 때 까지 반복한다.
		while (true) {
			// 더이상 반복을 진행하지 않아도 되는지 여부
			boolean isLoop = false;
			// 1. 지워질 블록을 판별한다.
			boolean[][] erase = new boolean[m][n]; // 지워질 위치를 체크할 곳
			// 0,0에서 부터 시작한다.
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j < n; ++j) {
					char nowVal = arr[i][j]; // 현재 값
					if (nowVal == empty) continue; // 이미 터진 자리는 검사하지 않는다.
					boolean isBomb = true; // 폭발 체크
					for (int d = 1; d < 4; ++d) { // 자기자신은 뺴고 비교
						int ni = i + dir[0][d];
						int nj = j + dir[1][d];
						if (!(0 <= ni && ni < m && 0 <= nj && nj < n) || 
								nowVal != arr[ni][nj]) {
							isBomb = false; // 하나라도 범위 밖이거나 다르면 false
							break;
						}
					}
					if (isBomb) { // 폭발시켜야하는 위치라면
						for (int d = 0; d < 4; ++d) { // 폭발 배열에 체크해둔다.
							erase[i + dir[0][d]][j + dir[1][d]] = true;
						}
						isLoop = true; // 하나라도 폭발 시킬게 있으면 반복해야함
					}
				}
			}
			// 폭발 시킬 게 없으면 끝낸다.
			if (!isLoop) break;
			// 2. 블록을 지운다.
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j < n; ++j) {
					if (erase[i][j]) { // 지워야하는 거라면
						++answer; // 카운팅
						arr[i][j] = empty;// 지워준다.
					}
				}
			}
			// 3. 아래로 내린다.(밑에서부터 위로 탐색)
			for (int j = 0; j < n; ++j) {
				for (int i = m - 1; i >= 0; --i) {
					if (arr[i][j] == empty) { // 비어있다면
						// empty가 아닌 행까지 탐색
						int findIdx = i; // 현재 행을 담아서 위로 찾아가면서 바꿀 행을 찾음
						while (true) {
							if (findIdx - 1 < 0) break; // 범위 밖이면 더이상 찾을게 없다는 것
							findIdx -= 1; // 현재 행의 위로 올라간다.
							if (arr[findIdx][j] != empty) { // 비어있지 않은 곳을 찾았으면
								arr[i][j] = arr[findIdx][j]; // 현재 비어있는 곳에 찾은 곳의 값을 옮기고
								arr[findIdx][j] = empty; // 찾은 곳의 위치를 비워준다.
								break;
							}
						}
					}
				}
			}
		}
		return answer;
	}

	public static void main(String[] args) {
		System.out.println(solution(4, 5, new String[] { "CCBDE", "AAADE", "AAABF", "CCBBF" }));
		System.out.println(solution(7, 2, new String[] { "AA", "BB", "AA", "BB", "ZZ", "ZZ", "CC" }));
	}

}