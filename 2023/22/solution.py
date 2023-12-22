# Bad, slow bruteForce solution, needs a lot of refactoring


class Brick:
    def __init__(self, start, span, id=None):
        self.coord = start
        self.span = span
        self.id = id
        self.parent = set()

    def getCopy(self):
        newBrick = Brick(self.coord.copy(), self.span.copy(), self.id)
        return newBrick

    def canMoveDown(self, coordSet):
        if self.coord[2] == 1:
            return False
        if self.span[2] > 0:
            c = self.coord
            lowerC = c[0], c[1], c[2] - 1
            return lowerC not in coordSet
        for c in self.moveOneDownOccupancy():
            if c in coordSet:
                return False
        return True

    def getOccupiedCells(self):
        c = self.coord.copy()
        s = self.span.copy()
        res = []
        for _ in range(max(*s) + 1):
            res.append(tuple(c))
            for j in range(3):
                c[j] += 1 if s[j] > 0 else 0
        return res

    def moveDown(self, coordSet):
        while self.canMoveDown(coordSet):
            self.coord[2] -= 1

    def moveOneDownOccupancy(self):
        if self.coord[2] == 1:
            return []
        for c in self.getOccupiedCells():
            nc = c[0], c[1], c[2] - 1
            yield nc


def deepCopy(bricks):
    return [b.getCopy() for b in bricks]


def fall(bricks, occupancySet):
    movedBricks = set()
    for b in bricks:
        if b.canMoveDown(occupancySet):
            movedBricks.add(b.id)
            occupancySet.difference_update(b.getOccupiedCells())
            b.moveDown(occupancySet)
            occupancySet.update(b.getOccupiedCells())
    return occupancySet, movedBricks


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    bricks = []
    for i, line in enumerate(lines):
        start, end = line.strip().split("~")
        start = [int(x) for x in start.split(",")]
        end = [int(x) for x in end.split(",")]
        span = [abs(end[i] - start[i]) for i in range(3)]
        bricks.append(Brick(start, span, str(i)))

    bricks.sort(key=lambda x: x.coord[2])

    occupancySet = set()
    for b in bricks:
        occupancySet.update(b.getOccupiedCells())
    occupancySet, _ = fall(bricks, occupancySet)

    toDisintegrate = set()
    countFall = 0
    for i, b1 in enumerate(bricks):
        setWithout = occupancySet.difference(b1.getOccupiedCells())
        movedBricks = set()
        for b in bricks[:i] + bricks[i + 1 :]:
            if b.canMoveDown(setWithout):
                simul, mb = fall(deepCopy(bricks), setWithout.copy())
                movedBricks.update(mb)
                toDisintegrate.add(b1.id)
        countFall += len(movedBricks)

    print(f"part1: {len(bricks) - len(toDisintegrate)}")
    print(f"part2: {countFall}")
