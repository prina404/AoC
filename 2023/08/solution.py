import math
import re


def multiNodes(graph, instructions):
    ghostNodes = [node for node in graph if node.endswith("A")]
    ghostSteps = []
    for node in ghostNodes:
        ghostSteps.append(
            getSteps(graph, instructions, node, lambda x: x.endswith("Z"))
        )
    return math.lcm(*ghostSteps)


def getSteps(graph, instructions, node, isEndNode):
    step = 0
    while not isEndNode(node):
        direction = instructions[step % len(instructions)]
        node = graph[node][0] if direction == "L" else graph[node][1]
        step += 1
    return step


def makeGraph(lines):
    graph = {}
    for line in lines[2:]:
        words = re.findall("[\w]+", line)
        graph[words[0]] = words[1:]
    return graph


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    instructions = lines[0].strip()
    graph = makeGraph(lines)
    part1 = getSteps(graph, instructions, "AAA", lambda x: x == "ZZZ")
    part2 = multiNodes(graph, instructions)
    print(f"{part1=}\n{part2=}")
