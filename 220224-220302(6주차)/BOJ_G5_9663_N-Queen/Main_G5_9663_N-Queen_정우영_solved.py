import sys

sys.stdin = open("input.txt")


N = int(input())

# 체크판
# index : row(행), value : col(열)
board = [0] * (N)


ans = 0


def check(row1):
    for row2 in range(row1):
        # 같은 열에 이미 퀸이 있으면
        if board[row1] == board[row2]:
            return False
        # 대각선에 이미 퀸이 있으면
        if abs(board[row1] - board[row2]) == (row1 - row2):
            return False

    return True


def func(row):
    global ans

    # 마지막 행까지 체크가 완료됐으면 경우의 수 + 1
    if row == N:
        ans += 1
        return

    for col in range(N):
        board[row] = col

        if check(row):
            func(row + 1)


func(0)
print(ans)
