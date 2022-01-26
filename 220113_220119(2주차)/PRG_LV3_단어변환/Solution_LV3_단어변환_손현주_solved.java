package Programmers;

public class prg_단어변환_LV3  {
	static int answer = 9999;

	static public int solution(String begin, String target, String[] words) {
		dfs(begin, target, words, new boolean[words.length], 0);
		return answer == 9999 ? 0 : answer;
	}

	static void dfs(String begin, String target, String[] words, boolean[] isVisited, int cnt) {
		// 각 단어별로 dfs 탐색을 해본다.
		for (int i = 0; i < words.length; ++i) {
			String now = words[i];
			if (isVisited[i])
				continue;
			int different = 0;  // 글자가 다른 개수
			for (int j = 0; j < now.length(); ++j) {
				if (begin.charAt(j) != now.charAt(j))
					++different;
			}
			if (different == 1) {
				if (target.equals(now) && answer > cnt + 1) {
					answer = cnt + 1;
					return;
				}
				isVisited[i] = true;
				dfs(now, target, words, isVisited, cnt + 1);
				isVisited[i] = false;
			}

		}
	}

	public static void main(String[] args) {
		String begin = "hit";
		String target = "cog";
		String[] words = {"hot", "dot", "dog", "lot", "log", "cog"};
		System.out.println(solution(begin, target, words));
	}

}
