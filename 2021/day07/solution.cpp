#include <algorithm>
#include <cmath>

#include "../util/inputUtils.cpp"

using namespace std;

int gaussSum(int n) {
    return n * (n + 1) / 2;
}

int cost(const vector<int>& nums, int target, bool part1) {
    int cost = 0;
    for (auto& n : nums)
        cost += (part1) ? abs(n - target) : gaussSum(abs(n - target));
    return cost;
}

int solve(const vector<int>& nums, int biggest, bool part1 = true) {
    int current = cost(nums, 0, part1);
    int next;
    for (int i = 0; i < biggest; i++) {
        if ((next = cost(nums, i + 1, part1)) > current)
            return current;
        current = next;
    }
    return 0;
}

int main() {
    auto positions = getIntsFromString(readInput("input.txt")[0], ",");
    cout << "Part 1:" << solve(positions, 1500) << endl;
    cout << "Part 2:" << solve(positions, 1500, false) << endl;
}