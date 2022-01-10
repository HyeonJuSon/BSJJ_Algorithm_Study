package algorithm.programmers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_LV2_캐시_정태현_X {
	public static int solution(int cacheSize, String[] cities) {
        int answer = 0;
        //1. 큐 선언
        Queue<String> q = new LinkedList<>();
        
        //2. for문으로 도시목록 순회
        int cnt = 0;
        for(int i=0; i<cities.length; i++) {
            boolean isPresent = false;
            cnt = 0;
            
            String temp = "";
            while(cnt < q.size()) {
                cnt++;
                
                String cur = q.poll();
                //찾는거면
                if(cities[i].equalsIgnoreCase(cur)) {
                    answer++;
                    isPresent = true;
                    temp = cur;
                } else { //찾는게 아니면
                    q.offer(cur);
                }
            }
            
            //일치하는 경우, 큐에 해당 도시를 젤 최근(맨뒤) 로 가져다놓아야 함
            if(isPresent) {
                q.offer(temp);
                continue;
            }
            
            //3-2. 일치하지 않을 경우
            if(q.size()==cacheSize) {
                q.poll();
                q.offer(cities[i]);
            } else {
                q.offer(cities[i]);
            }
            
            answer += 5;
        }
        return answer;
    }
	
	public static void main(String[] args) throws IOException {
		int cachesize = 0;
		String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"};
		System.out.println(solution(3, cities));
	}
}
