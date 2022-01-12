# https://www.acmicpc.net/problem/2096

# 줄의 개수 N
N = int(input())

# 최댓값, 최솟값 저장 리스트
max_lst = [0, 0, 0]
min_lst = [0, 0, 0]

# 최댓값, 최솟값 임시 저장 리스트
max_lst_tmp = [0, 0, 0]
min_lst_tmp = [0, 0, 0]

for row in range(N):
    # n행 값 입력받기
    col1, col2, col3 = list(map(int, input().split()))

    # 1열 계산
    max_lst_tmp[0] = col1 + max(
        max_lst[0],
        max_lst[1],
    )

    min_lst_tmp[0] = col1 + min(
        min_lst[0],
        min_lst[1],
    )

    # 2열 계산
    max_lst_tmp[1] = col2 + max(
        max_lst[0],
        max_lst[1],
        max_lst[2],
    )
    min_lst_tmp[1] = col2 + min(
        min_lst[0],
        min_lst[1],
        min_lst[2],
    )

    # 3열 계산
    max_lst_tmp[2] = col3 + max(
        max_lst[1],
        max_lst[2],
    )

    min_lst_tmp[2] = col3 + min(
        min_lst[1],
        min_lst[2],
    )

    # 계산한 값 복사
    max_lst = max_lst_tmp.copy()
    min_lst = min_lst_tmp.copy()

print(max(max_lst), min(min_lst))
