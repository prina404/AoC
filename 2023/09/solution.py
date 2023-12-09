def getNext(numList, isPart1):
    if all(n == 0 for n in numList):
        return 0
    nextRow = []
    for i, n in enumerate(numList[:-1]):
        nextRow.append(numList[i + 1] - n)

    if isPart1:
        return numList[-1] + getNext(nextRow, isPart1)
    return numList[0] - getNext(nextRow, isPart1)


def solve(lines, part):
    return sum([getNext(l, part) for l in lines])


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    lines = [[int(n) for n in l.split()] for l in lines]

    print(f"part1: {solve(lines, True)}")
    print(f"part2: {solve(lines, False)}")