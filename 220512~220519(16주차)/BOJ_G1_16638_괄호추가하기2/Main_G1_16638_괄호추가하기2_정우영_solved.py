import sys

sys.stdin = open("input.txt")

from cmath import inf
import sys

sys.setrecursionlimit(10**6)


def cal_postfix_expression(postfix_expression):
    number_list = []
    for e in postfix_expression:
        if e.strip("-").isdigit():
            number_list.append(e)
        else:
            n1 = int(number_list.pop())
            n2 = int(number_list.pop())
            if e == "+":
                number_list.append(n2 + n1)
            if e == "-":
                number_list.append(n2 - n1)
            if e == "*":
                number_list.append(n2 * n1)

    answer = number_list.pop()
    return answer


def change_postfix_expression(expression):
    stack = []
    postfix_expression = []

    for char in expression:
        if char.isdigit():
            postfix_expression.append(char)
        else:
            if char == "(":
                stack.append(char)

            elif char == "*":
                while stack and stack[-1] == "*":
                    postfix_expression.append(stack.pop())

                stack.append(char)

            elif char == "+" or char == "-":
                while stack and stack[-1] != "(":
                    postfix_expression.append(stack.pop())
                stack.append(char)

            else:
                while stack[-1] != "(":
                    postfix_expression.append(stack.pop())

                stack.pop()

    while stack:
        postfix_expression.append(stack.pop())

    return cal_postfix_expression(postfix_expression)


def process_bracket(brackets: list):
    temp_expression = []
    for index in range(len(oper_index)):
        n1 = expression[number_index[index]]
        n2 = expression[number_index[index + 1]]
        oper = expression[oper_index[index]]

        if brackets[index] == 1:
            temp_expression += ["(", n1, oper, n2, ")"]

        elif brackets[index] == 0:

            if brackets[index - 1] == 1 and index != 0:
                temp_expression += [oper]
            else:
                temp_expression += [n1, oper]
            if index == len(oper_index) - 1:
                temp_expression += [n2]

    # print(temp_expression)
    return change_postfix_expression(temp_expression)


def dfs(brackets: list, index: int):
    global max_answer
    if index > len(oper_index) - 1:
        max_answer = max(max_answer, process_bracket(brackets))
        return

    if index > 0 and brackets[index - 1] == 1:
        brackets[index] = 0
        dfs(brackets, index + 1)

    else:
        brackets[index] = 0
        dfs(brackets, index + 1)

        brackets[index] = 1
        dfs(brackets, index + 1)


N = int(input())
max_answer = -inf
expression = list(input())

if N == 1:
    print(*expression)
else:
    number_index = []
    oper_index = []
    for i in range(len(expression)):
        e = expression[i]
        if e in ["+", "-", "*"]:
            oper_index.append(i)
        else:
            number_index.append(i)

    dfs([0] * len(oper_index), 0)

    print(max_answer)
