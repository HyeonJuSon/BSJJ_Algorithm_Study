import java.io.*;
import java.util.*;

class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		String[] str = new String[N];
		//알파벳 빈도수 체크용
		int[] alp = new int[26];
		
		for(int i=0; i<N; i++) {
			str[i] = br.readLine();
		}
		
		//알파벳 빈도수대로 alp 배열에 숫자를 더함
		//자릿수대로 더한다
		//ex) ABC => A는 100, B는 10, C는 1
		for(int i=0; i<N; i++) {
			for(int j=str[i].length(); j>0; j--) {
				alp[(int)str[i].charAt(str[i].length()-j)-65] += (int)Math.pow(10, j-1);
			}
		}

		Arrays.sort(alp);
		int answer = 0;
		int nine = 9;
		//많은 빈도수(자릿수포함)를 가진 알파벳에 9부터 주는 방식
		for(int i=alp.length-1; i>=0; i--) {
			if(alp[i]!=0) {
				answer += alp[i] * nine;
				nine--;
			}
		}

		System.out.println(answer);
  	}

}
