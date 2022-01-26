package Programmers;

import java.util.*;

public class prg_이중우선순위큐_LV3 {
	static public int[] solution(String[] operations) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < operations.length; ++i) {
			StringTokenizer st = new StringTokenizer(operations[i], " ");
			String order = st.nextToken();
			int val = Integer.parseInt(st.nextToken());
			switch (order) {
			case "I":
				list.add(val);
				break;
			case "D":
				if (val == -1)
					Collections.sort(list);
				else
					Collections.sort(list, Collections.reverseOrder());
				if (list.size() > 0)
					list.remove(0);
				break;
			}
		}
		Collections.sort(list, Collections.reverseOrder());
		int max = 0, min = 0;
		if (list.size() > 0) {
			max = list.get(0);
			min = list.get(list.size() - 1);
		}
		int[] answer = { max, min };
		return answer;
	}

	public static void main(String[] args) {
		int[] answer = solution(new String[] { "I 16", "D 1" });
		System.out.println(answer[0] + ", " + answer[1]);
	}

}
