from collections import deque


def solution(cacheSize, cities):
    answer = 0

    # 캐시 저장 리스트
    caches = deque()

    for city in cities:
        # 대소문자 구분을 하지 않는다 -> 모든 문자 대문자로
        city = city.upper()

        # cache miss
        if city not in caches:
            answer += 5

        # cache hit
        else:
            answer += 1
            # 리스트에서 기존에 있는 도시 이름 삭제
            caches.remove(city)

        # 리스트 맨 앞에 도시 이름 저장
        caches.appendleft(city)

        # 리스트 길이가 지정된 캐시 크기보다 크면 리스트 맨 뒤 도시 이름 삭제
        if len(caches) > cacheSize:
            caches.pop()

    return answer
