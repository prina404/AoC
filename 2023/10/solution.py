from copy import deepcopy
import numpy as np


def getNeighbours(c, matrix):
    match matrix[c]:
        case "|": return (c[0] + 1, c[1]), (c[0] - 1, c[1])
        case "-": return (c[0], c[1] + 1), (c[0], c[1] - 1)
        case "F": return (c[0] + 1, c[1]), (c[0], c[1] + 1)
        case "7": return (c[0] + 1, c[1]), (c[0], c[1] - 1)
        case "L": return (c[0] - 1, c[1]), (c[0], c[1] + 1)
        case "J": return (c[0] - 1, c[1]), (c[0], c[1] - 1)
        case _  : return []


def extendPipes(mat):
    res = mat.copy()
    for i in range(1, len(mat) - 1):
        for j in range(1, len(mat[:]) - 1):
            match mat[i][j]:
                case "|": res[i + 1, j] = "|"; res[i - 1, j] = "|"
                case "-": res[i, j + 1] = "-"; res[i, j - 1] = "-"
                case "F": res[i + 1, j] = "|"; res[i, j + 1] = "-"
                case "7": res[i + 1, j] = "|"; res[i, j - 1] = "-"
                case "L": res[i - 1, j] = "|"; res[i, j + 1] = "-"
                case "J": res[i - 1, j] = "|"; res[i, j - 1] = "-"
                case _  : pass
    return res


def makePadding(lines: list[str]) -> list[list[str]]:
    padRow = ["."] * (len(lines[0]) + 1)
    res = [padRow]
    for line in lines:
        res.append([".", *list(line.strip()), "."])
    res.append(padRow)
    return np.matrix(res, dtype=np.str_)


def BFS(startCoord, matrix):
    EXL: set[tuple] = set()
    distances: dict[tuple, int] = {}
    queue = []
    queue.append(startCoord)
    distances[startCoord] = 0
    while len(queue) > 0:
        current = queue.pop(0)
        EXL.add(current)
        for nb in getNeighbours(current, matrix):
            if nb in EXL:
                continue
            if nb not in distances:
                distances[nb] = distances[current] + 1
            queue.append(nb)
    print(f"part1: {max(distances.values())}")
    return distances.keys()


def conn4neighbours(x: int, y: int):
    return [(x - 1, y), (x + 1, y), (x, y + 1), (x, y - 1)]


def floodFill(matrix):
    queue = []
    queue.append((0, 0))
    visited = set()
    while len(queue) > 0:
        coord = queue.pop(0)
        matrix[coord[0]][coord[1]] = "O"
        for nb in conn4neighbours(coord[0], coord[1]):
            if matrix[nb[0]][nb[1]] == "." and nb not in visited:
                queue.append(nb)
                visited.add(nb)


def doubleMatrix(mat):
    newMat = np.full((mat.shape[0] * 2, mat.shape[1] * 2), ".")
    for i in range(len(mat)):
        for j in range(len(mat[:])):
            newMat[2 * i, 2 * j] = mat[i, j]

    res = extendPipes(newMat)
    floodFill(res)
    return halfMatrix(res)


def halfMatrix(mat):
    newMat = np.full((mat.shape[0] // 2, mat.shape[1] // 2), ".")
    for i in range(len(newMat)):
        for j in range(len(newMat[:])):
            newMat[i, j] = mat[2 * i, 2 * j]
    return newMat


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    lines = makePadding(lines)
    start = np.where(lines == "S")
    start = start[0][0], start[1][0]
    lines[start] = "|" # Hardcoded for simplicity

    loopNodes = BFS(start, lines)
    
    for i in range(len(lines)):
        for j in range(len(lines[:])):
            if (i, j) not in loopNodes:
                lines[i, j] = "."

    mat = doubleMatrix(lines)

    print("part2: ", np.count_nonzero(mat == "."))
