import sys

sys.stdin = open("input.txt")

from collections import deque


def cal(x1, op, x2):
    x1 = x1
    x2 = x2

    if op == "*":
        return x1 * x2

    if op == "/":
        if x1 * x2 < 0 and x1 % x2 != 0:
            return (x1 // x2) + 1
        return x1 // x2

    if op == "+":
        return x1 + x2

    if op == "-":
        return x1 - x2


priority = {"*": 1, "/": 1, "+": 0, "-": 0}

inputs = list(input())

expression = deque()
numbers = deque()
opers = deque()
number_temp = None
minus = False
for i in range(len(inputs)):
    char = inputs[i]
    # 현재 문자가 연산자라면
    if char in ["+", "-", "*", "/"]:
        if number_temp == None:
            minus = True
        else:
            if minus:
                numbers.append(-number_temp)
                minus = False
            else:
                numbers.append(number_temp)

            opers.append(char)
            number_temp = None

    # 현재 문자가 숫자
    else:
        if number_temp == None:
            number_temp = 0

        number_temp *= 10
        number_temp += int(char)

if minus:
    numbers.append(-number_temp)
else:
    numbers.append(number_temp)

print(numbers, opers)

while len(opers) != 0:
    front_val = cal(numbers[0], opers[0], numbers[1])
    back_val = cal(numbers[-2], opers[-1], numbers[-1])

    result = ""

    if opers[0] in ("+", "-") and opers[-1] in ("*", "/"):
        result = "back"
    elif opers[0] in ("*", "/") and opers[-1] in ("+", "-"):
        result = "front"

    # 우선순위가 같을 때
    else:
        if front_val > back_val:
            result = "front"

        elif back_val > front_val:
            result = "back"
        else:
            result = "front"

    if result == "front":
        numbers.popleft()
        numbers.popleft()
        opers.popleft()
        numbers.appendleft(front_val)

    elif result == "back":
        numbers.pop()
        numbers.pop()
        opers.pop()
        numbers.append(back_val)


print(numbers[0])
