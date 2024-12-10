from dataclasses import dataclass


@dataclass
class Blocks:
    id: int
    numBlocks: int
    startIdx: int


@dataclass
class FreeSpace:
    startIdx: int
    size: int


def parseInput(line: str) -> tuple[list[Blocks], list[FreeSpace]]:
    blockList, freeList = [], []
    id, idx = 0, 0
    for i in range(0, len(line), 2):
        blockNum = int(line[i])
        blockList += [Blocks(id, blockNum, idx)]
        id += 1
        idx += blockNum

        if i + 1 < len(line):
            freeSize = int(line[i + 1])
            freeList.append(FreeSpace(idx, freeSize))
            idx += freeSize

    return blockList, freeList


def scoreBlocks(blockList: list[Blocks]) -> int:
    res = 0
    for b in blockList:
        for k in range(b.numBlocks):
            res += (b.startIdx + k) * b.id
    return res


def part2(line: str) -> int:
    blockList, freeList = parseInput(line)
    for b in blockList[::-1]:
        for f in freeList:
            if f.size >= b.numBlocks and f.startIdx < b.startIdx:
                freeList.append(FreeSpace(b.startIdx, b.numBlocks))
                b.startIdx = f.startIdx
                f.startIdx += b.numBlocks
                f.size -= b.numBlocks
                if f.size == 0:
                    freeList.remove(f)
                break

    blockList.sort(key=lambda b: b.startIdx)
    return scoreBlocks(blockList)


def part1(line: str) -> int:
    layout = []
    id = 0
    for i in range(0, len(line), 2):
        blocks = int(line[i])
        free = int(line[i + 1]) if i + 1 < len(line) else 0
        layout += [id for _ in range(blocks)] + ["." for _ in range(free)]
        id += 1

    lhs = 0
    rhs = len(layout) - 1
    while lhs < rhs:
        if layout[lhs] != ".":
            lhs += 1
            continue
        if layout[rhs] == ".":
            rhs -= 1
            continue

        layout[lhs], layout[rhs] = layout[rhs], layout[lhs]
        lhs += 1
        rhs -= 1

    return sum([i * layout[i] for i in range(len(layout)) if layout[i] != "."])


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        line = f.readlines()[0].strip()

    print("Part 1:", part1(line))
    print("Part 2:", part2(line))
