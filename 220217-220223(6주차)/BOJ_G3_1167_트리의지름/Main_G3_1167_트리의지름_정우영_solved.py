import sys

sys.stdin = open("input.txt")

# 정점의 개수
v = int(input())

graph = [[] for _ in range(v + 1)]
for _ in range(v):
    # 정점 번호, 간선 정보, -1은 마지막을 의미
    inputs = list(map(int, input().split()))

    # 정점 번호
    number = inputs[0]
    # 정점 번호, 거리
    edges = inputs[1:-1]

    for index in range(0, len(edges), 2):
        graph[number].append((edges[index], edges[index + 1]))


def dfs(node, total):
    global max_distance, max_distance_index

    if total > max_distance:
        max_distance = total
        max_distance_index = node

    for next_node, cost in graph[node]:
        if visit[next_node]:
            continue

        visit[next_node] = True
        dfs(next_node, total + cost)
        visit[next_node] = False


# 시작위치에서 가장 먼 거리와, 인덱스
max_distance, max_distance_index = 0, 0
# 노드 방문 여부
visit = [False] * (v + 1)
# 1번 노드부터 시작
visit[1] = True
dfs(1, 0)

# 가장 먼 거리의 인덱스에서 다시 가장 먼 거리의 인덱스 탐색
visit = [False] * (v + 1)
visit[max_distance_index] = True
dfs(max_distance_index, 0)
print(max_distance)
