import numpy as np
import time

NBRS = ((-1, -1), (-1, 0), (-1, 1),
        (0, -1),          (0, 1),
        (1, -1),  (1, 0), (1, 1))

def add_adjacent_candidates(i, j, nbr_list, arr) :
    for di, dj in NBRS:
        ni, nj = i + di, j + dj
        if arr[ni, nj] == 1:
            nbr_list.append((ni, nj))

def solve_fastest(arr):
    p1 = 0
    nbr_list = []
    bc = arr.copy()
    for i in range(1, arr.shape[0] - 1):
        for j in range(1, arr.shape[1] - 1):
            adjacent_count = np.sum(bc[i - 1 : i + 2, j - 1 : j + 2]) 
            if bc[i, j] == 1 and adjacent_count <= 4:
                arr[i, j] = 0
                add_adjacent_candidates(i, j, nbr_list, arr)
                p1 += 1
                
    p2 = p1
    nbr_set = set(nbr_list)
    while len(nbr_set) > 0:
        nbr_list = []
        for i, j in nbr_set:
            adjacent_count = np.sum(arr[i - 1 : i + 2, j - 1 : j + 2]) 
            if arr[i, j] == 1 and adjacent_count <= 4:
                arr[i, j] = 0
                add_adjacent_candidates(i, j, nbr_list, arr)
                p2 += 1

        nbr_set = set(nbr_list)
    return p1, p2

if __name__ == "__main__":
    with open("input.txt") as f:
        lines = f.readlines()
    lines = list(map(str.strip, lines))
    arr = np.zeros_like(lines, dtype=int, shape=(len(lines), len(lines[0])))
    for i in range(len(lines)):
        for j in range(len(lines[0])):
            arr[i, j] = lines[i][j] == "@"
    arr = np.pad(arr, (1, 1), constant_values=0)

    a = time.perf_counter()
    print("Part 1: {}\nPart 2: {}".format(*solve_fastest(arr)))
    print("-- Execution time: ", time.perf_counter() - a)
