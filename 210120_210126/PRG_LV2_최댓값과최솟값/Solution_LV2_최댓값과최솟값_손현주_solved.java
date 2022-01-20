package Programmers;

public class prg_최댓값과최솟값_LV2 {
	static public String solution(String s) {
		String answer = "";
		String[] arr = s.split(" ");
		int max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; ++i) {
			int val = Integer.parseInt(arr[i]);
			max = Math.max(max, val);
			min = Math.min(min, val);
		}
		answer = min + " " + max;
		return answer;
	}

	public static void main(String[] args) {
		System.out.print("1 2 3 4");
	}

}
