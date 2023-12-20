import math

# Over-engineering much?

LOW = 0
HIGH = 1


class Node:

    pulseCount = {LOW: 0, HIGH: 0}

    def __init__(self, name, otherNodes, debugNode=False):
        self.name = name.strip()
        self.nodes = [x.strip() for x in otherNodes.split(",")]
        self.inputBuffer = []
        self.debug = debugNode

    def registerObserver(self, name):
        pass

    def registerToConjunction(self, nodeDict):
        if not self.debug:
            for node in self.nodes:
                nodeDict[node].registerObserver(self.name)

    def receive(self, sourceNode, signal):
        self.pulseCount[signal] += 1
        if self.debug:
            return
        self.inputBuffer.append((sourceNode, signal))

    def processInput(self, nodeDict):
        pass

    def bufSize(self):
        return len(self.inputBuffer)


class Conjunction(Node):
    def __init__(self, name, otherNodes):
        super().__init__(name, otherNodes)
        self.observers = {}

    def registerObserver(self, name):
        self.observers[name] = LOW

    def processInput(self, nodeDict: dict[str, Node]):
        if len(self.inputBuffer) == 0:
            return
        sender, pulse = self.inputBuffer.pop(0)
        self.observers[sender] = pulse
        signal = LOW if all([x == HIGH for x in self.observers.values()]) else HIGH
        for node in self.nodes:
            nodeDict[node].receive(self.name, signal)
            
        return pulse 


class FlipFlop(Node):
    def __init__(self, name, otherNodes):
        super().__init__(name, otherNodes)
        self.state = "off"

    def processInput(self, nodeDict):
        if len(self.inputBuffer) == 0:
            return
        _, pulse = self.inputBuffer.pop()
        if pulse == LOW:
            self.state, signal = ("on", HIGH) if self.state == "off" else ("off", LOW)
            for node in self.nodes:
                nodeDict[node].receive(self.name, signal) 


def solve(broadcast, nodeDict: dict[str, Node]):
    terminalNode = [nodeDict[x] for x in nodeDict if "rx" in nodeDict[x].nodes][0]
    nParents = len(terminalNode.observers)
    parentCycle = []
    nIter = 0
    while True:
        nIter += 1
        for node in broadcast:  # Broadcast from source node
            nodeDict[node].receive(None, LOW)

        while sum([x.bufSize() for x in nodeDict.values()]) > 0:  # Process all nodes until no more pulses are exchanged
            for node in nodeDict:
                highPulse = nodeDict[node].processInput(nodeDict)
                if highPulse and node == terminalNode.name:  # cycle detection for part2
                    parentCycle.append(nIter)

        if nIter == 1000:  # part1
            print(f"part1: {(1000 + Node.pulseCount[LOW]) * Node.pulseCount[HIGH]}")

        if len(parentCycle) == nParents:  # part2
            print(f"part2: {math.lcm(*parentCycle)}")
            return


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    nodes: dict[str, Node] = {}
    broadcast: list[str]   = []
    for line in lines:
        name, others = line.split(" -> ")
        if name.startswith("%"):
            nodes[name[1:]] = FlipFlop(name[1:], others)
        elif name.startswith("&"):
            nodes[name[1:]] = Conjunction(name[1:], others)
        else:
            broadcast = [x.strip() for x in others.split(", ")]

    for node in list(nodes.values()):  # terminal dummy node detection (output | rx)
        for nbr in node.nodes:
            if nbr not in nodes:
                nodes[nbr] = Node(nbr, "", True)

    for node in nodes:  # Observable nodes register to observer conjunctions
        nodes[node].registerToConjunction(nodes)

    solve(broadcast, nodes)
