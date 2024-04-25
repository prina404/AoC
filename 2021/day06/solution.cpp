#include <unordered_map>

#include "../util/inputUtils.cpp"

using namespace std;

inline uint32_t key(int i, int j) { return (uint32_t)i << 16 | (uint32_t)j; }

struct args {
    const int a1;
    const int a2;
};

size_t childrens(int timer, int daysLeft, unordered_map<uint32_t, size_t>& cache) {
    uint32_t my_key = key(timer, daysLeft);

    if (cache.contains(my_key))
        return cache[my_key];

    if (timer >= daysLeft) {
        cache[my_key] = 1;
        return 1;
    }

    if (timer == 0)
        return childrens(6, daysLeft - 1, cache) + childrens(8, daysLeft - 1, cache);

    cache[my_key] = childrens(timer - 1, daysLeft - 1, cache);
    return cache[my_key];
}

size_t solve(vector<int> start, int days, unordered_map<uint32_t, size_t>& cache) {
    size_t res = 0;
    for (auto& fish : start)
        res += childrens(fish, days, cache);
    return res;
}

int main() {
    vector<int> fish = getIntsFromString(readInput("input.txt")[0], ",");
    unordered_map<uint32_t, size_t> cache{};
    cout << "Part 1: " << solve(fish, 80, cache) << endl;
    cout << "Part 2: " << solve(fish, 256, cache) << endl;
}
