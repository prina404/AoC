class Node:
    def __init__(self, coord, id=None):
        self.coord = coord
        self.nbrs = {}
        self.id = id

    def __hash__(self):
        return self.coord.__hash__()

    def __eq__(self, __value):
        return self.coord == __value.coord


def traversePath(coord, mat, step, visited):
    start, end = (0, 1), (len(mat) - 1, len(mat[0]) - 2)
    if coord in visited:
        return None
    visited.add(coord)

    nbrs = getNeighbours(coord[0], coord[1], mat)

    if len(nbrs) >= 3:
        return coord, step
    elif len(nbrs) == 1:
        next = nbrs[0]
    else:
        next = nbrs[0] if nbrs[1] in visited else nbrs[1]

    if next == start or next == end:
        return next, step + 1
    return traversePath(next, mat, step + 1, visited)


def getAdjacentNodes(node, mat, nodes):
    for nb in getNeighbours(node.coord[0], node.coord[1], mat):
        path = traversePath(nb, mat, 1, set((node.coord,)))
        if not path:
            continue
        otherNode, dist = path
        for n in nodes:
            if n.coord == otherNode:
                node.nbrs[n] = dist


def getNeighbours(i, j, mat):
    step = [(1, 0), (-1, 0), (0, 1), (0, -1)]
    if mat[i][j] == ">":
        return [(i, j + 1)]
    if mat[i][j] == "v":
        return [(i + 1, j)]
    nbrs = []
    for s in step:
        nI, nJ = i + s[0], j + s[1]
        if 0 <= nI < len(mat) and 0 <= nJ < len(mat[0]) - 1 and mat[nI][nJ] != "#":
            nbrs.append((nI, nJ))
    return nbrs


def makeNodes(mat):
    nodes = []
    l1, l2 = len(mat), len(mat[0])
    for i in range(l1):
        for j in range(l2):
            if mat[i][j] != "#" and len(getNeighbours(i, j, mat)) > 2:
                nodes.append(Node((i, j)))
    nodes.extend([Node((0, 1), "start"), Node((l1 - 1, l2 - 2), "end")])
    return nodes


def compressGraph(mat):
    nodes = makeNodes(mat)
    for node in nodes:
        getAdjacentNodes(node, mat, nodes)
    return nodes


def DFS(graph):
    start = [x for x in graph if x.id == "start"][0]
    queue = [(start, 0, set())]
    res = 0
    while len(queue) > 0:
        node, cost, prevNodes = queue.pop()
        if node.id == "end":
            res = max(cost, res)
        for nb in node.nbrs:
            if nb in prevNodes:
                continue
            nbCost = node.nbrs[nb] + cost
            nS = prevNodes.copy()
            nS.add(node)
            queue.append((nb, nbCost, nS))
    return res


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    mat = [list(l.strip()) for l in lines]

    graph = compressGraph(mat)
    res = DFS(graph)
    print("part1", res)

    for i in range(len(mat)):
        for j in range(len(mat[0])):
            if mat[i][j] in [">", "v"]:
                mat[i][j] = "."

    graph = compressGraph(mat)
    res = DFS(graph)
    print("part2", res)
