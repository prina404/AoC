import numpy as np
from numpy.typing import NDArray
import time

# Original solution
# def solve(arr: NDArray, part1: bool) -> int:
#     row, col = arr.shape
#     res = 0
#     removed = True
#     while removed:
#         removed = False
#         for i in range(1, row - 1):
#             for j in range(1, col - 1):
#                 if arr[i, j] == 1 and np.sum(arr[i - 1 : i + 2, j - 1 : j + 2]) <= 4:
#                     res += 1
#                     if not part1:
#                         arr[i, j] = 0
#                         removed = True
#     return res


# Conv2D trick by: https://sangillee.com/2024-07-27-how-to-compute-faster-conv2d-python/
# this runs 4x faster
def fast_conv2d(x, kernel):
    H, W = x.shape
    H_k, W_k = kernel.shape
    kernel_width_half = H_k // 2

    x_pad = np.pad(x, kernel_width_half)

    sub_matrices = np.lib.stride_tricks.as_strided(x_pad, (H, W, H_k, W_k), x_pad.strides * 2)
    return np.einsum("klij,ij->kl", sub_matrices, kernel)


def solve_faster(arr: NDArray, part1: bool) -> int:
    res = 0
    kernel = np.array([[1, 1, 1], [1, 0, 1], [1, 1, 1]])
    removed = True
    while removed:
        removed = False
        adjacent_count = fast_conv2d(arr, kernel)
        row, col = adjacent_count.shape
        for i in range(row):
            for j in range(col):
                if arr[i, j] == 1 and adjacent_count[i, j] < 4:
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

    a = time.perf_counter()
    print("Part 1: ", solve_faster(arr, True))
    b = time.perf_counter()
    print("Part 2: ", solve_faster(arr, False))
    c = time.perf_counter()

    print("Fast Part 1 time: ", b - a)
    print("Fast Part 2 time: ", c - b)
