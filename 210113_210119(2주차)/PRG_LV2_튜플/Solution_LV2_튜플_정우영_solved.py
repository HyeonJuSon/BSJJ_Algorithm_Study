def solution(s):
    answer = []
    numbers_list = []
    numbers = []
    temp = ""
    # 처음 { 마지막 } 제외하고 순회
    for char in s[1:-1]:
        # 새로운 숫자 리스트 시작
        if char == "{":
            numbers = []

        # 숫자 값이면 임시 문자열에 추가
        elif char.isnumeric():
            temp += char

        # 숫자 리스트 끝
        # 숫자 그룹에 추가
        elif char == "}":
            if temp != "":
                numbers.append(int(temp))
                temp = ""

            numbers_list.append(numbers)
        elif char == ",":
            if temp != "":
                numbers.append(int(temp))
                temp = ""

    # 길이 오름차순으로 정렬
    numbers_list = sorted(numbers_list, key=lambda x: len(x))
    # 전체 숫자 그룹 순회
    for numbers in numbers_list:
        for number in numbers:
            # 숫자 집합 리스트에 포함되지 않은 숫자라면 정답 리스트에 추가
            if number not in answer:
                answer.append(number)

    return answer


solution("{{123}}")
