import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution_LV2_최댓값과최솟값_정태현 {
//	public static String solution(String s) {
//        String answer = "";
//        PriorityQueue<Integer> minq = new PriorityQueue<>();
//        PriorityQueue<Integer> maxq = new PriorityQueue<>(Collections.reverseOrder());
//        
//        String[] str = s.split(" ");
//        int[] num = new int[str.length];
//        
//        for(int i=0; i<str.length; i++) {
//            num[i] = Integer.parseInt(str[i]);
//        }
//        
//        for(int i=0; i<num.length; i++) {
//            minq.offer(num[i]);
//            maxq.offer(num[i]);
//        }
//        
//        String min = Integer.toString(minq.poll());
//        String max = Integer.toString(maxq.poll());
//        
//        answer = min + " " + max;
//        return answer;
//    }
	public static String solution(String s) {
        String answer = "";
        ArrayList<String> list = new ArrayList<>();
        String[] str = s.split(" ");
        
        Arrays.sort(str, (a,b) -> Integer.compare(Integer.parseInt(a), Integer.parseInt(b)));
        
        answer = str[0] + " " + str[str.length-1];
        return answer;
    }
	
	public static void main(String[] args) {
		String s = "1 2 3 4";
		
		System.out.println(solution(s));
	}
}
