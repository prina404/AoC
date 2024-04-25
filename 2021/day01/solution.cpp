#include <fstream>
#include <iostream>
#include <limits>
#include <vector>
#include "../util/inputUtils.cpp"

int solve(std::vector<int>& nums, uint window_size) {
    int prev{std::numeric_limits<int>::max()};
    int res{};
    for (size_t i = 0; i < nums.size() - window_size + 1; i++) {
        int current{};
        for (size_t j = 0; j < window_size; j++) current += nums[i + j];
        res += current > prev;
        prev = current;
    }
    return res;
}

int main() {
    std::vector<std::string> lines = readInput("input.txt");
    std::vector<int> numbers;

    for (std::string line : lines) numbers.push_back(std::stoi(line));

    std::cout << "Part 1: " << solve(numbers, 1) << std::endl;
    std::cout << "Part 2: " << solve(numbers, 3) << std::endl;

    return 0;
}
