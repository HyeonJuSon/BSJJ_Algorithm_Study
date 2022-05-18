def is_all_even_odd(lst):
    first = lst[0] % 2

    for i in range(1, len(lst)):
        if first != lst[i] % 2:
            return False

    return True


dr = [-1, -1, 0, 1, 1, 1, 0, -1]
dc = [0, 1, 1, 1, 0, -1, -1, -1]
N, M, K = list(map(int, input().split()))

from collections import defaultdict

balls = []
graph = [[[] for _ in range(N)] for _ in range(N)]

balls = defaultdict(list)
for i in range(M):
    # 위치, 질량, 속력, 방향
    r, c, m, s, d = list(map(int, input().split()))
    r -= 1
    c -= 1
    balls[(r, c)].append({"m": m, "s": s, "d": d})

for _ in range(K):
    temp_balls = defaultdict(list)

    for k, values in balls.items():
        r, c = k
        for v in values:
            m, s, d = v["m"], v["s"], v["d"]

            nr = (r + dr[d] * s) % N
            nc = (c + dc[d] * s) % N

            temp_balls[(nr, nc)].append({"m": m, "s": s, "d": d})

    zeros = set()
    for k, v in temp_balls.items():
        if len(v) >= 2:
            r = k[0]
            c = k[1]
            count = len(v)
            total_m = 0
            total_s = 0
            total_d = []

            for i in range(count):
                m = v[i]["m"]
                s = v[i]["s"]
                d = v[i]["d"]
                total_m += m
                total_s += s
                total_d.append(d)

            m = total_m // 5
            s = total_s // count

            if m != 0:
                if is_all_even_odd(total_d):
                    temp_balls[(r, c)] = [
                        {"m": m, "s": s, "d": 0},
                        {"m": m, "s": s, "d": 2},
                        {"m": m, "s": s, "d": 4},
                        {"m": m, "s": s, "d": 6},
                    ]
                else:
                    temp_balls[(r, c)] = [
                        {"m": m, "s": s, "d": 1},
                        {"m": m, "s": s, "d": 3},
                        {"m": m, "s": s, "d": 5},
                        {"m": m, "s": s, "d": 7},
                    ]
            else:
                zeros.add((r, c))

    for zero_lo in zeros:
        temp_balls.pop(zero_lo)

    balls = temp_balls

result = 0
for values in balls.values():
    for value in values:
        result += value["m"]

print(result)
