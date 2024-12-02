def isSafe(level: list[str]) -> bool:
    nums = list(map(int, level))
    increasing = nums[0] < nums[1]
    for i in range(len(nums) - 1):
        prev, next = nums[i : i + 2]

        if abs(prev - next) > 3 or increasing != (prev < next) or prev == next:
            return False

    return True


def isSafeWithTolerance(level: list[str]) -> bool:
    for i in range(len(level)):
        if isSafe(level[:i] + level[i + 1 :]):
            return True
    return False


def solve(lines: list[str], part2=False) -> int:
    count = 0
    for level in lines:
        if isSafe(level.split()):
            count += 1
        elif part2 and isSafeWithTolerance(level.split()):
            count += 1

    return count


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = f.readlines()

    print("Part 1: ", solve(lines))
    print("Part 2: ", solve(lines, part2=True))
