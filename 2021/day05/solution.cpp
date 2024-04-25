#include <cmath>
#include <map>

#include "../util/inputUtils.cpp"

using namespace std;

struct coord {
    int x;
    int y;
    auto operator<=>(const coord&) const = default;
};

struct line {
    coord start;
    coord end;

    uint len() const {
        return max(abs(start.x - end.x), abs(start.y - end.y));
    }

    bool horizontal() const {
        return start.y == end.y;
    }

    bool vertical() const {
        return start.x == end.x;
    }

    auto operator<=>(const line&) const = default;
};

int solve(const vector<line>& coordinates, bool part1 = true) {
    map<coord, int> intersections{};
    int x, y;
    int cx, cy;
    for (auto& ln : coordinates) {
        if (part1 && !(ln.vertical() ^ ln.horizontal()))
            continue;
        x = min(ln.start.x, ln.end.x);
        y = min(ln.start.y, ln.end.y);
        for (size_t i = 0; i <= ln.len(); i++) {
            cx = x + (i * ln.horizontal());
            cy = y + (i * ln.vertical());
            if (!(ln.vertical() || ln.horizontal())) {  // diagonal line
                cx = (ln.start.x > ln.end.x) ? ln.start.x - i : x + i;
                cy = (ln.start.y > ln.end.y) ? ln.start.y - i : y + i;
            }
            coord square{cx, cy};
            intersections[square] += 1;
        }
    }

    int count{};
    for (auto& [k, v] : intersections)
        if (v > 1)
            count++;
    return count;
}

int main() {
    vector<string> lines = readInput("input.txt");
    vector<line> coordinates{};
    for (auto l : lines) {
        auto nums = getIntsFromString(l.replace(l.find(" -> "), 4, ","), ",");
        coordinates.push_back(line{{nums[0], nums[1]}, {nums[2], nums[3]}});
    }

    cout << "Part 1: " << solve(coordinates) << endl;
    cout << "Part 2: " << solve(coordinates, false) << endl;

    return 0;
}
