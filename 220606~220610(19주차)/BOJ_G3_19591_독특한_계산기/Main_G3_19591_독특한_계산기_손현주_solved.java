package gold;

import java.io.*;
import java.util.*;

public class BJ_19591_G3 {

	static Deque<Long> number;
	static Deque<Character> oper;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		number = new ArrayDeque<>();
		oper = new ArrayDeque<>();
		// 입력
		String input = br.readLine();
		String tmp = input.charAt(0) + "";
		for (int i = 1; i < input.length(); ++i) {
			char now = input.charAt(i);
			if (now == '+' || now == '-' || now == '*' || now == '/') {
				number.add(Long.parseLong(tmp));
				oper.add(now);
				tmp = "";
			} else
				tmp += now;
		}
		number.add(Long.parseLong(tmp));
		// 계산
		while (true) {
			if(oper.size()==1) {
				calculate(0);
				break;
			}else if(oper.size()==0) break;
			// 우선순위를 계산한다.
			int idx = getPriority();
			calculate(idx);
		}
		System.out.println(number.peek());
	}

	static void calculate(int idx) {
		if(idx == 0) { // 앞
			number.addFirst(getCalc(number.pollFirst(), number.pollFirst(), oper.pollFirst()));
		}else { // 뒤
			Long A = number.pollLast();
			Long B = number.pollLast();
			number.addLast(getCalc(B, A, oper.pollLast()));
		}
	}
	
	static int getPriority() {
		int answer = -1;
		char front = oper.peekFirst();
		char back = oper.peekLast();
		
		// 다른 경우
		if ((front == '*' || front == '/') && (back == '+' || back == '-')) {
			answer = 0; // 앞쪽이 우선순위가 높음
		} else if ((back == '*' || back == '/') && (front == '+' || front == '-')) {
			answer = 1; // 뒤쪽이 우선순위가 높음
		} else { // 앞 뒤가 우선순위가 같다면
					// 잠시 숫자 2개를 빼서 담고 다시 넣어준다.
			Long[] frontVal = { number.pollFirst(), number.peekFirst() };
			number.addFirst(frontVal[0]);
			Long[] backVal = { number.pollLast(), number.peekLast() };
			number.addLast(backVal[0]);
			// 앞뒤를 계산
			Long frontCalc = getCalc(frontVal[0], frontVal[1], front);
			Long backCalc = getCalc(backVal[1], backVal[0], back);// 뒤로빼는거 순서주의!!
			if (frontCalc < backCalc) { // 뒤의 연산이 더 크면 뒤 연산
				answer = 1;
			} else { // 같거나 앞 연산이 더크면 앞 연산자
				answer = 0;
			}
		}
		return answer;
	}

	static Long getCalc(Long A, Long B, char oper) {
		Long answer = A;
		switch (oper) {
		case '*':
			answer *= B;
			break;
		case '/':
			answer /= B;
			break;
		case '+':
			answer += B;
			break;
		case '-':
			answer -= B;
			break;
		}
		return answer;
	}
}
