import numpy as np
from numpy.typing import NDArray

def solve(arr: NDArray, part1: bool) -> int:
    row, col = arr.shape
    res = 0
    removed = True
    while removed:
        removed = False
        for i in range(1, row - 1):
            for j in range(1, col - 1):
                if arr[i, j] == 1 and np.sum(arr[i - 1 : i + 2, j - 1 : j + 2]) <= 4:
                    res += 1
                    if not part1:
                        arr[i, j] = 0
                        removed = True
    return res


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = f.readlines()
    lines = list(map(str.strip, lines))
    arr = np.zeros_like(lines, dtype=int, shape=(len(lines), len(lines[0])))
    for i in range(len(lines)):
        for j in range(len(lines[0])):
            arr[i, j] = lines[i][j] == "@"
    arr = np.pad(arr, (1, 1), constant_values=0)

    print("Part 1: ", solve(arr, True))
    print("Part 2: ", solve(arr, False))
