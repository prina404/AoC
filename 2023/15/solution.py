import re
from collections import defaultdict


def HASH(s):
    current = 0
    for char in s:
        current += ord(char)
        current *= 17
        current %= 256
    return current


def score(d):
    res = 0
    for key in d:
        if not d[key]:
            continue
        boxscore = int(key) + 1
        for i, lens in enumerate(d[key]):
            res += boxscore * (i + 1) * int(lens[1])
    return res


def part2(line):
    d = defaultdict(int)
    for token in line:
        token = re.split("[=|-]+", token)
        label = token[0]
        lenses = d[HASH(label)]
        if token[1]:  # = operation
            if not lenses:
                d[HASH(label)] = [token]
            else:
                toAppend = True
                for i, lens in enumerate(lenses):
                    if lens[0] == label:
                        lenses[i] = token
                        toAppend = False
                        break
                if toAppend:
                    lenses.append(token)
        elif lenses:  # Remove
            for i, l in enumerate(lenses):
                if l[0] == label:
                    del lenses[i]
                    break
    return score(d)


if __name__ == "__main__":
    line = open("input.txt").readline().strip().split(",")
    print("part1:", sum(map(HASH, line)))
    print("part2:", part2(line))
