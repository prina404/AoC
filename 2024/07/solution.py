import itertools


def hasValidOperations(test: int, operands: list[int], part1: bool) -> bool:
    numOps = 2 if part1 else 3
    for op in itertools.product(range(numOps), repeat=len(operands) - 1):
        res = operands[0]
        for i in range(len(op)):
            if op[i] == 0:
                res += operands[i + 1]
            elif op[i] == 1:
                res *= operands[i + 1]
            elif op[i] == 2:
                res = int(str(res) + str(operands[i + 1]))
            if res > test:
                break

        if res == test:
            return True
    return False


def solve(lines: list[str], part1: bool) -> int:
    res = 0
    for test, operands in lines:
        operands = list(map(int, operands.split()))
        if hasValidOperations(int(test), operands, part1):
            res += int(test)
    return res


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = [line.strip().split(": ") for line in f.readlines()]

    print(solve(lines, True))
    print(solve(lines, False))
