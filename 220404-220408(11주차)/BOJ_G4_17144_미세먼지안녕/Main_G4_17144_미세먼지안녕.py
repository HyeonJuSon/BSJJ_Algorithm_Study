import sys

sys.stdin = open("input.txt")

dirs = [[0, 1], [1, 0], [-1, 0], [0, -1]]


def is_inside(r, c):
    return -1 < r < R and -1 < c < C


# 전체 미세먼지 양
def total_dust():
    ans = 0
    for row in A:
        ans += sum(row)

    return ans + 2


# 미세먼지 있는 공간
def find_dust_area():
    dust_area = []
    for r in range(R):
        for c in range(C):
            # 공기 청정기 혹은 빈 공간
            if A[r][c] == -1 or A[r][c] == 0:
                continue

            dust_area.append([r, c])

    return dust_area


def cal_diffusion(r, c):
    diffusion_amount = A[r][c] // 5

    count = 0

    for d in range(4):
        nr = r + dirs[d][0]
        nc = c + dirs[d][1]

        # 리스트 밖
        if not is_inside(nr, nc):
            continue

        # 공기 청정기
        if A[nr][nc] == -1:
            continue

        count += 1
        # 상하좌우 퍼져야하는 양 계산
        diffusion_area[nr][nc] += diffusion_amount

    # 퍼진 원점에서 빠져야되는 양
    diffusion_area[r][c] -= diffusion_amount * count


# 위쪽 공기청정기 가동
def active_purifier_top():
    r, c = purifier_top, 0

    r -= 1
    A[r][c] = 0

    # 시계 방향으로 탐색해서 미세먼지 이동
    while r != 0:
        A[r][c] = A[r - 1][c]
        r -= 1

    while c != C - 1:
        A[r][c] = A[r][c + 1]
        c += 1

    while r != purifier_top:
        A[r][c] = A[r + 1][c]
        r += 1

    while c != 1:
        A[r][c] = A[r][c - 1]
        c -= 1

    A[r][c] = 0


def active_purifier_bottom():
    r, c = purifier_bottom, 0

    r += 1
    A[r][c] = 0

    # 반시계 방향으로 탐색해서 미세먼지 이동
    while r != R - 1:
        A[r][c] = A[r + 1][c]
        r += 1

    while c != C - 1:
        A[r][c] = A[r][c + 1]
        c += 1

    while r != purifier_bottom:
        A[r][c] = A[r - 1][c]
        r -= 1

    while c != 1:
        A[r][c] = A[r][c - 1]
        c -= 1

    A[r][c] = 0


# 집 크기 R X C
R, C, T = list(map(int, input().split()))

# 확산되는 양 A[r,c]//5,
# 남는 양 A[r,c] - (A[r,c]//5) * 확산된 방향 개수
purifier_top, purifier_bottom = 0, 0

A = []
for r in range(R):
    row = list(map(int, input().split()))
    A.append(row)

    if row[0] == -1:
        if purifier_top == 0:
            purifier_top = r
        else:
            purifier_bottom = r


# 각 좌표 미세먼지 +- 계산 리스트
diffusion_area = [[0 for _ in range(C)] for _ in range(R)]

for _ in range(T):
    # 미세먼지 있는 곳 찾기
    dust_area = find_dust_area()

    # 각 좌표 미세먼지 확산양 계산
    for r, c in dust_area:
        cal_diffusion(r, c)

    # 미세먼지 동시에 확산
    for r in range(R):
        for c in range(C):
            A[r][c] += diffusion_area[r][c]

            # 계산 리스트 0으로 초기화
            diffusion_area[r][c] = 0

    # 공기 청정기 가동
    active_purifier_top()
    active_purifier_bottom()

print(total_dust())
