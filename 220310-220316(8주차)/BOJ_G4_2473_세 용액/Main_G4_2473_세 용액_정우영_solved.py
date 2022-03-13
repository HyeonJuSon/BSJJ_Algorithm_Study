import sys

n = int(input())

lst = list(map(int, input().split()))

lst.sort()
cur_total = sys.maxsize
ans = [0, 0, 0]

for i in range(n - 2):
    left = i + 1
    right = n - 1
    while left < right:
        total = lst[i] + lst[left] + lst[right]

        if abs(total) < abs(cur_total):
            ans = [lst[i], lst[left], lst[right]]
            cur_total = sum(ans)

        if total < 0:
            left += 1

        if total > 0:
            right -= 1

        if total == 0:
            break


print(ans[0], ans[1], ans[2])
