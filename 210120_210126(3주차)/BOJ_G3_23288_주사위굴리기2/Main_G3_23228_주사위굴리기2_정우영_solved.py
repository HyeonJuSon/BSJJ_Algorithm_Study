import sys

sys.stdin = open("input.txt")
# https://www.acmicpc.net/problem/23288
import copy
from collections import deque


def change_direction(A, B):
    global dir
    if A > B:
        dir += 1
    elif B > A:
        dir -= 1
    if dir == len(directions):
        dir = 0
    if dir == -1:
        dir = 3


def reverse_direction():
    global dir
    if dir == 0:
        dir = 2
    elif dir == 2:
        dir = 0
    elif dir == 1:
        dir = 3
    elif dir == 3:
        dir = 1


def dice_move():
    global dice
    dice_copy = copy.deepcopy(dice)
    if dir == 0:
        # 동 구르기
        dice[1][0] = dice_copy[3][0]
        dice[1][1] = dice_copy[1][0]
        dice[1][2] = dice_copy[1][1]
        dice[3][0] = dice_copy[1][2]
    if dir == 1:
        # 남 구르기
        dice[0][0] = dice_copy[3][0]
        dice[1][1] = dice_copy[0][0]
        dice[2][0] = dice_copy[1][1]
        dice[3][0] = dice_copy[2][0]
    if dir == 2:
        # 서 구르기
        dice[1][2] = dice_copy[3][0]
        dice[1][1] = dice_copy[1][2]
        dice[1][0] = dice_copy[1][1]
        dice[3][0] = dice_copy[1][0]
    if dir == 3:
        # 북 구르기
        dice[0][0] = dice_copy[1][1]
        dice[1][1] = dice_copy[2][0]
        dice[2][0] = dice_copy[3][0]
        dice[3][0] = dice_copy[0][0]


def bfs(y, x, value):
    queue = deque()
    queue.append((y, x))
    visit = set()
    visit.add((y, x))
    cnt = 1
    while queue:
        y, x = queue.popleft()
        for dx, dy in [(1, 0), (0, 1), (-1, 0), (0, -1)]:
            ny = y + dy
            nx = x + dx
            if (ny, nx) in visit:
                continue
            if 0 <= ny < N and 0 <= nx < M and board[ny][nx] == value:
                queue.append((ny, nx))
                visit.add((ny, nx))
                cnt += 1
    return cnt


N, M, K = list(map(int, input().split()))
# 지도 정보 입력
board = []
for _ in range(N):
    lst = list(map(int, input().split()))
    board.append(lst)
# 주사위 전개도
dice = [[2], [4, 1, 3], [5], [6]]
# 주사위 위치
dice_location = [0, 0]
# 이동 방향 동 -> 남 -> 서 -> 북
directions = [(0, 1), (1, 0), (0, -1), (-1, 0)]
# 현재 이동 방향(0: 동, 1: 남, 2: 서, 3: 북)
dir = 0
# 총 점수
score = 0
for k in range(K):
    dy, dx = directions[dir]
    ny = 0
    nx = 0
    """
    다음 위치 구하기
    """
    # 다음 위치가 지도 범위 안
    if 0 <= dice_location[0] + dy < N and 0 <= dice_location[1] + dx < M:
        ny = dice_location[0] + dy
        nx = dice_location[1] + dx
    # 다음 위치가 지도 범위 밖, 이동 방향 반대로
    else:
        # 주사위 방향 반대로
        reverse_direction()
        dy, dx = directions[dir]
        ny = dice_location[0] + dy
        nx = dice_location[1] + dx
    # 주사위 굴리기
    dice_move()
    # 점수계산
    value = board[ny][nx]
    cnt = bfs(ny, nx, value)
    score += cnt * value
    # 방향 정하기
    change_direction(dice[3][0], board[ny][nx])
    # 주사위 다음 위치 저장
    dice_location[0] = ny
    dice_location[1] = nx

print(score)
