from functools import reduce
import numpy as np
from numpy.typing import NDArray


def extract_problems(mat: NDArray) -> dict[str, list[NDArray]]:
    cols = mat.shape[1]
    problems = {"+": [], "*": []}
    left = 0
    for j in range(1, cols):
        op = mat[-1, left]
        # if at the bottom there's an op sign, i finished parsing the previous col
        if mat[-1, j] in ("+", "*"):
            problems[op].append(mat[:-1, left : j - 1])
            left = j
        if j + 1 == cols:  # last col
            problems[op].append(mat[:-1, left : j + 1])
    return problems


def solve_problem(op: str, problem: NDArray) -> int:
    func = {"+": lambda x, y: x + y, "*": lambda x, y: x * y}
    nums = [int("".join(row)) for row in problem]
    return reduce(func[op], nums)  # type: ignore


def solve(mat: NDArray) -> tuple[int, int]:
    problems = extract_problems(mat)
    p1 = sum([solve_problem(op, p) for op in problems for p in problems[op]])
    p2 = sum([solve_problem(op, p.T) for op in problems for p in problems[op]])
    return p1, p2


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = [line.strip("\n") for line in f.readlines()]

    mat = np.array([[char for char in row] for row in lines])
    print("Part 1: {}\nPart 2: {}".format(*solve(mat)))
