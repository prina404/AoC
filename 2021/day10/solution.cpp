#include <map>
#include <numeric>

#include "../util/inputUtils.cpp"

using namespace std;

map<char, char> pairs{
    {'(', ')'},
    {'[', ']'},
    {'<', '>'},
    {'{', '}'},
};
map<char, size_t> score{
    {')', 3},
    {']', 57},
    {'}', 1197},
    {'>', 25137},
};

size_t scoreIncompleteLines(const vector<char>& stack) {
    size_t res = 0;
    vector<char> symbols{')', ']', '}', '>'};
    for (int i = stack.size(); i > 0; i--)
        res = (res * 5) + indexOf(symbols, pairs[stack[i - 1]]) + 1;
    return res;
}

size_t getLineScore(const string& line, bool part1 = true) {
    vector<char> contextStack{};
    for (char c : line) {
        if (pairs.contains(c)) {  // opening parenthesis
            contextStack.push_back(c);
            continue;
        }
        // closing parenthesis
        if (pairs[contextStack.back()] != c) {  // wrong closing parenthesis
            return score[c] * part1;
        }
        contextStack.pop_back();  // pop the stack
    }
    if (part1 || contextStack.size() == 0)
        return 0;
    return scoreIncompleteLines(contextStack);
}

int solve(const auto& lines, bool part1 = true) {
    vector<size_t> result{};
    for (auto& ln : lines) {
        size_t score = getLineScore(ln, part1);
        if (score)
            result.push_back(score);
    }
    if (part1)
        return reduce(result.begin(), result.end());

    ranges::sort(result);
    return result[result.size() / 2];
}

int main() {
    vector<string> lines {readInput("input.txt")};
    cout << "Part 1: " << solve(lines) << endl;
    cout << "Part 2: " << solve(lines, false) << endl;
}