import sys

n = int(input())

lst = list(map(int, input().split()))

# 오름차순 정렬
lst.sort()

# 비교용 세 용액 합
cur_total = sys.maxsize
# 세 용액 저장 리스트
ans = [0, 0, 0]

# 0 부터 길이-2 탐색
for i in range(n - 2):

    left, right = i + 1, n - 1

    while left < right:
        # 현재 세 용액 총합
        total = lst[i] + lst[left] + lst[right]

        # 현재 세 용액 총합이 비교 세 용액 총합보다 0보다 가까울 때(절대 값 비교)
        if abs(total) < abs(cur_total):
            # 정답 리스트 및 비교용 총합 갱싱
            ans = [lst[i], lst[left], lst[right]]
            cur_total = sum(ans)

        # 현재 총합이 음수라면 왼쪽 포인터 이동
        # 값을 더 크게 만듦
        if total < 0:
            left += 1

        # 현재 총합이 양수라면 오른쪽 포인터 이동
        # 값을 더 작게 만듦
        if total > 0:
            right -= 1

        if total == 0:
            break


print(ans[0], ans[1], ans[2])
