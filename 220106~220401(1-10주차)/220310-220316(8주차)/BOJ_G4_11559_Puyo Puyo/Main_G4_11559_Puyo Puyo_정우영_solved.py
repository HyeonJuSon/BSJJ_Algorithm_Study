import sys

sys.stdin = open("input.txt")

from collections import deque

dy = [0, 0, 1, -1]
dx = [1, -1, 0, 0]

# 가로 세로 길이
N, M = 12, 6


def bfs(y, x):
    block = graph[y][x]
    visit_graph[y][x] = True
    que = deque()
    que.append((y, x))
    adj_blocks = list()

    while que:
        y, x = que.popleft()
        adj_blocks.append((y, x))

        for d in range(4):
            ny = y + dy[d]
            nx = x + dx[d]

            if ny == -1 or ny == N:
                continue
            if nx == -1 or nx == M:
                continue

            if not visit_graph[ny][nx] and graph[ny][nx] == block:
                que.append((ny, nx))
                visit_graph[ny][nx] = True

    # 인접한 같은 블럭이 4개 이상일 때 블럭 터트리기
    if len(adj_blocks) >= 4:
        for adj_y, adj_x in adj_blocks:
            graph[adj_y][adj_x] = "."

        return True, len(adj_blocks)

    return False, 0


colors = ["R", "G", "B", "P", "Y"]
graph = []

for _ in range(N):
    graph.append(list(input()))

# 연쇄 횟수
ans = 0
# 이전 터진 블럭 개수, 현재 터진 블럭 개수
pre_boom, boom = 0, 0

while True:
    # 이전 터진 블럭 개수 갱싱
    pre_boom = boom

    # 방문 체크용 그래프 다시 그리기
    visit_graph = [[False for _ in range(M)] for _ in range(N)]

    # 블럭 터트리기
    for y in range(N):
        for x in range(M):
            block = graph[y][x]
            if block in colors and not visit_graph[y][x]:
                flag, cnt = bfs(y, x)
                # 연쇄 작용이 일어 났으면 터진 블럭 수 + 1
                if flag:
                    boom += cnt

    # 터진 블럭이 없으면 종료
    if pre_boom == boom:
        break

    # 터진 블럭이 있으면 연쇄 횟수 +1
    else:
        ans += 1

    # 블럭 내리기
    # 아래에서 위로 탐색 11 -> 0
    for y in reversed(range(N)):
        for x in range(M):
            block = graph[y][x]
            if block in colors:
                # 임시 세로 좌표
                temp_y = y + 1

                # 범위를 벗어 나지 않고, 빈 공간일 때 까지 탐색
                while temp_y != N and graph[temp_y][x] == ".":
                    temp_y += 1

                # 값 바꿔치기
                graph[y][x], graph[temp_y - 1][x] = ".", block


print(ans)
