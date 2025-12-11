from functools import cache


def solve(graph, from_, to):
    @cache
    def DFS(start, goal):
        if start == "out":
            return 0
        walks = 0
        for nbr in graph[start]:
            if nbr == goal:
                return 1
            walks += DFS(nbr, goal)
        return walks

    return DFS(from_, to)


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = [l.strip().split() for l in f.readlines()]

    g = {l[0][:-1]: l[1:] for l in lines}
    print("Part 1: ", solve(g, "you", "out"))
    print("Part 2: ", solve(g, "svr", "fft") * solve(g, "fft", "dac") * solve(g, "dac", "out"))
