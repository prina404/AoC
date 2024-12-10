from collections import deque

dirs = ((0, 1), (1, 0), (0, -1), (-1, 0))


def checkBounds(mat: list[list[str]], pos: tuple) -> int | None:
    if 0 <= pos[0] < len(mat) and 0 <= pos[1] < len(mat[0]):
        return mat[pos[0]][pos[1]]
    return None


def conn4nbr(mat: list[list[str]], pos: tuple):
    for d in dirs:
        elem = mat[pos[0]][pos[1]]
        newPos = (pos[0] + d[0], pos[1] + d[1])
        newElem = checkBounds(mat, newPos)
        if newElem and newElem - elem == 1:
            yield newPos


def BFS(mat: list[list[str]], startPos: tuple, part1: bool) -> int:
    visited = set()
    EXL = deque()
    EXL.append(startPos)
    score = 0
    while len(EXL) > 0:
        current = EXL.popleft()
        if part1 and current in visited:
            continue
        visited.add(current)
        if mat[current[0]][current[1]] == 9:
            score += 1

        for nbr in conn4nbr(mat, current):
            if nbr in visited:
                continue
            EXL.append(nbr)
            
    return score


def solve(mat: list[list[str]], part1: bool) -> int:
    return sum(
        [
            BFS(mat, (i, j), part1)
            for i in range(len(mat))
            for j in range(len(mat[0]))
            if mat[i][j] == 0
        ]
    )


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = [[int(c) for c in line.strip()] for line in f.readlines()]

    print("Part 1:", solve(lines, True))
    print("Part 2:", solve(lines, False))
