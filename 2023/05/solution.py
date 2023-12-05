from dataclasses import dataclass


@dataclass
class Range:
    src: int
    dst: int
    length: int

    def getDst(self, srcNum: int) -> int:
        assert self.isInSrcRange(srcNum)
        return self.dst + (srcNum - self.src)

    def getSrc(self, dstNum: int) -> int:
        assert self.isInDstRange(dstNum)
        return self.src + (dstNum - self.dst)

    def isInSrcRange(self, srcNum: int) -> bool:
        return self.src <= srcNum < self.src + self.length

    def isInDstRange(self, dstNum: int) -> bool:
        return self.dst <= dstNum < self.dst + self.length


def getLocation(mappings: dict[str, list[Range]], seed: int):
    for _, lst in mappings.items():
        for rn in lst:
            if rn.isInSrcRange(seed):
                seed = rn.getDst(seed)
                break
    return seed


def getReverseLocation(mappings, location):
    for _, lst in reversed(mappings.items()):
        for rn in lst:
            if rn.isInDstRange(location):
                location = rn.getSrc(location)
                break
    return location


def part1(lines: list[str], seeds: list[int]):
    mappings: dict[str, list[Range]] = {}
    currentKey = ""
    for line in lines[1:]:
        if len(line.strip()) == 0:
            currentKey = ""
            continue
        if currentKey == "" and line.isascii:
            currentKey = line.split()[0]
        else:
            val = [int(x) for x in line.split()]
            rn = Range(val[1], val[0], val[2])
            if not currentKey in mappings.keys():
                mappings[currentKey] = [rn]
            else:
                mappings[currentKey].append(rn)
    return min([getLocation(mappings, seed) for seed in seeds]), mappings


def part2(lines, seeds, mappings):
    ## at first locate a small location number
    locations = []
    for i in range(0, len(seeds) - 1, 2):
        rangeIntervals = list(range(seeds[i], seeds[i] + seeds[i + 1] + 1, 20000))
        locations.append(part1(lines, rangeIntervals)[0])
    numToReverse = min(locations)
    print(f"found small location: {numToReverse}")
    ## do a reverse mapping of the location proximity
    reverseRange = list(range(numToReverse - 10000, numToReverse + 1))
    for i in reverseRange:
        n = getReverseLocation(mappings, i)
        for j in range(0, len(seeds) - 1, 2):
            if seeds[j] <= n <= seeds[j] + seeds[j + 1]:
                return i
    return None


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    seeds = [int(x) for x in lines[0].split(":")[1].split()]

    p1, mappings = part1(lines, seeds)
    print("part1: ", p1)
    print("part2:", part2(lines, seeds, mappings))
