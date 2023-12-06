import numpy as np


def binarySearch(n, fn):  # fn returns True if n is too big, False otherwise
    n //= 2
    interval = n // 2
    while True:
        if fn(n) and not fn(n - 1):
            return n
        if interval < 1:
            interval = 1
        if fn(n):
            n -= interval
        else:
            n += interval
        interval //= 2


def betterSolver(time, distance):
    second = binarySearch(time, lambda x: (time - x) * x > distance)
    return time - (2 * second) + 1


def solve(times, distances):
    res = []
    for i, timeToBeat in enumerate(times):
        res.append(betterSolver(timeToBeat, distances[i]))
    return np.prod(res)


if __name__ == "__main__":
    lines = open("input.txt").readlines()

    times = [int(x) for x in lines[0].split(":")[1].strip().split()]
    dist = [int(x) for x in lines[1].split(":")[1].strip().split()]

    totTime = int(lines[0].split(":")[1].replace(" ", ""))
    totDist = int(lines[1].split(":")[1].replace(" ", ""))

    print("part 1: ", solve(times, dist))
    print("part 2: ", solve([totTime], [totDist]))
