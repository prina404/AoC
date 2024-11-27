#include <numeric>

#include "../util/inputUtils.cpp"

using namespace std;

struct Pos {
    int x{};
    int y{};
};

bool probeLands(Pos v, auto& xR, auto& yR) {  // simulate throw
    Pos pos{0, 0};
    while (true) {
        pos.x += v.x;
        pos.y += v.y--;
        v.x -= (v.x) ? 1 : 0;

        if (pos.x > xR[1] || pos.y < yR[0])
            return false;
        if (pos.x >= xR[0] && pos.x <= xR[1] && pos.y <= yR[1] && pos.y >= yR[0])
            return true;
    }
}

int part2(auto& xR, auto& yR) {
    int count{};
    for (int y = abs(yR[0]) + 1; y >= yR[0] - 1; y--)
        for (int x = 0; x <= xR[1]; x++)
            if (probeLands({x, y}, xR, yR))
                count++;
    return count;
}

int part1(auto& yRange) {
    int maxY = abs(yRange[0]);
    return maxY * (maxY - 1) / 2;
}

int main() {
    auto lines = readInput("input.txt");

    vector<string> nums = split(lines[0].substr(15), ", ");
    auto xRange = getIntsFromString(nums[0], "..");
    auto yRange = getIntsFromString(nums[1].substr(2), "..");

    cout << "Part 1: " << part1(yRange) << endl;
    cout << "Part 2: " << part2(xRange, yRange) << endl;
}