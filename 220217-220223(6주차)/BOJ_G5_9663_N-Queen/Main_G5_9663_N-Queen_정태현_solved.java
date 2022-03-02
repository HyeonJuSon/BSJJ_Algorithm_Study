import java.io.*;
import java.util.*;

public class Main {
	static int[] arr;
	static int N;
	static int cnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = null;
		
		N = Integer.parseInt(br.readLine());
		arr = new int[N];
		cnt = 0;
		queen(0);
		
		System.out.println(cnt);
	}
	
	public static void queen(int depth) {
		if(depth==N) {
			cnt++;
			return;
		}
		
		for(int i=0; i<N; i++) {
			arr[depth] = i;
			
			if(check(depth)) {
				queen(depth+1);
			}
		}
	}
	
	public static boolean check(int depth) {
		for(int i=0; i<depth; i++) {
			if(arr[depth] == arr[i]) {
				return false;
			}
			
			if(Math.abs(depth - i) == Math.abs(arr[depth] - arr[i])) {
				return false;
			}
		}
		
		return true;
		
	}
}
