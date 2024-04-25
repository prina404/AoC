#include <algorithm>
#include <unordered_map>
#include <unordered_set>

#include "../util/inputUtils.cpp"

using namespace std;

unordered_set<char> strToSet(const string& str) {
    unordered_set<char> res{};
    for (const auto& c : str)
        res.insert(c);
    return res;
}

string inverseMapLookup(const unordered_map<string, int>& strMap, int num) {
    for (auto& [k, v] : strMap)
        if (v == num)
            return k;
}

bool mapContainsSectors(const auto& strMap, const string& word, int numToCheck) {
    unordered_set<char> s1 = strToSet(word);
    unordered_set<char> s2 = strToSet(inverseMapLookup(strMap, numToCheck));
    return includes(s1.begin(), s1.end(), s2.begin(), s2.end());
}

int deduceDigit(const unordered_map<string, int>& strMap, const string& word) {
    vector<char> intersect{};
    unordered_set<char> s1 = strToSet(inverseMapLookup(strMap, 4));
    unordered_set<char> s2 = strToSet(word);

    switch (word.size()) {
        case 5:
            if (mapContainsSectors(strMap, word, 7))
                return 3;
            set_intersection(s1.begin(), s1.end(), s2.begin(), s2.end(), back_inserter(intersect));
            if (intersect.size() == 3)
                return 5;
            return 2;
        case 6:
            if (mapContainsSectors(strMap, word, 4))
                return 9;
            if (mapContainsSectors(strMap, word, 7))
                return 0;
            return 6;
    }
}

int part2(const auto& lines) {
    unordered_map<int, int> digits{{2, 1}, {4, 4}, {3, 7}, {7, 8}};  // map[len -> digit]
    vector<string> output{};
    uint result{};
    for (auto& ln : lines) {
        auto wordVec = split(ln, " | ");
        auto lhs = split(wordVec[0]);  // left  of |
        auto rhs = split(wordVec[1]);  // right of |
        // sort strings by len
        sort(lhs.begin(), lhs.end(), [](string& first, string& second) { return first.size() < second.size(); });

        unordered_map<string, int> strMap{};
        for (string& word : lhs) {
            if (digits.contains(word.length()))
                strMap[word] = digits[word.length()];
            if (word.length() == 5 || word.length() == 6)
                strMap[word] = deduceDigit(strMap, word);
        }
        string finalNum{};
        for (string& word : rhs) 
            for (const auto& [k, v] : strMap) 
                if (strToSet(word) == strToSet(k)) 
                    finalNum += to_string(v);
        result += stoi(finalNum);
    }
    return result;
}

int part1(const auto& lines) {
    unordered_map<int, int> digits{{2, 1}, {4, 4}, {3, 7}, {7, 8}};  // map[len -> digit]
    vector<string> output{};
    for (auto& ln : lines) {
        auto vec = split(split(ln, " | ")[1], " ");
        output.insert(output.end(), vec.begin(), vec.end());
    }

    int count = 0;
    for (auto& str : output)
        if (digits.contains(str.length()))
            count++;
    return count;
}

int main() {
    auto lines = readInput("input.txt");

    cout << "Part1: " << part1(lines) << endl;
    cout << "Part2: " << part2(lines) << endl;
}