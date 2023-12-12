from functools import cache


# Heavily refactored solution, yet pretty slow
@cache
def solutions(springs, codes, currLen):
    if len(springs) == 0:
        return 1 if len(codes) == 0 and currLen == 0 else 0
    res = 0
    if springs[0] == '?':
        options = ['.', '#']
    else : 
        options = springs[0]

    for c in options:
        if c == '#':
            res += solutions(springs[1:], codes, currLen+1)
        else:
            if currLen > 0:
                if len(codes) > 0 and codes[0] == currLen:
                    res += solutions(springs[1:], codes[1:], 0)
            else:
                res += solutions(springs[1:], codes, 0)
    return res

def solve(lines):
    p1, p2 = 0, 0
    for line in lines:
        ln = line.split()
        codes = tuple([int(x) for x in ln[1].split(',')])
        p1 += solutions(ln[0]+'.', codes, 0)
        p2 += solutions(ln[0]+('?'+ln[0])*4+'.', (codes*5), 0)
    return p1, p2


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    p1, p2 = solve(lines)
    print(f"part1: {p1}")
    print(f"part2: {p2}")