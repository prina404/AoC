def isinvalid(s: str, part1=True) -> bool:
    if part1:
        return len(s) % 2 == 0 and s[: len(s) // 2] == s[len(s) // 2 :]

    for i in range(1, len(s) // 2 + 1):
        if len(s) % i != 0:
            continue
        # very readable, right?
        if all([s[i * k : (i * k) + i] == s[:i] for k in range(len(s)//i)]):
            return True
    return False


def solve(ranges: list[str], part1: bool) -> int:
    res = 0
    for r in ranges:
        low, high = r.split("-")
        for n in range(int(low), int(high) + 1):
            res += n * isinvalid(str(n), part1) 
    return res


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = f.readline()
    ranges = lines.split(",")
    print("Part1: ", solve(ranges, True))
    print("Part2: ", solve(ranges, False))
