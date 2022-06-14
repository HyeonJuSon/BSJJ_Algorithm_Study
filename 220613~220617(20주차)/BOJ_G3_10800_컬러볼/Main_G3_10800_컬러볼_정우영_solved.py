import sys

sys.stdin = open("input.txt")

from collections import defaultdict 

N = int(input())
ball = []
for i in range(N):
    # 색, 크기
    C, S = map(int, sys.stdin.readline().split())
    # 색, 크기, 인덱스 순으로 추가
    ball.append((C, S, i))

# 크기 기준으로 오름차순 정렬
ball = sorted(ball, key=lambda x: x[1])


answer = defaultdict(int)
color_sums = defaultdict(int)
total = 0
j = 0
for color, size, index in ball:

    # 다른 컬러, 중복 무게를 처리하기위한 로직
    # 현재 볼의 크기보다 작은 경우의 비교볼(j) 탐색
    while ball[j][1] < size:
        total += ball[j][1]
        color_sums[ball[j][0]] += ball[j][1]
        j += 1

    answer[index] = total - color_sums[color]


for i in range(N):
    print(answer[i])
