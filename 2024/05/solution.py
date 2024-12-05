from collections import defaultdict


def scoreUpdate(rules: dict[str, set], upd: list[str], part1: bool) -> int:
    isValid = True
    for i in range(len(upd) - 1):
        for j in range(i, len(upd)):
            if upd[i] in rules[upd[j]]:
                upd[j], upd[i] = upd[i], upd[j]  # part 2 swap
                isValid = False

    if not (part1 ^ isValid):
        return int(upd[len(upd) // 2])
    return 0


def solve(lines: list[str], part1: bool):
    rules = defaultdict(set)

    splitIdx = lines.index("")
    for l in lines[:splitIdx]:
        X, Y = l.split("|")
        rules[X].add(Y)

    updates = [l.split(",") for l in lines[splitIdx + 1 :]]
    return sum([scoreUpdate(rules, u, part1) for u in updates])


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = [line.strip() for line in f.readlines()]

    print("Part 1: ", solve(lines, True))
    print("Part 2: ", solve(lines, False))
