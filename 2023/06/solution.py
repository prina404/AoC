import numpy as np


def solve(times, distances): # bruteforce approach :)
    res = [0] * len(times)
    for i, timeToBeat in enumerate(times):
        for second in range(1, timeToBeat):
            if (timeToBeat - second) * second > distances[i]:
                res[i] += 1
    return np.prod(res)


if __name__ == "__main__":  
    lines = open("input.txt").readlines()

    times = [int(x) for x in lines[0].split(":")[1].strip().split()]
    dist = [int(x) for x in lines[1].split(":")[1].strip().split()]

    totTime = int(lines[0].split(":")[1].replace(" ", ""))
    totDist = int(lines[1].split(":")[1].replace(" ", ""))

    print("part 1: ",solve(times, dist))
    print("part 2: ",solve([totTime], [totDist]))
