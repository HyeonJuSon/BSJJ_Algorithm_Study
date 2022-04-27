package Programmers;

import java.io.*;
import java.util.*;

public class prg_문자열압축_LV2 {

	public static int solution(String s) {
		int N = s.length();
		int answer = N;

		for (int len = 1; len <= N/2; ++len) {
			String tmp = "";
			String base = "";
			int cnt = 0;
			boolean isFind = false;
			int lastIdx = -1;
			for (int i = 0; i <= N - len; i += len) {
				cnt = 0;
				lastIdx = i;
				base = s.substring(i, i + len);
				isFind = false;
				for (int j = i + len; j < N - len + 1; j += len) {
					String now = s.substring(j, j + len);
					if (base.equals(now)) {
						++cnt;
						isFind = true;
					} else {
						break;
					}
				}
				if (isFind) {
					tmp += Integer.toString(cnt + 1) + base;
					i += cnt * len ;
					lastIdx = i +len;
					isFind = false;
				} else {
					if (cnt == 0) {
						tmp += base;
						lastIdx = i+len;
					}
				}
			}
			if(isFind && lastIdx+len <= N)
				tmp += s.substring(lastIdx+1, N);
			else {
				for(int i=lastIdx;i<N;++i) tmp += s.charAt(i);
			}
			answer = Math.min(answer, tmp.length());
		}
		return answer;
	}

	public static void main(String[] args) {
		System.out.println(solution("aabbaccc"));
		System.out.println(solution("ababcdcdababcdcd"));
		System.out.println(solution("abcabcdede"));
		System.out.println(solution("abcabcabcabcdededededede"));
		System.out.println(solution("xababcdcdababcdcd"));
	}

}
