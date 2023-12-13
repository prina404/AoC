import numpy as np


def fixSmudge(mat, idx):
    diffNum = 0
    for i in range(min(idx + 1, mat.shape[1] - idx - 1)):
        c1, c2 = mat[:, idx - i], mat[:, idx + 1 + i]
        for v1, v2 in zip(c1, c2):
            if v1 != v2:
                smudge = i
                diffNum += 1
    if diffNum != 1:
        return 0
    for i, v in enumerate(mat[:, idx - smudge]):
        if v != mat[i, idx + 1 + smudge]:
            mat[i, idx - smudge] = "." if v == "#" else "#"
            return idx + 1


def isMirror(mat, idx):
    for i in range(min(idx + 1, mat.shape[1] - idx - 1)):
        if not np.all(mat[:, idx - i] == mat[:, idx + 1 + i]):
            return False
    return True


def getSmudgeScore(mat):
    vSum = sum(fixSmudge(mat, i) for i in range(mat.shape[1] - 1))
    if vSum:
        return vSum
    return sum(fixSmudge(mat.T, i) * 100 for i in range(mat.shape[0] - 1))


def getMatScore(mat):
    rows, cols = mat.shape
    hScore = sum(i + 1 for i in range(cols - 1) if isMirror(mat, i))
    vScore = sum(i + 1 for i in range(rows - 1) if isMirror(mat.T, i)) * 100
    return hScore + vScore


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    patterns = []
    temp = []
    for line in lines:
        if line.strip() != "":
            temp.append(list(line.strip()))
        else:
            patterns.append(np.matrix(temp))
            temp = []

    print("part1: ", sum(getMatScore(mat) for mat in patterns))
    print("part2: ", sum(getSmudgeScore(mat) for mat in patterns))
