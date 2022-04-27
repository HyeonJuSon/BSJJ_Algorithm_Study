import sys

sys.stdin = open("input.txt")

T = int(input())

for _ in range(T):
    # 위상 정렬 리스트

    # 건물 개수, 건설 순서 규칙
    n, k = list(map(int, input().split()))
    # 건물 건설 시간
    d_list = [0] + list(map(int, input().split()))

    # 인접 간선 그래프
    adj_graph = [[] for _ in range(n + 1)]

    # 인접 노드 수 저장 리스트
    in_degree = [0] * (n + 1)

    # 건설 순서 x,y
    for _ in range(k):
        x, y = list(map(int, input().split()))
        # 진출 노드 저장
        adj_graph[x].append(y)

        # 진입 노드 개수 갱신
        in_degree[y] += 1

    # 건설해야할 건물 번호 w
    w = int(input())

    # 각 노드 도달 시간 저장 리스트
    time_list = [0] * (n + 1)

    # 진입 노드 개수가 0인 노드를 저장하는 큐
    q = []

    # 진입 노드 개수가 0인 노드 찾아서 큐에 저장
    for i in range(1, n + 1):
        if in_degree[i] == 0:
            q.append(i)
            time_list[i] = d_list[i]

    while q:
        node = q.pop()
        # 현재 노드의 진출 노드 리스트
        adj_list = adj_graph[node]

        for adj_node in adj_list:
            in_degree[adj_node] -= 1
            # 현재 노드 최대 도달 시간 갱신
            time_list[adj_node] = max(time_list[adj_node], time_list[node] + d_list[adj_node])

            # 진입 노드가 개수가 0이 됐으면 큐에 저장
            if in_degree[adj_node] == 0:
                q.append(adj_node)

    print(time_list[w])
