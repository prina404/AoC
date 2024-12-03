import re


def getEnabledSubStr(line: str) -> str:
    i = 0
    while i < len(line):
        i += 1
        if line[i:].startswith("don't()"):
            j = 0
            while j + i < len(line) and not line[i + j :].startswith("do()"):
                j += 1
            line = line[:i] + line[i + j :]
            i += len("do()")
    return line


def solve(program: str) -> int:
    instructions = re.findall("mul\(\d{1,3},\d{1,3}\)", program)
    res = 0
    for mul in instructions:
        x, y = re.findall("\d+", mul)
        res += int(x) * int(y)
    return res


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        line = "".join(f.readlines())    

    print("Part 1: ", solve(line))
    print("Part 2: ", solve(getEnabledSubStr(line)))
