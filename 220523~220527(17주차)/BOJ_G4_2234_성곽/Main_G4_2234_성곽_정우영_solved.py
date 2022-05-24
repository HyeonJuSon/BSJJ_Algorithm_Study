import sys

sys.stdin = open("input.txt")


from collections import deque


def is_inside(y, x):
    return -1 < y < N and -1 < x < M


def bfs(y, x):
    count = 0
    que = deque()
    que.append((y, x))

    visit[y][x] = True

    while que:
        count += 1

        y, x = que.popleft()

        for d in range(4):
            ny = y + dy[d]
            nx = x + dx[d]

            if not is_inside(ny, nx):
                continue

            if visit[ny][nx]:
                continue

            if graph[y][x][d] == 0:
                que.append((ny, nx))
                visit[ny][nx] = True

    return count


# 남 동 북 서
dy = [1, 0, -1, 0]
dx = [0, 1, 0, -1]

# 세로 N, 가로 M
M, N = list(map(int, input().split()))

graph = [[[0, 0, 0, 0] for _ in range(M)] for _ in range(N)]

for y in range(N):
    row = list(input().split())
    for x in range(M):
        digit = format(int(row[x]), "b")
        # 3 -> 11

        for _ in range(4 - len(digit)):
            digit = "0" + digit

        # 0011
        # [0,0,1,1]
        for d in range(4):
            if digit[d] == "1":
                graph[y][x][d] = 1

visit = [[False for _ in range(M)] for _ in range(N)]

ans = [0, 0, 0]

for y in range(N):
    for x in range(M):
        if visit[y][x] == False:
            ans[0] += 1
            ans[1] = max(ans[1], bfs(y, x))


for y in range(N):
    for x in range(M):
        for d in range(4):
            if graph[y][x][d] == 1:
                visit = [[False for _ in range(M)] for _ in range(N)]
                graph[y][x][d] = 0
                ans[2] = max(ans[2], bfs(y, x))
                graph[y][x][d] = 1

for a in ans:
    print(a)
