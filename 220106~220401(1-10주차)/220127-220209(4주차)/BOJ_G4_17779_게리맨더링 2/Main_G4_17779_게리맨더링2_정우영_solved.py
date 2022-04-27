def dfs(x, y, d1, d2, total):

    # 구역 체크용 리스트
    area_graph = [[0] * (N + 1) for _ in range(N + 1)]

    # 각 구역 인구 수 총합
    area_sum = [0] * 5

    # 5번 구역 경계선 체크
    for i in range(0, d1 + 1):
        area_graph[x + i][y - i] = 5

    for i in range(0, d2 + 1):
        area_graph[x + i][y + i] = 5

    for i in range(0, d2 + 1):
        area_graph[x + d1 + i][y - d1 + i] = 5

    for i in range(0, d1 + 1):
        area_graph[x + d2 + i][y + d2 - i] = 5

    # 1번 구역 체크
    for r in range(1, x + d1):
        for c in range(1, y + 1):
            if area_graph[r][c] == 5:
                break

            area_graph[r][c] = 1
            # 1번 구역 인구수 더하기
            area_sum[1] += graph[r][c]

    # 2번 구역 체크
    for r in range(1, x + d2 + 1):
        for c in range(N, y, -1):
            if area_graph[r][c] == 5:
                break

            area_graph[r][c] = 2
            # 2번 구역 인구수 더하기
            area_sum[2] += graph[r][c]

    # 3번 구역 체크
    for r in range(x + d1, N + 1):
        for c in range(1, y - d1 + d2):
            if area_graph[r][c] == 5:
                break

            area_graph[r][c] = 3
            # 3번 구역 인구수 더하기
            area_sum[3] += graph[r][c]

    # 4번 구역 체크
    for r in range(x + d2 + 1, N + 1):
        for c in range(N, y - d1 + d2 - 1, -1):
            if area_graph[r][c] == 5:
                break

            area_graph[r][c] = 4
            # 4번 구역 인구수 더하기
            area_sum[4] += graph[r][c]

    # 5구역 인구수 계산 : 전체 인구수 - 나머지 구역 인구수
    area_sum[0] = total - sum(area_sum)

    # 현재 경우의 최대 인구 - 최소 인구
    return max(area_sum) - min(area_sum)


N = int(input())
# 좌표 시작 값(1,1) 맞추기 위해 더미행 추가
graph = [[0] * (N + 1)]

total = 0

for _ in range(N):
    row = [0] + list(map(int, input().split()))
    # 전체 인구수 계산
    total += sum(row)
    graph.append(row)

ans = 10e9

# x,y,d1,d2 완전 탐색
for x in range(1, N):
    for y in range(1, N):
        for d1 in range(1, N):
            for d2 in range(1, N):
                # 조건에 맞지 않으면 다음 경우 탐색
                if not (x + d1 + d2) <= N:
                    continue

                if not (y - d1) >= 1:
                    continue

                if not (y + d2) <= N:
                    continue

                ans = min(ans, dfs(x, y, d1, d2, total))

print(ans)
