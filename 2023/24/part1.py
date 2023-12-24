def getCoefficients(pos, vel):
    px, py, _ = pos
    vx, vy, _ = vel
    m = vy / vx
    q = py - m * px
    return m, q


def computeIntersections(pos, vel):
    res = 0
    for i in range(len(pos) - 1):
        m1, q1 = getCoefficients(pos[i], vel[i])
        for j in range(i + 1, len(pos)):
            m2, q2 = getCoefficients(pos[j], vel[j])

            if m1 == m2:
                continue

            y_intersect = (m2 * q1 - m1 * q2) / (m2 - m1)
            x_intersect = (q2 - q1) / (m1 - m2)

            low, high = 200000000000000, 400000000000000

            if not low <= x_intersect <= high or not low <= y_intersect <= high:
                continue
            isValid = True
            for k in [i, j]:
                px, py, _ = pos[k]
                vx, vy, _ = vel[k]
                if (x_intersect < px and vx > 0) or (x_intersect > px and vx < 0):
                    isValid = False
                if (y_intersect < py and vy > 0) or (y_intersect > py and vy < 0):
                    isValid = False
            if isValid:
                res += 1
    print("part1: ", res)


if __name__ == "__main__":
    lines = open("input.txt").readlines()
    pos = []
    vel = []
    for line in lines:
        p = line.strip().split(" @ ")

        pos.append([*map(int, p[0].split(","))])
        vel.append([*map(int, p[1].split(","))])

    computeIntersections(pos, vel)
