import sys

sys.stdin = open("input.txt")

import heapq

# 지역의 개수, 수색범위, 길의 개수
n, m, r = list(map(int, input().split()))

# 각 구역의 아이템 수
ts = [0] + list(map(int, input().split()))

# 양방향 그래프
graph = [[] for _ in range(n + 1)]

# 지역번호 a,b 와 길의 길이 l
for _ in range(r):
    a, b, l = list(map(int, input().split()))
    graph[a].append((b, l))
    graph[b].append((a, l))

INF = 10e9

# 다익스트라, 각 노드까지의 최단 거리 계산
def dijkstra(start):
    distance = [INF] * (n + 1)
    distance[start] = 0

    que = []
    heapq.heappush(que, (0, start))
    while que:
        now_cost, now_node = heapq.heappop(que)

        if distance[now_node] < now_cost:
            continue

        for next_node, next_cost in graph[now_node]:
            if next_cost + now_cost < distance[next_node]:
                distance[next_node] = next_cost + now_cost
                heapq.heappush(que, (distance[next_node], next_node))

    return distance


ans = 0

# 각 노드를 시작점으로 다른 노드까지의 최단 거리 계산
for i in range(1, n + 1):

    # 다른 노드 까지의 최단 거리 계산
    distance = dijkstra(i)

    # 현재 노드에서 얻을 수 있는 아이템 개수 계산
    total_cost = 0
    for index in range(len(distance)):
        # 수색 범위 안의 노드라면 아이템 개수 추가
        if distance[index] <= m:
            total_cost += ts[index]

    # 최댓값 갱신
    ans = max(ans, total_cost)

print(ans)
