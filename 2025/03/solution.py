def startIdx(line: str, idx: int, num_batteries: int) -> int:
    start_range = line[idx : len(line) - num_batteries + 1]
    return idx + start_range.index(max(start_range))

def getJoltage(line: str, n_batteries: int) -> int:
    result = ""
    idx = 0
    for i in range(n_batteries):
        idx = startIdx(line, idx, n_batteries - i)
        result += line[idx]
        idx += 1
    return int(result)

if __name__ == "__main__":
    with open("input.txt") as f:
        lines = f.readlines()

    print("Part1: ", sum([getJoltage(l.strip(), 2) for l in lines]))
    print("Part2: ", sum([getJoltage(l.strip(), 12) for l in lines]))
