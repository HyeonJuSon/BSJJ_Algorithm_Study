package gold;

import java.io.*;
import java.util.*;

public class BJ_17298_G4 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		Stack<Integer> index = new Stack<>();
		int[] answer = new int[N];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; ++i) {
			answer[i] = Integer.parseInt(st.nextToken());
		}

		for (int i = 0; i < N; ++i) {
			while (!index.isEmpty() && answer[index.peek()] < answer[i]) {
				answer[index.pop()] = answer[i];
			}
			index.push(i);
		}                     
		
		while (!index.isEmpty()) answer[index.pop()] = -1;
		
		StringBuilder sb = new StringBuilder(); 
		for (int a : answer) sb.append(a).append(' ');
		
		System.out.println(sb);
	}

}
