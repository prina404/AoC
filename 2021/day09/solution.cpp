#include <algorithm>
#include <set>

#include "../util/inputUtils.cpp"

using namespace std;

struct Coord {
    const size_t x;
    const size_t y;
    auto operator<=>(const Coord&) const = default;
};

vector<Coord> conn4neighbour(const Coord& p) {
    return {{p.x - 1, p.y}, {p.x + 1, p.y}, {p.x, p.y + 1}, {p.x, p.y - 1}};
}

bool isMinimum(const auto& mat, const Coord p) {
    for (auto& nb : conn4neighbour(p))
        if (mat[nb.y][nb.x] <= mat[p.y][p.x])
            return false;
    return true;
}

int getBasinSize(const auto& mat, const Coord& startPoint) {  // BFS
    set<Coord> visited{};
    set<Coord> EXL{startPoint};
    while (EXL.size() > 0) {
        Coord current = *(EXL.begin());
        EXL.erase(EXL.begin());

        if (visited.contains(current))
            continue;
        visited.insert(current);

        for (auto& nb : conn4neighbour(current))
            if (mat[nb.y][nb.x] != 9)
                EXL.insert(nb);
    }
    return visited.size();
}

int solve(const auto& matrix, bool part1 = true) {
    size_t result = 0;
    set<Coord> minimums{};
    for (size_t i = 1; i < matrix.size() - 1; i++){
        for (size_t j = 1; j < matrix[0].size() - 1; j++){
            if (isMinimum(matrix, Coord{j, i}))
                minimums.insert(Coord{j, i});
        }}

    if (part1) {
        for (auto& c : minimums)
            result += matrix[c.y][c.x] + 1;
        return result;
    }

    vector<int> bSize{};
    for (const Coord& min : minimums)
        bSize.push_back(getBasinSize(matrix, min));
    ranges::sort(bSize, ranges::greater());

    return bSize[0] * bSize[1] * bSize[2];
}

int main() {
    vector<string> lines = readInput("input.txt");
    vector<vector<uint>> matrix(lines.size() + 2, vector<uint>(lines[0].size() + 2, 9));

    for (size_t i = 1; i < lines.size() + 1; i++) {
        vector<int> digits = getIntsFromString(lines[i - 1], "");
        for (size_t j = 1; j < lines[0].size() + 1; j++)
            matrix[i][j] = digits[j - 1];
    }

    cout << "Part 1: " << solve(matrix) << endl;
    cout << "Part 2: " << solve(matrix, false) << endl;
}