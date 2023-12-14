import numpy as np


def cycle(mat):
    mat = np.rot90(mat)
    for _ in range(4):
        mat = np.rot90(mat, 3)
        shiftRocks(mat)


def shiftRocks(mat):
    for i in range(1, mat.shape[1]):
        for j in range(mat.shape[0]):
            if mat[i, j] == "O":
                for k in range(1, i + 1):
                    p = mat[i - k, j]
                    if p == "O" or p == "#":
                        break
                    mat[i - k + 1, j] = "."
                    mat[i - k, j]     = "O"


def getScore(mat):
    score = 0
    for i in range(mat.shape[1]):
        score += len(np.where(mat[i, :] == "O")[1]) * (mat.shape[1] - i)
    return score


def getRepeatedCycle(mat: np.matrix):  # Very slow, needs refactoring
    cycle(mat)
    visited = {hash(mat.tobytes()): None}
    while True:
        cycle(mat)
        if hash(mat.tobytes()) in visited:
            offset = list(visited.keys()).index(hash(mat.tobytes()))
            return len(visited.keys()) - offset, offset
        visited[hash(mat.tobytes())] = None


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    mat = np.matrix([list(l.strip()) for l in lines])
    shiftRocks(mat)
    print("part1: ", getScore(mat))

    cycleLen, offset = getRepeatedCycle(mat)

    bigM = 1_000_000_000
    currIter = cycleLen * (bigM // cycleLen)
    while currIter + offset >= bigM:
        currIter -= cycleLen

    while currIter + offset < bigM - 1:
        cycle(mat)
        currIter += 1

    print(f"pt2: ", getScore(mat))
