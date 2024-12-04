directions = ((1, 0), (0, 1), (1, 1), (1, -1))


def checkBounds(mat: list[str], i: int, j: int) -> bool:
    if 0 <= i < len(mat) and 0 <= j < len(mat[0]):
        return mat[i][j]
    return ""


# Unreadable af ¯\_(ツ)_/¯
def checkNeighbours(mat: list[str], i: int, j: int, part1: bool) -> int:
    dirs = directions if part1 else directions[2:]
    _range = range(4) if part1 else range(-1, 2)
    word = "XMAS" if part1 else "MAS"

    count = 0
    for dy, dx in dirs:
        letters = "".join(checkBounds(mat, i + dy * k, j + dx * k) for k in _range)
        count += letters == word or letters == word[::-1]

    return count if part1 else count == 2


def solve(mat: list[str], part1: bool) -> int:
    return sum(
        [
            checkNeighbours(mat, i, j, part1)
            for i in range(len(mat))
            for j in range(len(mat[0]))
        ]
    )


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = ["".join(r.strip()) for r in f.readlines()]

    print("Part 1:", solve(lines, True))
    print("Part 2:", solve(lines, False))
