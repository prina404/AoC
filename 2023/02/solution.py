import numpy as np

def part2(lines:str):
    result = 0
    for line in lines:
        gameDict = {'red':0, 'green':0, 'blue':0}
        hands = line.split('; ')
        for hand in hands:
            colors = hand.split(', ')
            for color in colors:
                num, colorStr = color.split(' ')
                gameDict[colorStr] = max(int(num), gameDict[colorStr])
        result += np.prod(list(gameDict.values()))
    return result

def part1(lines:str):
    maxValues = {'red' : 12, 'green' : 13, 'blue' : 14}
    idSum = 0
    for i, line in enumerate(lines):
        hands = line.split('; ')
        ok = True
        for hand in hands:
            colors = hand.split(', ')
            for color in colors:
                num, colorStr = color.split()
                if int(num) > maxValues[colorStr]:
                    ok = False
        if ok:
            idSum += i + 1
    return  idSum


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = f.readlines()
    lines = [line.split(': ')[1].strip() for line in lines]

    print(part1(lines))
    print(part2(lines))
