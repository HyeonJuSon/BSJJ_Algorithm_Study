package gold;

import java.io.*;
import java.util.*;

public class BJ_16638_G1 {

	static int N;
	static int max;
	static String[] arr;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		max = Integer.MIN_VALUE; // 정답 값
		arr = new String[N];
		String input = br.readLine();
		// 01. 데이터 전처리
		for (int i = 0; i < input.length(); ++i) {
			arr[i] = input.charAt(i) + "";
		}
		// 02. 탐색
		// 최대 뽑을 수 있는 괄호를 뽑아낸다. 연산자 수가 짝수면 N / 4 개 홀수면 N/4+1
		int operCnt = N / 2;
		int target = operCnt % 2 == 0 ? operCnt / 2 : (operCnt / 2) + 1;
		// R == 0개일 때 계산
		calculate(null);
		// 그 외의 계산
		for (int R = 1; R <= target; ++R) {
			find(0, 1, R, new int[R]);
		}
		// 출력
		System.out.println(max);
	}

	static void calculate(int[] select) {
		// 임시배열에 복사
		String[] nowArr = new String[N];
		for (int i = 0; i < N; ++i)
			nowArr[i] = arr[i];
		// 1. 괄호부터 계산한다.
		if (select != null) {
			for (int i = 0; i < select.length; ++i) {
				if (select[i] != 0) {
					int selIdx = select[i]; // 뽑은 인덱스를 추출
					int front = selIdx - 1;
					int back = selIdx + 1;
					int val = 0;
					switch (nowArr[selIdx]) {
					case "+":
						val = Integer.parseInt(nowArr[front]) + Integer.parseInt(nowArr[back]);
						break;
					case "-":
						val = Integer.parseInt(nowArr[front]) - Integer.parseInt(nowArr[back]);
						break;
					case "*":
						val = Integer.parseInt(nowArr[front]) * Integer.parseInt(nowArr[back]);
						break;
					}
					nowArr[selIdx] = Integer.toString(val);
					nowArr[front] = nowArr[back] = null;
				}
			}
		}
		// 2. 곱하기를 계산한다.
		for (int i = 1; i < N; i += 2) {
			if (nowArr[i]!=null&&nowArr[i].equals("*")) { // 곱하기일때만 계산
				int L = i - 1, R = i + 1;
				while (L - 1 > 0 && nowArr[L] == null)
					--L;
				while (R + 1 < N && nowArr[R] == null)
					++R;
				nowArr[i] = Integer.toString(Integer.parseInt(nowArr[L]) * Integer.parseInt(nowArr[R]));
				nowArr[L] = nowArr[R] = null;
			}
		}
		// 3. 나머지를 계산한다.
		for (int i = 1; i < N; i += 2) {
			if (nowArr[i]==null) continue;
			if (nowArr[i].equals("+") || nowArr[i].equals("-")) {
				int L = i - 1, R = i + 1;
				while (L - 1 > 0 && nowArr[L] == null)
					--L;
				while (R + 1 < N && nowArr[R] == null)
					++R;
				if(nowArr[i].equals("+")) {
					nowArr[i] = Integer.toString(Integer.parseInt(nowArr[L]) + Integer.parseInt(nowArr[R]));
				}else if(nowArr[i].equals("-")) {
					nowArr[i] = Integer.toString(Integer.parseInt(nowArr[L]) - Integer.parseInt(nowArr[R]));
				}
				nowArr[L] = nowArr[R] = null;
			}
		}
		// 4. 총합을 계산한다.
		int sum = 0;
		for (int i = 0; i < N; ++i) {
			if (nowArr[i] != null)
				sum += Integer.parseInt(nowArr[i]);
		}
		// 5. 갱신
		max = Math.max(max, sum);
	}

	static void find(int cnt, int start, int R, int[] select) {
		if (cnt == R) {
			calculate(select);
			return;
		}

		for (int i = start; i < N; i += 2) {
			if (cnt > 0 && (select[cnt - 1] + 2) == i)
				continue;
			select[cnt] = i;
			find(cnt + 1, i + 2, R, select);
		}
	}

}
