def solve(lines):
    dial = 50
    part1 = part2 = 0
    for l in lines:
        dir, n = l[0], int(l[1:])
        step = -1 if dir == 'L' else 1
        for _ in range(n):
            dial += step
            dial = dial % 100
            if dial == 0:
                part2 += 1
        if dial == 0:
            part1 += 1
    return part1, part2

if __name__ == "__main__":
    with open("input.txt") as f:
        lines = f.readlines()
    
    print("Part1: {}\nPart2: {}".format(*solve(lines)))