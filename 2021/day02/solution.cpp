#include <fstream>
#include <iostream>
#include <vector>
#include "../util/inputUtils.cpp"

int solve(const std::vector<std::string>& lines, bool part2) {
    int pos{};
    int depth{};
    int aim{};
    for (auto& line : lines) {
        std::string command = line.substr(0, line.find(" "));
        int value = std::stoi(std::string{line.back()});
        if (command == "forward") {
            pos += value;
            depth += (part2) ? aim * value : 0;
        } else {
            if (part2)
                aim += (command == "up") ? -value : value;
            else
                depth += (command == "up") ? -value : value;
        }
    }
    return pos * depth;
}

int main() {
    std::vector<std::string> lines = readInput("input.txt");

    std::cout << "Part 1: " << solve(lines, false) << std::endl;
    std::cout << "Part 2: " << solve(lines, true) << std::endl;

    return 0;
}
