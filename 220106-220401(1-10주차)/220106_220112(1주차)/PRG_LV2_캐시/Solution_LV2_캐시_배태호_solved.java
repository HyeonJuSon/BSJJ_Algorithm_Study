import java.io.*;
import java.util.*;

public class Solution_LV2_캐시 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		int cacheSize = 3;
		String[] cities = { "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul", "Jeju", "Pangyo", "Seoul" };

		//////////////////////////////////////////////////////////////////
		int answer = 0;
		Queue<String> q = new LinkedList<String>();

		for (int i = 0; i < cities.length; i++) {
			if (q.contains(cities[i].toLowerCase())) { // 캐시에 있으면
				q.remove(cities[i].toLowerCase());
				q.add(cities[i].toLowerCase());
				answer += 1;
			} else { // 캐시 없으면
				if (q.size() >= cacheSize) //캐시가 꽉차있으면
					q.poll();
				if (cacheSize != 0) // 캐시사이즈가 0이 아니면
					q.add(cities[i].toLowerCase());
				answer += 5;
			}
		}
		System.out.println(answer);
	}
}