package Programmers;

import java.util.*;

public class prg_캐시_LV2 {
	static public int solution(int cacheSize, String[] cities) {
		int answer = 0;
		int N = cities.length;
		if (cacheSize == 0) return 5 * N;
		HashSet<String> hs = new HashSet<>(); // 포함여부만 빠르게 찾기 위해 사용
		Deque<String> dq = new ArrayDeque<>();
		for (int i = 0; i < N; ++i) {
			String nowCity = cities[i].toLowerCase(); // 소문자로 맞추어준다.
			if (hs.contains(nowCity)) { // key값이 있다면
				answer += 1; // cash Hit 
				Deque<String> copy = new ArrayDeque<>(); // cash Hit이므로 최앞단으로 붙여준다.
				while (!dq.isEmpty()) {
					String val = dq.pollLast();
					if (!val.equals(nowCity)) copy.addFirst(val);
				}
				copy.addFirst(nowCity);
				while (!copy.isEmpty()) dq.addLast(copy.pollFirst());
			} else { // 키 값이 없다면 ( 캐시 비어있으면 바로 삽입, 꽉찼으면 최뒷단(참조오래안된거)뺀다 )
				if (!dq.isEmpty() && dq.size() >= cacheSize) hs.remove(dq.pollLast());
				dq.addFirst(nowCity);
				hs.add(nowCity);
				answer += 5; // cash Miss
			}
		}
		return answer;
	}

	public static void main(String[] args) {
		System.out.println(solution(3,
				new String[] { "Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"

				}));

	}

}
