from collections import deque

N, M = list(map(int, input().split()))

graph = []
dy = [0, 0, 1, -1]
dx = [1, -1, 0, 0]


def is_inside(y, x):
    return -1 < y < N and -1 < x < M


def MeltIce():
    temp_list = []
    for y in range(N):
        for x in range(M):
            if graph[y][x] > 0:
                count = 0
                for d in range(4):
                    ny = y + dy[d]
                    nx = x + dx[d]

                    if not is_inside(ny, nx):
                        continue

                    if graph[ny][nx] == 0:
                        count += 1
                temp_list.append([y, x, count])

    for y, x, count in temp_list:
        graph[y][x] -= count
        if graph[y][x] < 0:
            graph[y][x] = 0


def bfs(y, x, visited):
    que = deque()
    que.append((y, x))
    visited[y][x] = True
    while que:
        y, x = que.popleft()

        for d in range(4):
            ny = y + dy[d]
            nx = x + dx[d]

            if not is_inside(ny, nx):
                continue

            if visited[ny][nx]:
                continue

            if graph[ny][nx] > 0:
                visited[ny][nx] = True
                que.append((ny, nx))


def CountIceberg():
    visited = [[False for _ in range(M)] for _ in range(N)]
    iceberg_count = 0
    ice_count = 0
    for y in range(N):
        for x in range(M):
            if graph[y][x] > 0:
                ice_count += 1

                if not visited[y][x]:
                    iceberg_count += 1
                    bfs(y, x, visited)

    return iceberg_count, ice_count


for y in range(N):
    row = list(map(int, input().split()))
    graph.append(row)

ans = 0
while True:
    ans += 1

    MeltIce()

    iceberg_count, ice_count = CountIceberg()

    if iceberg_count >= 2:
        break

    if ice_count == 0:
        ans = 0
        break

print(ans)
