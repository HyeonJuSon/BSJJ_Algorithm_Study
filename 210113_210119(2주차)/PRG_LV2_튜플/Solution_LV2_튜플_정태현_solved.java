package algorithm.programmers;

import java.io.*;
import java.util.*;

public class Solution_LV2_튜플_정태현_solved {
	public static int[] solution(String s) {
        // int[] answer = {};
        //{{ 제거
        s = s.substring(2);
        //}} 제거
        s = s.substring(0, s.length()-2);
        //-를 구분자로 세팅
        s = s.replace("},{", "-");
        //구분자 - 기준으로 split
        String[] sarr = s.split("-");
        
        //길이 기준 오름차순으로 정렬
        Arrays.sort(sarr, new Comparator<String>(){
            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        });
        
        //set으로 체크하면서 없는 원소 answer에 넣어주기
        HashSet<Integer> set = new HashSet<>();
        int[] answer = new int[sarr.length];
        for(int i=0; i<sarr.length; i++) {
            String[] temp = sarr[i].split(",");
            for(String st: temp) {
                int a = Integer.parseInt(st);
                // 원소 하나씩 늘어나니까 하나 add 하면 break
                if(!set.contains(a)) {
                    set.add(a);
                    answer[i] = a;
                    break;
                }
            }
        }
        
        //왜 안 되지?
//        HashSet<Integer> answer = new HashSet<>();
//        for(int i=0; i<sarr.length; i++) {
//            String[] temp = sarr[i].split(",");
//            for(String st: temp) {
//                int a = Integer.parseInt(st);
//                answer.add(a);
//            }
//        }
        
        return answer;
    }
	
	public static void main(String[] args) throws IOException {
		String str = "{{2},{2,1},{2,1,3},{2,1,3,4}}";
		System.out.println(Arrays.toString(solution(str)));
	}
}
