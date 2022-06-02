import sys

sys.stdin = open("input.txt")

# https://www.acmicpc.net/problem/16235
def spring():
    for y in range(N):
        for x in range(N):
            # 현재 좌표에 나무가 있을 때
            if trees[y][x]:
                # 양분 흡수 후 살아있는 나무 저장
                live_trees = []

                # 나이를 기준으로 오름차순 정렬
                trees[y][x].sort()

                for index in range(len(trees[y][x])):
                    # 나무 나이
                    age = trees[y][x][index]

                    # 남은 양분이 나이보다 많을 때
                    if foods[y][x] >= age:
                        # 양분 감소
                        foods[y][x] -= age
                        live_trees.append(age + 1)

                    else:
                        # 양분 흡수 후 죽는 나무 저장
                        dead_trees.append((y, x, trees[y][x][index:]))
                        break

                # 살아있는 나무 값 할당
                trees[y][x] = live_trees


def summer():
    for y, x, dead_tree in dead_trees:
        for age in dead_tree:
            foods[y][x] += age // 2


def fall():
    dy = [-1, -1, -1, 0, 0, 1, 1, 1]
    dx = [-1, 0, 1, -1, 1, -1, 0, 1]
    for y in range(N):
        for x in range(N):
            if trees[y][x]:
                for index in range(len(trees[y][x])):
                    age = trees[y][x][index]
                    if age % 5 == 0:
                        for d in range(8):
                            ny = y + dy[d]
                            nx = x + dx[d]

                            if -1 < ny < N and -1 < nx < N:
                                trees[ny][nx].append(1)


def winter():
    for y in range(N):
        for x in range(N):
            foods[y][x] += plus_foods[y][x]


def check_tree():
    count = 0
    for y in range(N):
        for x in range(N):
            count += len(trees[y][x])

    return count


# N X N 땅 크기
# M 나무 개수
# K년 후 남은 나무 개수
N, M, K = list(map(int, input().split()))


# 처음 양분 5
foods = [[5 for _ in range(N)] for _ in range(N)]

# 각 칸에 추가되는 양분
plus_foods = []
for _ in range(N):
    plus_foods.append(list(map(int, input().split())))

# 나무의 정보
# 위치, 나이
trees = [[[] for _ in range(N)] for _ in range(N)]

for _ in range(M):
    y, x, z = list(map(int, input().split()))
    x -= 1
    y -= 1
    trees[y][x].append(z)


for _ in range(K):
    dead_trees = []
    spring()
    summer()
    fall()
    winter()

ans = check_tree()
print(ans)
