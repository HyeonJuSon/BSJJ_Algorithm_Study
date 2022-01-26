import sys

sys.stdin = open("input.txt")
# https://www.acmicpc.net/problem/11657


# 도시개수 N, 버스노선 M
N, M = list(map(int, input().split()))

minus_cycle = False

graph = [[] for _ in range(N + 1)]
for _ in range(M):
    A, B, C = list(map(int, input().split()))
    graph[A].append((B, C))

INF = 10e9

distances = [INF] * (N + 1)


def Bellman_Ford():
    distances[1] = 0

    for _ in range(N - 1):
        for now_node in range(1, N + 1):
            for next_node, cost in graph[now_node]:
                if distances[now_node] != INF and distances[now_node] + cost < distances[next_node]:
                    distances[next_node] = distances[now_node] + cost

    for now_node in range(1, N + 1):
        for next_node, cost in graph[now_node]:
            if distances[now_node] != INF and distances[now_node] + cost < distances[next_node]:
                return True

    return False


minus_cycle = Bellman_Ford()
if minus_cycle:
    print(-1)
else:
    for dist in distances[2:]:
        if dist == INF:
            print(-1)
        else:
            print(dist)
