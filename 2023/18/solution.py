import shapely

dirDict = {"R": (0, 1), "L": (0, -1), "U": (-1, 0), "D": (1, 0)}


def vectorSum(v1, v2, step):
    return (v1[0] + step * v2[0], v1[1] + step * v2[1])


def unpackCoords(lines, part2):
    dirs = ["R", "D", "L", "U"]
    current = (0, 0)
    coords = [current]
    for line in lines:
        if part2:
            substr = dirs[int(line[-3])], int(line[-8:-3], 16)
        else:
            substr = line.split()[:2]
        direction, step = substr
        current = vectorSum(current, dirDict[direction], int(step))
        coords.append(current)
    return coords


def solve(lines, part2):
    coords = unpackCoords(lines, part2)
    poly = shapely.Polygon(coords)
    return int(poly.area + (poly.length + 2) / 2)


if __name__ == "__main__":
    lines = open("input.txt").readlines()

    print("part1: ", solve(lines, False))
    print("part2: ", solve(lines, True))
