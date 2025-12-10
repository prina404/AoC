import sys
from pulp import LpProblem, LpVariable, LpMinimize, lpSum, LpInteger, value, GLPK


def press_btn(state: str, btn: tuple[int]) -> str:
    new_state = list(state)
    for i in btn:
        new_state[i] = "#" if state[i] == "." else "."
    return "".join(new_state)


def min_cost(state: str, buttons: list[tuple], target: str, score={}, visited=set()) -> int | None:
    if state == target:
        score[state] = 0

    if state in score:
        return score[state]

    if state in visited:
        return None
    visited.add(state)

    distances = []
    for btn in buttons:
        new_state = press_btn(state, btn)
        new_score = min_cost(new_state, buttons, target, score, visited)
        if new_score is not None:
            distances.append(new_score + 1)

    score[state] = min(distances) if distances else None
    return score[state]


def btn_to_mask(button, max_len):
    mask = [0] * max_len
    for b in button:
        mask[b] = 1
    return tuple(mask)


def pulp_solver(buttons, joltage):
    prob = LpProblem("ButtonPress", LpMinimize)
    x = [LpVariable(f"x_{i}", lowBound=0, cat=LpInteger) for i in range(len(buttons))]
    prob += lpSum(x)
    for j in range(len(joltage)):
        prob += (
            lpSum((1 if j in buttons[i] else 0) * x[i] for i in range(len(buttons))) == joltage[j]
        )
    prob.solve(GLPK(msg=0))
    return int(value(prob.objective))


def parseLine(line):
    return (
        str(line[0][1:-1]),
        [tuple(int(i) for i in b[1:-1].split(",")) for b in line[1:-1]],
        str(line[-1][1:-1]),
    )


def solve(lines):
    sys.setrecursionlimit(5000)
    p1 = p2 = 0
    for line in lines:
        target, buttons, joltage = parseLine(line)
        state_zero = "." * len(target)
        p1 += min_cost(state_zero, buttons, target, {}, set())
        p2 += pulp_solver(buttons, eval(joltage))

    return p1, p2


if __name__ == "__main__":
    with open("input.txt") as f:
        lines = [l.strip().split() for l in f.readlines()]

    print("Part 1: {}\nPart 2: {}".format(*solve(lines)))
