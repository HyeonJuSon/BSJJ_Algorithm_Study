import java.io.*;
import java.util.*;

public class Soulition_programmers_level3_단어변환 {
	static int answer = Integer.MAX_VALUE;

	public static void main(String[] args) throws NumberFormatException, IOException {
		String begin = "hit";
		String target = "cog";
		String[] words = { "hot", "dot", "dog", "lot", "log", "cog" };
//		String[] words = { "hot", "dot", "dog", "lot", "log" };

		//////////////////////////////////////////////////////////////////
		boolean[] visited = new boolean[words.length];
		recursive(begin, target, words, 0, visited);
		if (Integer.MAX_VALUE == answer) answer = 0;
		System.out.println(answer);
	}

	private static void recursive(String s, String target, String[] words, int num, boolean[] visited) {
		// TODO Auto-generated method stub
		if (s.equals(target)) {
			answer = Integer.min(answer, num);
			return;
		}

		for (int i = 0; i < words.length; i++) {
			if (visited[i]) continue;
			int cnt = 0;
			for (int j = 0; j < words[i].length(); j++)
				if (s.charAt(j) == words[i].charAt(j))
					cnt++;

			if (cnt == s.length() - 1) {
				visited[i] = true;
				recursive(words[i], target, words, num + 1, visited);
				visited[i] = false;
			}
		}
	}
}