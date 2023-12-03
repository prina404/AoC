from dataclasses import dataclass

@dataclass
class Number():
    x: int
    y: int
    length: int

    def getNumber(self, matrix) -> int:
        num = ''.join([matrix[self.y][self.x + i] for i in range(self.length)])
        return int(num)
    
    def isSchematic(self, matrix) -> bool:
        for i in range(self.length):
            for coord in conn8neighbours(self.x + i, self.y):
                char = matrix[coord[1]][coord[0]]
                if char != '.' and not char.isdigit():
                    return True
        return False
    
    def isCloseToGear(self, matrix) -> tuple[int, int]:
        for i in range(self.length):
            for coord in conn8neighbours(self.x + i, self.y):
                char = matrix[coord[1]][coord[0]]
                if char == '*':
                    return (coord[1], coord[0])
        return None


def conn8neighbours(x: int, y: int):
    coords = []
    for i in range(3):
        for j in range(3):
            coords.append((x - 1 + i, y - 1 + j))
    return coords

def makeNumberList(lines: list[list[str]]):
    numList = []
    for i, line in enumerate(lines):
        j = 0
        while j < len(line):
            if not line[j].isdigit():
                j += 1
                continue
            x, y = j, i
            lenCounter = 0
            while line[j].isdigit():
                j += 1
                lenCounter += 1
            num = Number(x, y, lenCounter)
            numList.append(num)
    return numList

def part1(lines: list[list[str]]):
    numList = makeNumberList(lines)
    return sum([n.getNumber(lines) for n in numList if n.isSchematic(lines)]), numList
                
def part2(numList: list[Number], lines: list[str]) -> int:
    gearDict: dict[tuple, list[Number]] = {}
    res = 0
    for n in numList:
        gearNum = n.isCloseToGear(lines)
        if gearNum is None:
            continue
        if gearNum in gearDict.keys():
            gearDict[gearNum].append(n)
        else:
            gearDict[gearNum] = [n]
    
    for key in list(gearDict.keys()):
        if len((lst := gearDict[key])) == 2:
            res += lst[0].getNumber(lines) * lst[1].getNumber(lines)
    return res


def makePadding(lines:list[str]) -> list[list[str]]:
    padRow = ['.'] * (len(lines[0])+2)
    res = [padRow]
    for line in lines:
        res.append(['.', *list(line.strip()), '.'])
    res.append(padRow)
    return res
    
if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = f.readlines()
    lines = makePadding(lines)
    #print(lines)
    result, numList = part1(lines)
    print(result)
    print(part2(numList, lines))