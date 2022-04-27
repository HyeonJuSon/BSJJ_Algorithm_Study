import sys

V, E = map(int, sys.stdin.readline().split())
edges = []
parent = [0] * (V + 1)
for _ in range(E):
    v, u, c = list(map(int, sys.stdin.readline().split()))
    edges.append((v, u, c))


edges = sorted(edges, key=lambda x: x[2])


def findParent(x):
    if parent[x] == 0:
        parent[x] = x

    elif parent[x] != x:
        parent[x] = findParent(parent[x])

    return parent[x]


def union(a, b):
    a = findParent(a)
    b = findParent(b)
    if a < b:
        parent[b] = a
    else:
        parent[a] = b


result = 0
for edge in edges:
    a, b, c = edge
    if findParent(a) != findParent(b):
        union(a, b)
        result += c

print(result)
