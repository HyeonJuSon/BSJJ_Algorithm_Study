# https://www.acmicpc.net/problem/1865

import sys

sys.stdin = open("input.txt")
# 참고 자료 : https://velog.io/@kimdukbae/알고리즘-벨만-포드-알고리즘-Bellman-Ford-Algorithm
# 테스트 케이스 T
T = int(input())

INF = int(1e9)

# 벨만-포드 알고리즘
def ans():
    distance[1] = 0
    # N 번 반복
    for i in range(N):
        # 정점 1번 부터 N+1번까지 시작점으로 순회
        for j in range(1, N + 1):
            # 연결된 정점의 번호와 비용
            for next, cost in graph[j]:
                if distance[next] > distance[j] + cost:
                    distance[next] = distance[j] + cost

                    if i == N - 1:
                        return True

    return False


for t in range(T):
    # 지점의 수 N, 도로의 수 M, 웜홀의 수 W
    N, M, W = list(map(int, input().split()))

    graph = [[] for _ in range(N + 1)]

    # 도로 정보, 방향이 없다
    # S,E 는 연결된 지점의 번호, T는 늘어나느 시간
    for _ in range(M):
        S, E, T = list(map(int, input().split()))
        graph[S].append((E, T))
        graph[E].append((S, T))

    # 웜홀 정보, 방향이 있다
    # S,E 는 연결된 지점의 번호, T는 줄어드는 시간
    for _ in range(W):
        S, E, T = list(map(int, input().split()))
        graph[S].append((E, -T))

    # 각 정점 최단거리
    distance = [INF] * (N + 1)

    # 음의 사이클 발생여부
    if ans():
        print("YES")
    else:
        print("NO")
