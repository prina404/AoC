#include <algorithm>
#include <fstream>
#include <iostream>
#include <vector>
#include <bitset>
#include "../util/inputUtils.cpp"

int oneCount(const std::vector<std::string> &vec, int position)
{
    int sum{};
    for (auto &v : vec)
        sum += v[position] == '1' ? 1 : -1;
    return sum;
}

void filterLines(std::vector<std::string> &lines, std::string prefix)
{
    lines.erase(std::remove_if(lines.begin(), lines.end(),
                               [prefix](std::string s)
                               {
                                   return !(s.starts_with(prefix));
                               }),
                lines.end());
}

std::string reduceVector(std::vector<std::string> lines, bool reverseBits)
{
    std::string prefix{};
    for (size_t j = 0; j < lines[0].length(); j++)
    {
        char first{(reverseBits) ? '0' : '1'};
        char second{(reverseBits) ? '1' : '0'};
        prefix += (oneCount(lines, j) >= 0) ? first : second;
        filterLines(lines, prefix);
        if (lines.size() == 1)
            return lines[0];
    }
    return NULL;
}

int part2(const std::vector<std::string> &lines)
{
    std::string oxyStr{reduceVector(lines, false)};
    std::string CO2Str{reduceVector(lines, true)};
    return std::stoi(oxyStr, 0, 2) * std::stoi(CO2Str, 0, 2);
}

int part1(const std::vector<std::string> &lines)
{
    uint gamma{};
    uint epsilon{};
    for (size_t j = 0; j < lines[0].length(); j++)
    {
        gamma <<= 1;
        epsilon <<= 1;
        if (oneCount(lines, j) > 0)
            gamma++;
        else
            epsilon++;
    }
    return gamma * epsilon;
}

int main()
{
    std::vector<std::string> lines = readInput("input.txt");

    std::cout << "Part 1: " << part1(lines) << std::endl;
    std::cout << "Part 2: " << part2(lines) << std::endl;

    return 0;
}