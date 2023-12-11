from copy import deepcopy
from dataclasses import dataclass

@dataclass
class Galaxy():
    x: int
    y: int
    xP: int = 0
    yP: int = 0

    def manhattanDist(self, other):
        return abs(self.x - other.x) + abs(self.y-other.y) 
    
    def commit(self):
        self.x += self.xP
        self.y += self.yP


def expandGalaxy(mat, galaxies, expansion = 1):
    # Vertical:
    for j in range(len(mat[0])):
        if not any(line[j] == '#' for line in mat):
            for gal in galaxies:
                if gal.x > j:
                    gal.xP += expansion-1
    # Horizontal
    for i in range(len(mat)):
        if not any([v == '#' for v in mat[i]]):
            for gal in galaxies:
                if gal.y > i:
                    gal.yP += expansion-1
    for gal in galaxies:
        gal.commit()

def solve(galaxies):
    res = 0
    for i, g1 in enumerate(galaxies[:-1]):
        for g2 in galaxies[i+1:]:
            res += g1.manhattanDist(g2)
    return res

if __name__ == "__main__":
    lines = open('input.txt').readlines()

    galaxies = []
    for i in range(len(lines)):
        for j, v in enumerate(lines[i]):
            if v == '#':
                galaxies.append(Galaxy(j,i))

    copy = deepcopy(galaxies)
    expandGalaxy(lines, galaxies, 2)
    print(f"part1: {solve(galaxies)}")
    
    expandGalaxy(lines, copy, 1000000)
    print(f"part2: {solve(copy)}")
    