from pprint import pprint
import sys

sys.stdin = open("input.txt")

dy = [0, 0, 1, -1]
dx = [1, -1, 0, 0]

# 세로 가로
N, M = list(map(int, input().split()))

graph = []

for _ in range(N):
    graph.append(list(map(int, input().split())))

cheese_count = 0
air_contact_graph = [[0 for _ in range(M)] for _ in range(N)]

# 전체 치즈 개수 계산
for y in range(N):
    for x in range(M):
        if graph[y][x] == 1:
            cheese_count += 1


def bfs(y, x):
    que = list()
    que.append((y, x))
    visit = set()
    visit.add((y, x))

    while que:
        y, x = que.pop()

        for d in range(4):
            ny = y + dy[d]
            nx = x + dx[d]

            if ny < 0 or ny >= N:
                continue

            if nx < 0 or nx >= M:
                continue

            if (ny, nx) in visit:
                continue

            # 다음 좌표가 공기일 때
            if graph[ny][nx] == 0:
                # 큐에 좌표 삽입
                que.append((ny, nx))
                visit.add((ny, nx))

            # 다음 좌표가 치즈일 때
            if graph[ny][nx] == 1:
                # 치즈의 공기 접촉 횟수 +1
                air_contact_graph[ny][nx] += 1


ans = 0
while True:
    ans += 1
    # 0,0 부터 bfs
    bfs(0, 0)

    for y in range(N):
        for x in range(M):
            if graph[y][x] == 1:
                # 외부 공기 접촉 횟수 2이상일 때
                if air_contact_graph[y][x] >= 2:
                    # 치즈 삭제
                    graph[y][x] = 0
                    # 치즈 개수 감소
                    cheese_count -= 1
                # 외부 공기 접촉면이 2이상아닐 때
                else:
                    # 접촉 횟수 초기화
                    air_contact_graph[y][x] = 0

    # 남은 치즈 개수라면 종료
    if cheese_count == 0:
        break

print(ans)
