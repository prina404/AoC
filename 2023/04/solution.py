
def solution(lines: list[str]):
    res = 0
    winDict= {k:1 for k in range(len(lines))}
    for k, line in enumerate(lines):
        splitLine = line.split('|')
        winning = set(splitLine[0].split())
        mine = set(splitLine[1].split())

        i = 0
        for n in mine:
            if n in winning:
                i += 1 
        if i == 0:
            continue

        for j in range(i):
            winDict[k + j + 1] += winDict[k]
        res += 2**(i - 1) 
        
    print("part1:", res)
    print("part2:", sum(winDict.values()))

if __name__ == "__main__":
    lines = open("input.txt").readlines()
    lines = [line.split(':')[1] for line in lines]
    solution(lines)