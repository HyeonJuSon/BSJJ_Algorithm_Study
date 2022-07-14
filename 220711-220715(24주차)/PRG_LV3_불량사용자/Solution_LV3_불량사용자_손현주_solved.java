import java.util.*;
import java.util.regex.Pattern;

public class PRG_LV3_불량사용자 {

	static Ban[] ban;
	static int answer = 0;
	static int N;
	static HashSet<HashSet<String>> check;
	static int solution(String[] user_id, String[] banned_id) {
		// 1. 밴아이디별 가능한 맵핑문자열을 추가한다.
		N = banned_id.length;
		ban = new Ban[N];
		for (int i = 0; i < N; ++i) {
			ban[i] = new Ban(banned_id[i]);
			find(user_id, banned_id[i],i);
		}
		// 2. 중복 제외 가능한 것들 나열 
		check = new HashSet<HashSet<String>>();
		for(int i=0;i<ban[0].list.size();++i) {// 최 상단에서 dfs로 체크
			HashSet<String> visit = new HashSet<String>();
			visit.add(ban[0].list.get(i));
			dfs(1, visit, ban[0].list.get(i));
		}
		return check.size();
	}
	
	static void dfs(int depth, HashSet<String> visit, String log) {
		
		if(depth >= N) {
			// 나열 순서 상관 없이 중복 방지
			check.add(visit);
			return;
		}
		
		for(int i=0;i<ban[depth].list.size();++i) {
			String now = ban[depth].list.get(i); 
			if(visit.contains(now)) continue; // 이미 밴목록에 있으면 빼고
			HashSet<String> nowVisit = new HashSet<String>(visit);
			nowVisit.add(now);
			dfs(depth+1, nowVisit, log+now);
		}
	}
	
	static void find(String[] user_id, String target,int idx) {
		target = target.replace("*", ".");
		int uCnt = user_id.length;
		for (int i = 0; i < uCnt; ++i) {
			if(Pattern.matches(target,user_id[i])) {
				ban[idx].list.add(user_id[i]);
			}
		}
	}

	static class Ban {
		String target;
		ArrayList<String> list;

		Ban(String target) {
			this.target=target;
			list = new ArrayList<>();
		}
	}

	public static void main(String[] args) {
		String[] uid = { "frodo", "fradi", "crodo", "abc123", "frodoc" };
		String[] bid = { "fr*d*", "*rodo", "******", "******" };
		int a = solution(uid, bid);
		System.out.println(a);
	}

}
