import random
from copy import deepcopy
from collections import defaultdict


def getRandomNodes(graph):
    n1 = random.choice(list(graph.keys()))
    n2 = random.choice(list(graph[n1]))
    return n1, n2


def collapseRandomEdge(graph):
    n1, n2 = getRandomNodes(graph)
    superNode = n1 + n2
    merged = graph[n1].union(graph[n2])

    merged.discard(n1)
    merged.discard(n2)

    del graph[n1]
    del graph[n2]

    graph[superNode] = set()
    for node in merged:
        if n1 in graph[node]:
            graph[node].remove(n1)
        if n2 in graph[node]:
            graph[node].remove(n2)

        graph[node].add(superNode)
        graph[superNode].add(node)


def karger(graph, backup):
    while len(graph) > 2:
        collapseRandomEdge(graph)
    subGraph = list(graph.keys())[0]
    subNodes = set([subGraph[i : i + 3] for i in range(0, len(subGraph), 3)])
    mincut = 0
    for node in subNodes:
        for nbr in backup[node]:
            if nbr not in subNodes:
                mincut += 1

    return mincut


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    graph = defaultdict(set)
    for l in lines:
        lhs, rhs = l.strip().split(": ")
        for node in rhs.split():
            graph[lhs].add(node)
            graph[node].add(lhs)

    mincut = 1e3
    backup = graph
    while mincut > 3:
        graph = deepcopy(backup)
        mincut = karger(graph, backup)

    keys = list(graph.keys())
    print("Merry xmas: ", len(keys[0]) * len(keys[1]) // 9)
