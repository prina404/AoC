def naive_check(grid: list[str], tiles: dict[int, int]):
    h, w = grid[0].split("x")
    total_surface = int(h) * int(w)
    indices = tuple(map(int, grid[1].split()))
    tile_surface = sum(tiles[i] * n for i, n in enumerate(indices))
    return tile_surface <= total_surface


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = [l.strip() for l in f.readlines()]

    tiles = {}
    for i in range(6):
        tiles[i] = sum(s == "#" for s in "".join(lines[5*i : 5*i + 5]))
    grids = [l.split(": ") for l in lines if "x" in l]
    print("Part 1:", sum(naive_check(g, tiles) for g in grids))
