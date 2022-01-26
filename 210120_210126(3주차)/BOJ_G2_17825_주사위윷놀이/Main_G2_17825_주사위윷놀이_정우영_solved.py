import sys

sys.stdin = open("input.txt")
# https://www.acmicpc.net/problem/17825
# 판 위치에 따른 점수 목록
board_score = [0, 2, 4, 6, 8, 10, 13, 16, 19, 12, 14, 16, 18, 20, 22, 24, 22, 24, 26, 28, 30, 28, 27, 26, 32, 34, 36, 38, 25, 30, 35, 40, 0]
# 주사위 값(1~5)에 따라 다음 위치
board_next = [
    [1, 2, 3, 4, 5],  # 0
    [2, 3, 4, 5, 9],  # 1
    [3, 4, 5, 9, 10],  # 2
    [4, 5, 9, 10, 11],  # 3
    [5, 9, 10, 11, 12],  # 4
    [6, 7, 8, 28, 29],  # 5
    [7, 8, 28, 29, 30],  # 6
    [8, 28, 29, 30, 31],  # 7
    [28, 29, 30, 31, 32],  # 8
    [10, 11, 12, 13, 16],  # 9
    [11, 12, 13, 16, 17],  # 10
    [12, 13, 16, 17, 18],  # 11
    [13, 16, 17, 18, 19],  # 12
    [14, 15, 28, 29, 30],  # 13
    [15, 28, 29, 30, 31],  # 14
    [28, 29, 30, 31, 32],  # 15
    [17, 18, 19, 20, 24],  # 16
    [18, 19, 20, 24, 25],  # 17
    [19, 20, 24, 25, 26],  # 18
    [20, 24, 25, 26, 27],  # 19
    [21, 22, 23, 28, 29],  # 20
    [22, 23, 28, 29, 30],  # 21
    [23, 28, 29, 30, 31],  # 22
    [28, 29, 30, 31, 32],  # 23
    [25, 26, 27, 31, 32],  # 24
    [26, 27, 31, 32, 32],  # 25
    [27, 31, 32, 32, 32],  # 26
    [31, 32, 32, 32, 32],  # 27
    [29, 30, 31, 32, 32],  # 28
    [30, 31, 32, 32, 32],  # 29
    [31, 32, 32, 32, 32],  # 30
    [32, 32, 32, 32, 32],  # 31
    [32, 32, 32, 32, 32],  # 32
]
# 주사위 숫자 10개 입력
dices = list(map(int, input().split()))
# 말의 현재 위치
horse_location = [0, 0, 0, 0]
# 최댓값
max_score = 0
# 주사위 값 인덱스,
def func(index, score):
    global max_score
    if index == 10:
        max_score = max(max_score, score)
        return
    # 현재 인덱스 주사위
    dice = dices[index]
    for horse_index in range(0, 4):
        # 말의 현재 위치
        now_horse = horse_location[horse_index]
        # 현재 말이 도착했으면 skip
        if now_horse == 32:
            continue
        # 말의 다음 위치
        next_horse = board_next[now_horse][dice - 1]
        # 말의 다음 위치가 도착점이거나 다른 말이 없을 때
        if next_horse not in horse_location or next_horse == 32:
            # 말 다음 위치 저장
            horse_location[horse_index] = next_horse
            func(index + 1, score + board_score[next_horse])
            # 말 현재 위치 돌리기
            horse_location[horse_index] = now_horse


func(0, 0)
print(max_score)
