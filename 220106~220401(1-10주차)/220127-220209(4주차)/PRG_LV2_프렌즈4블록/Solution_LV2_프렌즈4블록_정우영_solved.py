# 우, 하, 우하 체크
def check(original, board, y, x, deleteLocation):
    if original == board[y][x + 1] and original == board[y + 1][x] and original == board[y + 1][x + 1]:
        deleteLocation.add((y, x))
        deleteLocation.add((y, x + 1))
        deleteLocation.add((y + 1, x))
        deleteLocation.add((y + 1, x + 1))


def solution(m, n, board):
    answer = 0
    for y in range(m):
        board[y] = list(board[y])

    while True:
        deleteLocation = set()

        # 블럭 삭제할 좌표 구하기
        for y in range(0, m - 1):
            for x in range(0, n - 1):
                # 블럭이 없는(0) 좌표는 스킵
                if board[y][x] == 0:
                    continue

                original = board[y][x]
                # 조건에 맞는 블럭 좌표인지 확인
                check(original, board, y, x, deleteLocation)

        # 삭제할 블럭이 없으면 반복문 종료
        if not len(deleteLocation):
            break

        # 삭제한 블록 개수 플러스
        answer += len(deleteLocation)

        # 블럭 삭제
        for y, x in deleteLocation:
            board[y][x] = 0

        # 블럭 재배치
        for y in reversed(range(m - 1)):
            for x in reversed(range(n)):
                # 빈 좌표는 스킵
                if board[y][x] == 0:
                    continue

                i = y
                while True:
                    # 좌표가 구역내이고, 기준 좌표 아래가 비어있으면
                    if i + 1 < m and board[i + 1][x] == 0:
                        board[i + 1][x], board[i][x] = board[i][x], 0
                        i = i + 1
                    # 아니면 종료
                    else:
                        break
    return answer
