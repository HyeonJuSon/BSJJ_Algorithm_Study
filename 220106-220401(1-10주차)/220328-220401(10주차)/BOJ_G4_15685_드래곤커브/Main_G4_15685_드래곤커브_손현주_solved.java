package gold;

import java.io.*;
import java.util.*;

public class bj_15685_G4 {

	static boolean[][] isVisited = new boolean[101][101];
	static int N;
	static int[][] dir = { { 0, -1, 0, 1 }, { 1, 0, -1, 0 } }; // 우,상,좌,하

	static ArrayList<Integer> getDirections(int d, int g) {
		ArrayList<Integer> answer = new ArrayList<>();
		answer.add(d); // 초기 방향
		while (g-- > 0) { // 최초 세대까지 돌리고 
			int start = answer.size() - 1; // 맨마지막(=바로직전세대)부터 회전(거슬러올라가기 때문에 반시계)시킨다.
			for (int i = start; i >= 0; --i) { 
				int nd = (answer.get(i) + 1) % 4; // 3 + 1 -> 0
				answer.add(nd); // 추가
			}
		}
		return answer;
	}
	
	static void markEdge(int x, int y, ArrayList<Integer> directions) {
		isVisited[x][y] = true;
		for(int d : directions) {
			if(d==0) {
				isVisited[++x][y]=true;
			}else if(d==1) {
				isVisited[x][--y]=true;
			}else if(d==2) {
				isVisited[--x][y]=true;
			}else {
				isVisited[x][++y]=true;
			}
		}
	}
	
	static boolean isSquare(int x, int y) {
		return isVisited[x][y] && isVisited[x+1][y] && isVisited[x][y+1] && isVisited[x+1][y+1];
	}
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = null;
		for (int i = 0; i < N; ++i) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int g = Integer.parseInt(st.nextToken());
			// 1. g세대 부터 최초의 세대까지 방향을 다 얻어온다.
			ArrayList<Integer> directions = getDirections(d, g);
			// 2. x,y위치에서 방향별 꼭지점을 마킹한다.
			markEdge(x,y,directions);
		}
		// 3. 사각형의 개수를 센다.
		int cnt =0;
		for(int i=0;i<100;++i) {
			for(int j=0;j<100;++j) {
				if(isSquare(i,j)) ++cnt;
			}
		}
		System.out.println(cnt);
	}

}
