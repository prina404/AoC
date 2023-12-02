import numpy as np
import re

def part2(lines:str):
    result = 0
    for line in lines:
        gameDict = {'red':0, 'green':0, 'blue':0}
        lst = re.split('[; |, ]+', line)
        for i in range(0, len(lst), 2):
            num, color = lst[i:i+2]
            gameDict[color] = max(int(num), gameDict[color])
        result += np.prod(list(gameDict.values()))
    return result

def part1(lines:str):
    maxValues = {'red' : 12, 'green' : 13, 'blue' : 14}
    idSum = 0
    for i, line in enumerate(lines):
        ok = True
        lst = re.split('[; |, ]+', line)
        for j in range(0, len(lst), 2):
            num, color = lst[j:j+2]
            if int(num) > maxValues[color]:
                ok = False
        if ok:
            idSum += i + 1
    return idSum


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = f.readlines()
    lines = [line.split(': ')[1].strip() for line in lines]
    print(part1(lines))
    print(part2(lines))
