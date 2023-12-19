from dataclasses import dataclass
import re


@dataclass
class Range:
    lower: list
    upper: list

    def splitRange(self, rule):
        idx = "xmas".index(rule[0])
        op = rule[1]
        num = int(rule[2:])

        l1, u1 = self.listCopy()
        l2, u2 = self.listCopy()

        u1[idx] = num - 1 if op == "<" else num
        l2[idx] = num + 1 if op == ">" else num

        if op == "<":
            return Range(l1, u1), Range(l2, u2)
        return Range(l2, u2), Range(l1, u1)

    def listCopy(self):
        return self.lower.copy(), self.upper.copy()

    def getCombinations(self):
        prod = 1
        for i in range(4):
            prod *= self.upper[i] - self.lower[i] + 1
        return prod


@dataclass
class WFlows:
    default: str
    rules: list[str]

    def processPart(self, components: list[int]):
        x, m, a, s = components
        for rule in self.rules:
            p1, p2 = rule.split(":")
            if eval(p1):
                return p2
        return self.default


def parseInstructions(lines) -> dict[str, WFlows]:
    res = {}
    for line in lines:
        name, rest = line[:-1].split("{")
        rules = []
        for rule in rest.split(","):
            if ":" not in rule:
                default = rule
            else:
                rules.append(rule)
        res[name] = WFlows(default, rules)
    return res


def solve(wflows, values):
    res = 0
    for value in values:
        componentValues = list(map(int, re.findall("[\d]+", value)))
        next = "in"
        while next not in "AR":
            next = wflows[next].processPart(componentValues)
        res += sum(componentValues) if next == "A" else 0
    return res


def solvep2(instruction: str, ranges: Range, wflows: dict[str, WFlows]):
    currentRange = ranges
    res = 0
    for rule in wflows[instruction].rules:
        condition, instr = rule.split(":")
        s1, s2 = currentRange.splitRange(condition)
        if instr == "A":
            res += s1.getCombinations()
        elif instr != "R":
            res += solvep2(instr, s1, wflows)
        currentRange = s2

    if (default := wflows[instruction].default) == "A":
        res += currentRange.getCombinations()
    elif default != "R":
        res += solvep2(default, currentRange, wflows)
    return res


if __name__ == "__main__":
    lines = open("input.txt").read().split("\n")
    instr, values = lines[: lines.index("")], lines[lines.index("") + 1 :]
    instr = parseInstructions(instr)
    print('part1:',solve(instr, values))
    print('part2:',solvep2("in", Range([1] * 4, [4000] * 4), instr))
