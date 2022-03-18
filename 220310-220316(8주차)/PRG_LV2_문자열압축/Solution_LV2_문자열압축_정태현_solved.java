import java.io.*;
import java.util.*;

public class Main {
	public static int solution(String s) {
        int answer = Integer.MAX_VALUE;
        
        //1글자일때
        if(s.length() == 1) return 1;
        
        //확인할 글자 기준길이
        for(int i=1; i<=s.length()/2; i++) {
            String temp = "";
            int sameCnt = 1;
            
            //초기설정
            String ans = "";
            
            //자르는 길이만큼 확인
            for(int j=0; j<s.length()/i; j++) {

                //같으면
                //j는 횟수라서 i를 곱해서 index 체크해줘야함
                if(s.substring(j*i, (j*i)+i).equals(temp)){
                    sameCnt++;
                    continue;
                } 
                
                //자릿수 2개 이상만 숫자붙임
                if(sameCnt>=2) {
                    ans = ans + sameCnt + temp;
                    sameCnt = 1;
                } else { //1개는 숫자없이
                    ans = ans + temp;
                }
                
                temp = s.substring(j*i, (j*i)+i);
            }
            //마지막꺼 못 붙인 거 붙임
            if(sameCnt>=2) {
                ans = ans + sameCnt + temp;
                sameCnt = 1;
            } else { //1개는 숫자없이
                ans = ans + temp;
            }
            
            //딱 안나누어 떨어질 때 뒤에 남은것 붙이기
            if(s.length()%i != 0) {
                ans += s.substring(s.length()-s.length()%i, s.length());
            }
            
            answer = Math.min(answer, ans.length());
        }
        
        
        
        return answer;
    }
	
	public static void main(String[] args) throws IOException {
		String s = "xababcdcdababcdcd";
		
		System.out.println(solution(s));
	}
}
