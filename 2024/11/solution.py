from functools import cache


@cache
def stoneScore(num, step):
    if step == 0:
        return 1

    if num == 0:
        return stoneScore(1, step - 1)

    numStr = str(num)
    if len(numStr) % 2 == 0:
        lhs = int(numStr[: (len(numStr) // 2)])
        rhs = int(numStr[len(numStr) // 2 :])
        return stoneScore(lhs, step - 1) + stoneScore(rhs, step - 1)

    return stoneScore(num * 2024, step - 1)


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        stones = list(map(int, f.readline().split()))

    print("Part 1:", sum([stoneScore(gem, 25) for gem in stones]))
    print("Part 2:", sum([stoneScore(gem, 75) for gem in stones]))
