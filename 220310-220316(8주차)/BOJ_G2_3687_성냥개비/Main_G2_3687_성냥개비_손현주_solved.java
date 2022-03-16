package gold;

import java.util.Arrays;
import java.util.Scanner;

public class bj_3687_G2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int i = 0; i < T; ++i) {
			int N = sc.nextInt(); // 성냥개비 개수
			long[] minDP=new long[101];
			Arrays.fill(minDP, Long.MAX_VALUE);
			minDP[2]=1;
			minDP[3]=7;
			minDP[4]=4;
			minDP[5]=2;//2,3,5중 2가 가장 작음
			minDP[6]=6; // 0,6,9 중 0이 가장 작으나 맨 앞자리에 0이올수없으므로 6
			minDP[7]=8;
			minDP[8]=10; // 성냥 2개와 6개로 10을 만들수있다.
			//  최소값이기 때문에 3,5,9는 사용할 필요가 없음.
			String[] value = {"1","7","4","2","0","8"};
			for(int n=9;n<=100;++n) { // 성냥 9개부터 100개 까지 dp를 구축
				for(int j=2;j<=7;++j) { // 2~7까지만 한글자씩 만들 수 있음
					String str = minDP[n-j]+value[j-2]; //j-2인이유는 j최소값이2이기때문
					minDP[n]=Math.min(minDP[n], Long.parseLong(str));//최소값으로 갱신한다
				}
			}
			
			// 최대값 구하기
			StringBuilder max = new StringBuilder();
			// 홀, 짝별로 맨앞자리를 추가해준다.
			if(N%2==1) { // 성냥개수가 홀수면 311111...
				max.append("7");
			}else { // 짝수면 111111...
				max.append("1");
			}
			// 나머지 자리수를 채운다. 2개씩 소비되므로 N/2-1까지 (맨앞자리구한거빼야함)
			for(int c=0;c<N/2-1;++c) max.append("1");
			
			// 출력하기
			System.out.println(minDP[N]+" "+max.toString());
		}
	}

}
