import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution_LV3_단어변환_정태현_solved {
	static int answer = 51;
    static int len = 0;
    public static int solution(String begin, String target, String[] words) {
    	//단어 길이는 다 똑같다
    	len = begin.length();
        //dfs용 visited 배열
        boolean[] visited = new boolean[words.length];
        
        //맨 앞 인자에 단어 거쳐온 개수 출력
        dfs(0, visited, words, begin, target);
        
        if(answer==51) answer = 0;
        return answer;
    }
    
    public static void dfs(int cnt, boolean[] visited, String[] words, String str, String target) {
        //target 단어와 일치하면
    	if(str.equals(target)) {
            answer = Math.min(answer, min);
            return;
        }
        
        int diff = 0;
        for(int i=0; i<words.length; i++) {
            diff = 0;
            //알파벳 차이 갯수 계산
            for(int j=0; j<len; j++) {
                if(str.charAt(j)!=words[i].charAt(j)) diff++;
            }
            //찾아보지 않은 단어
            if(!visited[i] && diff==1) {
            	//visited 체크 해주지 않으면 무한 걸릴듯
                visited[i] = true;
                dfs(cnt+1, visited, words, words[i], target);
                visited[i] = false;
            }
            
        }
        
        return;
        
    }
	public static void main(String[] args) {
		String[] words = {"hot", "dot", "dog", "lot", "log", "cog"};
//		String[] words = {"hot", "dot", "dog", "lot", "log"};
		System.out.println(solution("hit", "cog", words));
	}
}
