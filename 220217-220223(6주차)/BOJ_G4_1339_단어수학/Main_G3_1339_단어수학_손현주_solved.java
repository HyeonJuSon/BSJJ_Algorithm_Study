package gold;
import java.io.*;
import java.util.*;
public class bj_1339_G3 {

   static int N, alpha[]=new int[26];
   public static void main(String[] args) throws Exception {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      N = Integer.parseInt(br.readLine());
      
      for(int i=0;i<N;++i) {
    	  String input = br.readLine();
    	  int len = input.length();
    	  int val = (int)Math.pow(10, len-1);
    	  
    	  for(int j=0;j<len;++j) {
    		  alpha[input.charAt(j)-'A']+=val;
    		  val/=10;
    	  }
      }
      
      Arrays.sort(alpha);
      int max = 0;
      for(int x= 25; x>=17; --x) {
    	 max += alpha[x] * (x-16);
      }
      
      System.out.println(max);
   }

   
}