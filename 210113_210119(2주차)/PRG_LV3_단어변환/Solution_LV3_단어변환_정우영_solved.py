def checkWord(word1, word2):
    # 일치하는 알파벳 수
    count = 0

    # 두 단어의 일치하는 알파벳 수
    for idx in range(len(word1)):
        if word1[idx] == word2[idx]:
            count += 1

    # 일치하는 알파벳 수가 1개인 경우
    # 단어를 바꿀 수 있음.
    if count == len(word1) - 1:
        return True

    return False


def solution(begin, target, words):
    global answer
    # words 길이는 3개 이상 50개 이하
    MAX_LENGTH = 51

    # 탐색 횟수 최댓값으로 초기화
    answer = MAX_LENGTH

    # 단어 방문 여부
    visit = {}
    for word in words:
        visit[word] = False

    def func(word, count):
        global answer
        # 현재 탐색 횟수가 최소 탐색 횟수 이상이면 현재 탐색 멈춤
        if answer <= count:
            return

        # 타켓 단어 찾으면
        if word == target:
            # 최소 탐색 횟수 갱신
            answer = count
            return

        # 모든 단어 순회
        for next_word in words:
            if checkWord(word, next_word) and not visit[next_word]:
                # 단어 방문 체크
                visit[next_word] = True

                func(next_word, count + 1)

                # 단어 방문 체크 해제
                visit[next_word] = False

    # 시작 단어 방문 체크
    visit[begin] = True
    # 현재 단어, 탐색횟수
    func(begin, 0)

    # 탐색 횟수가 갱신된적 있으면
    if answer != MAX_LENGTH:
        return answer

    # 갱신된적 없으면
    return 0


solution("hit", "cog", ["hot", "dot", "dog", "lot", "log"])
