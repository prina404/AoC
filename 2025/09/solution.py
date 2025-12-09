def point_in_rect(px, py, x1, y1, x2, y2):
    x1, x2 = min(x1, x2), max(x1, x2)
    y1, y2 = min(y1, y2), max(y1, y2)
    return x1 < px < x2 and y1 < py < y2


# check if a horizontal line intersects the rectangle
def line_rect_intersect(edge, rect):
    (x1_e, y1_e), (x2_e, y2_e) = edge
    x1_r, y1_r, x2_r, y2_r = rect
    xe, Xe = min(x1_e, x2_e), max(x1_e, x2_e)
    xr, Xr = min(x1_r, x2_r), max(x1_r, x2_r)
    yr, Yr = min(y1_r, y2_r), max(y1_r, y2_r)
    return ((xe <= xr and Xe >= Xr) or (Xe >= Xr and xe == xr)) and (yr < y1_e < Yr)


def solve(tiles: list[tuple[int, int]]) -> tuple[int, int]:
    rectangles = {}
    for i in range(len(tiles) - 1):
        for j in range(1, len(tiles)):
            x1, y1 = tiles[i]
            x2, y2 = tiles[j]
            rectangles[(x1, y1, x2, y2)] = (abs(x2 - x1) + 1) * (abs(y2 - y1) + 1)

    rectangles = dict(sorted(rectangles.items(), key=lambda x: x[1], reverse=True))
    horizontal_lines = [
        (tiles[i], tiles[i + 1]) for i in range(len(tiles) - 1) if tiles[i][1] == tiles[i + 1][1]
    ]

    for rect in rectangles:
        contains_tiles = False
        for tx, ty in tiles:
            if point_in_rect(tx, ty, *rect):
                contains_tiles = True
                break
        if contains_tiles:
            continue

        line_intersections = 0
        for line in horizontal_lines:
            line_intersections += line_rect_intersect(line, rect)

        if line_intersections == 0:
            return max(rectangles.values()), rectangles[rect]


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = [l.strip().split(",") for l in f.readlines()]

    lines = [(int(a), int(b)) for a, b in lines]
    print("Part 1: {}\nPart 2: {}".format(*solve(lines)))
