if __name__ == "__main__":
    with open("input.txt", "r") as f:
        lines = f.readlines()

    l1 = [int(pair.split()[0]) for pair in lines]
    l2 = [int(pair.split()[1]) for pair in lines]

    print("Part 1:", sum([abs(x - y) for x, y in zip(sorted(l1), sorted(l2))]))
    print("Part 2:", sum([x * l2.count(x) for x in l1]))
