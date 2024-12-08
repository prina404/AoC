from collections import defaultdict
import itertools


def checkBounds(mat: list[list[str]], antinode: tuple) -> bool:
    return 0 <= antinode[0] < len(mat) and 0 <= antinode[1] < len(mat[0])


def getAntiNodes(mat: list[list[str]], coords: list[tuple], part2: bool) -> set[tuple]:
    antinodes = set()
    for (y1, x1), (y2, x2) in itertools.combinations(coords, 2):

        dx = x2 - x1
        dy = y2 - y1

        k = 1
        while True:
            leftNode  = (y2 + dy * k, x2 + dx * k)
            rightNode = (y1 - dy * k, x1 - dx * k)
            inbound = False

            if checkBounds(mat, leftNode):
                antinodes.add(leftNode)
                inbound = True
            if checkBounds(mat, rightNode):
                antinodes.add(rightNode)
                inbound = True
            if not (part2 and inbound):
                break

            k += 1

    return antinodes


def solve(mat: list[list[str]], part2: bool) -> int:
    antennas = defaultdict(list)

    for i in range(len(mat)):
        for j in range(len(mat[0])):
            if mat[i][j] != ".":
                antennas[mat[i][j]].append((i, j))

    antinodes = set()
    for transmitter in antennas.values():
        antinodes |= getAntiNodes(mat, transmitter, part2)
        if part2:
            antinodes |= set(transmitter)
    return len(antinodes)


if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = [[c for c in line.strip()] for line in f.readlines()]

    print("Part 1:", solve(lines, False))
    print("Part 2:", solve(lines, True))
