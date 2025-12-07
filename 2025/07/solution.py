from functools import cache
from copy import copy


@cache
def part2(current_beam: tuple[int, int], diffusers: frozenset[tuple[int, int]], depth: int) -> int:
    if current_beam is None:
        return 1
    i, j = current_beam
    lhs = rhs = None
    for k in range(i + 1, depth):
        if (k, j) not in diffusers:
            continue
        lhs = (k, j - 1) if not lhs else lhs
        rhs = (k, j + 1) if not rhs else rhs

    if lhs is None and rhs is None:  # no diffuser found for current_beam
        return 1
    return part2(lhs, diffusers, depth) + part2(rhs, diffusers, depth)


def part1(beams: set[int], diffusers: frozenset[tuple[int, int]], depth: int) -> int:
    res = 0
    for i in range(2, depth, 2):
        for j in copy(beams):
            if (i, j) in diffusers:
                res += 1
                beams.remove(j)
                beams.update([j - 1, j + 1])
    return res


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = f.readlines()

    start_beam = lines[0].index("S")
    diffusers = set()
    for i in range(len(lines)):
        for j in range(len(lines[0])):
            if lines[i][j] == "^":
                diffusers.add((i, j))

    diffusers = frozenset(diffusers)

    print("Part 1: ", part1({start_beam}, diffusers, len(lines)))
    print("Part 2: ", part2((0, start_beam), diffusers, len(lines)))
