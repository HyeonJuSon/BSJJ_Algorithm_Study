import sys

sys.stdin = open("input.txt")


# 단어의 개수
N = int(input())

char_set = set()
lst = []

# 각 문자의 자리 수 계산
digit_dict = {}

for _ in range(N):
    words = list(input())

    # 현재 단어의 길이
    len_words = len(words)

    for index in range(len(words)):
        word = words[index]
        # 현재 문자의 자리 수
        digit = len_words - index - 1

        # 자리수 더하기
        if word in digit_dict:
            digit_dict[word] += 10 ** digit
        else:
            digit_dict[word] = 10 ** digit

    lst.append(words)

# 자리 수를 기준으로 내림차순 정렬
sorted_digit_list = sorted(digit_dict.items(), key=lambda x: -x[1])

ans = 0

# 9부터 1까리 차례대로 부여
value = 9

for _, digit in sorted_digit_list:
    ans += digit * value
    value -= 1

print(ans)
