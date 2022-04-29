import java.io.*;
import java.util.*;

public class bj_20056_G4 {

	static int N, M, K;
	static ArrayList<FireBall> fireballs;
	static int[][] count;
	static boolean gameOver;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		// fireball 입력받는다.
		fireballs = new ArrayList<>();
		count = new int[N][N];
		gameOver = false;
		for (int i = 0; i < M; ++i) { // 파볼 입력
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			fireballs.add(new FireBall(x, y, m, s, d));
		}
		// K 만큼 수행
		for (int i = 0; i < K; ++i) {
			count = new int[N][N];
			// 1. 모든 파이어 볼을 이동시킨다.
			moveFireBalls();
			// 2. 분열
			sprayFireBalls();
		}
		// 질량의 합 출력
		int answer = 0;
		for (FireBall f : fireballs) {
			answer += f.m;
		}
		System.out.println(answer);
	}

	static void sprayFireBalls() {
		ArrayList<FireBall> newFB = new ArrayList<>();
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (count[i][j] >= 2) { // 카운트가 2이상이면
					int fCnt = 0; // 파볼 갯수
					int fMassSum = 0; // 파볼들의 질량의 합
					int fSpeedSum = 0; // 파볼들의 스피드 합
					int fDirCheck[] = new int[8];
					for (int c=0;c<fireballs.size();++c) {
						FireBall f = fireballs.get(c);
						if (f.x == i && f.y == j) {
							++fCnt;
							fSpeedSum += f.s;
							fDirCheck[f.d]++;
							fMassSum += f.m;
							fireballs.remove(c);
							--c;
						}
					}

					int nm = fMassSum / 5;
					if (nm == 0)
						continue;
					int ns = fSpeedSum / fCnt;
					int nd = getDir(fDirCheck);
					for (int nf = 0; nf < 4; ++nf) {
						newFB.add(new FireBall(i, j, nm, ns, nd));
						nd += 2;
					}
				}
			}
		}
		for(int i=0;i<newFB.size();++i) {
			FireBall now = newFB.get(i);
			fireballs.add(new FireBall(now.x,now.y,now.m,now.s,now.d));
		}
	}

	static int getDir(int[] tmp) {
		boolean o = false, e = false;
		for (int i = 0; i < 8; ++i) {
			if ((i + 1) % 2 == 0 && tmp[i] != 0)
				e = true;
			else if ((i + 1) % 2 != 0 && tmp[i] != 0)
				o = true;
		}
		return o && e ? 1 : 0;
	}

	static int[][] dir = { { -1, -1, 0, 1, 1, 1, 0, -1 }, { 0, 1, 1, 1, 0, -1, -1, -1 } };

	static void moveFireBalls() {
		ArrayList<FireBall> tmp = new ArrayList<>();
		for (int i = 0; i < fireballs.size(); ++i) {
			FireBall now = fireballs.get(i);
			int nx = (now.x + N + (dir[0][now.d] * (now.s % N))) % N;
			int ny = (now.y + N + (dir[1][now.d] * (now.s % N))) % N;
			count[nx][ny]++;
			tmp.add(new FireBall(nx, ny, now.m, now.s, now.d));
		}
		fireballs = tmp; // 갱신.
	}

	static class FireBall {
		int x, y;
		int m;
		int s;
		int d;

		public FireBall(int x, int y, int m, int s, int d) {
			this.x = x;
			this.y = y;
			this.m = m;
			this.s = s;
			this.d = d;
		}

	}

}
