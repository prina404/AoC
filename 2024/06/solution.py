DIRS = ("^", ">", "v", "<")
MOVE = ((-1, 0), (0, 1), (1, 0), (0, -1))


def vSum(a: tuple, b: tuple):
    return (a[0] + b[0], a[1] + b[1])


def validCoord(mat: list[list[str]], i: int, j: int) -> str | bool:
    if 0 <= i < len(mat) and 0 <= j < len(mat[0]):
        return mat[i][j]
    return 0


def simulate(
    mat: list[list[str]], current: tuple, direction: int, pos: set, part1=True
) -> bool:
    visited = set()
    while True:
        nextCoord = vSum(current, MOVE[direction])
        if not validCoord(mat, *nextCoord):
            return False

        if (direction, nextCoord) in visited:
            return True
        visited.add((direction, current))

        if validCoord(mat, *nextCoord) == "#":
            direction = (direction + 1) % 4
            continue
        current = nextCoord
        if part1:
            pos.add(current)


def getStartPosition(mat: list[list[str]]) -> tuple:
    for i in range(len(mat)):
        for j in range(len(mat[0])):
            if mat[i][j] != "." and mat[i][j] != "#":
                return i, j
    raise Exception


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        mat = [list(r.strip()) for r in f.readlines()]

    start = getStartPosition(mat)
    direction = DIRS.index(mat[start[0]][start[1]])

    visited = set()
    simulate(mat, start, direction, visited, True)
    print("Part 1: ", len(visited))

    count = 0
    for i, j in visited:
        mat[i][j] = "#"
        count += simulate(mat, start, direction, visited, False)
        mat[i][j] = "."

    print("Part 2: ", count)
