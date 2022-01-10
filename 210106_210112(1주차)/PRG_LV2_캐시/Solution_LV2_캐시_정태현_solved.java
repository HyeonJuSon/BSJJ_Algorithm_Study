package algorithm.programmers;

import java.io.IOException;
import java.util.LinkedList;

public class Solution_LV2_캐시_정태현_solved {
	public static int solution(int cacheSize, String[] cities) {
        int answer = 0;
        
        //예외: cacheSize가 0이면 계속 cache miss
        if(cacheSize==0) return 5 * cities.length;
        
        //1. 연결리스트 선언
        LinkedList<String> list = new LinkedList<>();
        
        //2. for문으로 도시목록 순회
        for(int i=0; i<cities.length; i++) {
            String city = cities[i].toUpperCase();
            //3. cache hit, cache miss 판별
            //list 구조 --> first(오래된값 ... 최근값)last
            
            //값이 있다, cache hit
            if(list.remove(city)) { //LinkedList에서 remove에 value 값을 주면 값의 유무에 따라 boolean 값을 출력한다
                list.addLast(city);
                answer++;
            } else { //값이 없음, cache miss + 캐시에 추가
                //3-1. 캐쉬가 꽉 찼는지 여부 판별
                if(list.size()==cacheSize) {
                    list.removeFirst();
                } 
                
                list.addLast(city);
                answer += 5;
            }
        }
        return answer;
    }
	
	public static void main(String[] args) throws IOException {
		int cachesize = 0;
		String[] cities = {"Jeju", "Pangyo", "Seoul", "NewYork", "LA", "Jeju", "Pangyo", "Seoul", "NewYork", "LA"};
		System.out.println(solution(3, cities));
	}
}
