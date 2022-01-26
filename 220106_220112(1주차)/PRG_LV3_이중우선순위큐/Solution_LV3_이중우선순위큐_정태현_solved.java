import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution_LV3_이중우선순위큐_정태현_solved {
	public static int[] solution(String[] operations) {
        int[] answer = new int[2];
        //maxq = 내림차순, minq = 오름차순
        PriorityQueue<Integer> maxq = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> minq = new PriorityQueue<>();
        
        //1. 명령어에 따라 큐 작업 수행
        for(int i=0; i<operations.length; i++) {
            String[] str = operations[i].split(" ");
            if(str[0].equals("I")) { //삽입
                maxq.offer(Integer.parseInt(str[1]));
                minq.offer(Integer.parseInt(str[1]));
            } else { //D
                if(!maxq.isEmpty()) {
                    if(str[1].equals("1")) { //최대값 삭제
                    int max = maxq.peek();
                    maxq.remove(max); //remove?????
                    minq.remove(max);
                    
                    } else { //최소값 삭제
                        int min = minq.peek();
                        maxq.remove(min);
                        minq.remove(min);
                    }
                }
                
            }
        }
        
        //2. 최대값, 최소값 출력
        if(maxq.size() != 0) answer[0] = maxq.poll();
        else answer[0] = 0;
        if(minq.size() != 0) answer[1] = minq.poll();
        else answer[1] = 0;
        
        return answer;
    }
	
	public static void main(String[] args) {
//		String[] operations = {"I 16", "I -5643", "D -1", "D 1", "D 1", "I 123", "D -1"};
		String[] operations = {"I -45", "I 653", "D 1", "I -642", "I 45", "I 97", "D 1", "D -1", "I 333"};
		System.out.println(Arrays.toString(solution(operations)));
	}

}
