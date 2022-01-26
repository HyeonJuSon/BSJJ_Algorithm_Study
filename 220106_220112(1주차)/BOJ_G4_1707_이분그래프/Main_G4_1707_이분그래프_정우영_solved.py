import sys

sys.stdin = open("input.txt")
# https://www.acmicpc.net/problem/1707

# 테스트 케이스 K
K = int(input())

# 테스트 케이스 순회
for _ in range(K):
    # 정점의 개수 V, 간선의 개수 E
    V, E = list(map(int, input().split()))

    # 그래프 변수
    graph = [[] for _ in range(V + 1)]

    # 정점 방문 체크
    visit = [0] * (V + 1)

    # 간선 정보
    # 1 부터 V 까지
    for _ in range(E):
        u, v = list(map(int, input().split()))
        # 정점 연결
        graph[u].append(v)
        graph[v].append(u)

    def dfs(start, start_group):
        # (시작 노드, 시작 집합)
        stack = [(start, start_group)]

        while stack:
            node, group = stack.pop()

            # 다음 노드 집합
            next_nodes = graph[node]

            for next_node in next_nodes:
                # 미방문 노드
                if visit[next_node] == 0:
                    # 현재 노드와 다른 집합 부여
                    visit[next_node] = group * -1
                    # 스택에 다음 노드 저장
                    stack.append((next_node, visit[next_node]))

                # 현재 노드와 같은 집합이면 False 반환
                elif visit[next_node] == group:
                    return False
        # True 반환
        return True

    # 결과 값
    ans = True

    # 모든 정점 순회
    for i in range(1, V + 1):
        # 방문한적 없는 정점에서 시작
        if visit[i] == 0:
            ans = dfs(i, 1)
            if ans == False:
                break
            
    if ans:
        print("YES")
    else:
        print("NO")
