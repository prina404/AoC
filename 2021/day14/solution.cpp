#include <map>
#include <memory>
#include <numeric>

#include "../util/inputUtils.cpp"

using namespace std;

size_t solve(map<string, char>& mapping, const string& initialSeq, int iters) {
    map<string, size_t> couples{};
    for (size_t i = 0; i < initialSeq.size() - 1; i++) {
        string cp{initialSeq[i], initialSeq[i + 1]};
        couples[cp]++;
    }

    for (int i = 0; i < iters; i++) {
        map<string, size_t> couplesCopy{couples};
        for (auto& [couple, occurrencies] : couplesCopy) {
            string lhsIncrement{couple[0], mapping[couple]};
            string rhsIncrement{mapping[couple], couple[1]};
            couples[couple] -= occurrencies;
            couples[lhsIncrement] += occurrencies;
            couples[rhsIncrement] += occurrencies;
        }
        //couples = newCouples;
    }

    map<char, size_t> result{};
    result[initialSeq[0]] = 1;
    result[initialSeq[initialSeq.size() - 1]] = 1;
    for (auto& [k, v] : couples) {
        result[k[0]] += v;
        result[k[1]] += v;
    }

    vector<long> scores{};
    for (auto& [k, v] : result) scores.push_back(v);

    return (ranges::max(scores) - ranges::min(scores)) / 2;
}

int main() {
    auto lines = readInput("input.txt");
    map<string, char> mappings{};
    string initialConfig = lines[0];
    for (size_t i = 2; i < lines.size(); i++) {
        auto ln = split(lines[i], " -> ");
        mappings[ln[0]] = ln[1][0];
    }

    cout << "Part 1: " << solve(mappings, initialConfig, 10) << endl;
    cout << "Part 2: " << solve(mappings, initialConfig, 40) << endl;
}