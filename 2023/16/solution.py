dirDict = {"R": (0, 1), "L": (0, -1), "U": (-1, 0), "D": (1, 0)}

reflectDict = {
    "R|": "UD",
    "L|": "UD",
    "R\\": "D",
    "R/": "U",
    "L\\": "U",
    "L/": "D",
    "U-": "LR",
    "D-": "LR",
    "U\\": "L",
    "U/": "R",
    "D\\": "R",
    "D/": "L",
}


def checkBounds(i, j, mat):
    return 0 <= i < len(mat) and 0 <= j < len(mat[0])


EXL= set()
def solve(direction, i, j, mat):
    while True:
        if (direction, i, j) in EXL:
            return
        EXL.add((direction, i, j))

        i += dirDict[direction][0]
        j += dirDict[direction][1]
        if not checkBounds(i, j, mat):
            return

        next = mat[i][j]
        token = direction + next

        direction = reflectDict[token] if token in reflectDict else direction
        if len(direction) > 1:
            solve(direction[0], i, j, mat)
            solve(direction[1], i, j, mat)
            return


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    mat = [list(l.strip()) for l in lines]
    solve("R", 0, -1, mat)
    print("Part 1 :", len(set([(x, y) for _, x, y in EXL])) - 1)

    startTup = []
    for j in range(len(mat[0])):
        startTup.append(("D", -1, j, mat))
        startTup.append(("U", len(mat), j, mat))
    for i in range(len(mat)):
        startTup.append(("R", i, -1, mat))
        startTup.append(("L", i, len(mat[0]), mat))

    scores = []
    for tup in startTup:
        EXL.clear()
        solve(*tup)
        scores.append(len(set((x, y) for _, x, y in EXL)) - 1)
    maxScore = max(scores)
    print("Part 2: ", maxScore)
