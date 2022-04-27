def solution(s):
    answer = len(s)

    for size in range(1, len(s) // 2 + 1):
        original_string = s[0:size]  # 원본 문자열
        total_string = ""  # 문자열 기록
        cnt = 1  # 반복 횟수

        for start in range(0, len(s) - size, size):
            compare_string = s[start + size : start + size * 2]  # 비교 문자열

            # 원본 == 비교
            if original_string == compare_string:
                cnt += 1  # 반복 횟수 +1

            # 원본 != 비교
            else:
                # 반복했을 때
                if cnt > 1:
                    total_string += f"{cnt}{original_string}"
                # 반복없을 때
                else:
                    total_string += f"{original_string}"
                cnt = 1

                # 원본 문자열 갱신
                original_string = compare_string

        if cnt > 1:
            total_string += f"{cnt}{original_string}"
        else:
            total_string += f"{original_string}"

        # 최소 길이 갱신
        answer = min(answer, len(total_string))

    return answer


solution("aabbaccc")
