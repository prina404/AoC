from dataclasses import dataclass
from sortedcontainers import SortedSet
import heapq


dirDict = {"R": (0, 1), "L": (0, -1), "U": (-1, 0), "D": (1, 0)}


@dataclass
class Crucible:
    i: int
    j: int
    direction: str
    count: int
    dissipated: int = 0
    part2: bool = False

    def __hash__(self) -> int:
        return hash((self.i, self.j, self.direction, self.count))

    def __eq__(self, __value: object) -> bool:
        return (
            self.i == __value.i
            and self.j == __value.j
        )

    def getneighbours(self):
        dirLst = ["U", "D", "L", "R"]
        match self.direction:
            case "U":
                dirLst.remove("D")
            case "D":
                dirLst.remove("U")
            case "L":
                dirLst.remove("R")
            case "R":
                dirLst.remove("L")

        maxNum = 10 if self.part2 else 3
        if self.count == maxNum:
            dirLst.remove(self.direction)

        if self.part2 and self.count < 4:
            dirLst = [self.direction]

        nbrs = []
        for dir in dirLst:
            count = 1 if self.direction != dir else self.count + 1
            i = self.i + dirDict[dir][0]
            j = self.j + dirDict[dir][1]
            nbrs.append(Crucible(i, j, dir, count, part2=self.part2))
        return nbrs


def checkBounds(i, j, mat):
    return 0 <= i < len(mat) and 0 <= j < len(mat[0])

def BFS(start: Crucible, mat):
    EXL = set()
    frontier = SortedSet([start], lambda x: x.dissipated)
    bestScore = 1e10

    while len(frontier) > 0:
        current = frontier.pop(0)
        if current in EXL:
            continue
        EXL.add(current)
        
        if current.i == len(mat) - 1 and current.j == len(mat[0]) - 1:
            bestScore = min(bestScore, current.dissipated)
        for nb in current.getneighbours():
            if checkBounds(nb.i, nb.j, mat):
                nb.dissipated = current.dissipated + int(mat[nb.i][nb.j])
                nb.parent = current
                frontier.add(nb)

    return bestScore


if __name__ == "__main__":  # slow solution, needs refactoring
    lines = open("input.txt").readlines()
    lines = [list(l.strip()) for l in lines]
    start = Crucible(0, 0, "R", 0)
    cost = BFS(start, lines)
    print("part1: ", cost)

    start = Crucible(0, 0, "R", 0, part2=True)
    cost = BFS(start, lines)
    print("part2: ", cost)
