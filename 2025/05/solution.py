
def part1(ranges, items):
    res = 0
    for item in items:
        inrange = False
        for lhs, rhs in ranges:
            if (int(lhs) <= item <= int(rhs)):
                inrange = True
        res += inrange
    return res

def shrink_overlap(r, o):
    low, high = r
    if o[0] <= high <= o[1]:
        high = o[0] - 1
    if o[0] <= low <= o[1]:
        low = o[1] + 1
    if high < low:
        return (0,0)
    return low, high

def part2(ranges):
    res = 0
    for i, r in enumerate(ranges):
        for other in ranges:
            if r == other:
                continue
            r = shrink_overlap(r, other)
        ranges[i] = r
        
    for r in set(ranges):
        if r != (0,0):
            res += r[1] - r[0] + 1

    return res 
if __name__ == "__main__":
    with open("input.txt") as f:
        lines = f.readlines()
    ranges = []
    items = []

    for l in lines:
        if l.strip() == '':
            continue
        if "-" in l:
            ranges.append(tuple(map(int, l.strip().split('-'))))
        else:
            items.append(int(l.strip()))

    print(part1(ranges, items))
    print(part2(ranges))