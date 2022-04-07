import sys

sys.stdin = open("input.txt")

from itertools import permutations
from copy import deepcopy


def rotate(sy, sx, ey, ex):
    while True:
        y, x = sy, sx
        temp = graph[y][x]

        # 윗변 이동
        while True:
            x += 1
            temp, graph[y][x] = graph[y][x], temp

            if x == ex:
                break
        # 우변 이동
        while True:
            y += 1
            temp, graph[y][x] = graph[y][x], temp

            if y == ey:
                break
        # 아랫변 이동
        while True:
            x -= 1
            temp, graph[y][x] = graph[y][x], temp

            if x == sx:
                break
        # 좌변 이동
        while True:
            y -= 1
            temp, graph[y][x] = graph[y][x], temp

            if y == sy:
                break

        # 각 좌표 안쪽으로 좁히기
        sx += 1
        sy += 1
        ey -= 1
        ex -= 1

        # 한점으로 모이면 종료
        if sx >= ex or sy >= ey:
            break


N, M, K = list(map(int, input().split()))


ori_graph = []
for _ in range(N):
    ori_graph.append(list(map(int, input().split())))

cals = []
for _ in range(K):
    cal = list(map(int, input().split()))
    cal[0] -= 1
    cal[1] -= 1
    cals.append(cal)

ans = 10e9

# 연산 순서를 바꾸기 위해 순열 리스트 생성
for cals in permutations(cals, K):
    # 원본 배열 복사
    graph = deepcopy(ori_graph)
    # 회전
    for r, c, s in cals:
        sy, sx = (r - s, c - s)
        ey, ex = (r + s, c + s)

        rotate(sy, sx, ey, ex)

    # 연산자 모두 수행 후 배열의 최솟값 계산
    for row in graph:
        ans = min(sum(row), ans)

print(ans)
