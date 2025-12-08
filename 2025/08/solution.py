import math


def dist(a, b):
    return math.sqrt((a[0] - b[0]) ** 2 + (a[1] - b[1]) ** 2 + (a[2] - b[2]) ** 2)


def solve(boxes: dict[int, tuple], circuits: dict[int, set], part1) -> int:
    distances = {}
    for i in range(len(boxes)):
        for j in range(i + 1, len(boxes)):
            distances[(i, j)] = dist(boxes[i], boxes[j])

    srt = sorted(distances.items(), key=lambda x: x[1])
    for i, pair in enumerate(srt):
        (a, b), _ = pair
        for idx, circuit in circuits.items():
            if a in circuit:
                a_circuit = idx  # index of circuit containing a
            if b in circuit:
                b_circuit = idx
        if a_circuit != b_circuit:
            if len(circuits) == 2:
                return boxes[a][0] * boxes[b][0]

            circuits[a_circuit] |= circuits[b_circuit]  # merge circuits & delete b
            del circuits[b_circuit]

        if i == 1000 and part1:
            break

    tops = sorted(list(map(len, circuits.values())), reverse=True)
    return tops[0] * tops[1] * tops[2]


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = [l.strip().split(",") for l in f.readlines()]

    boxes = {i: tuple(map(int, l)) for i, l in enumerate(lines)}
    circuits = {i: {i} for i in boxes}
    print("Part 1: ", solve(boxes, circuits, True))
    print("Part 2: ", solve(boxes, circuits, False))
