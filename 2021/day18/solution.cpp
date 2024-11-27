#include <iterator>
#include <list>

#include "../util/inputUtils.cpp"

using namespace std;

bool isNum(const string& str) {
    return str != "[" && str != "]" && str != ",";
}
string sumStr(const string& a, const string& b) {
    return to_string(stoull(a) + stoull(b));
}

list<string> getStringVec(const string& line) {
    list<string> res;
    for (auto c : line)
        res.push_back(string{c});
    return res;
}

bool explode(list<string>& lst) {  // horrible to read but a quite easy implementation
    auto it = lst.begin();
    size_t depth{};
    while (it != lst.end()) {
        if (*it == "[")
            depth++;
        else if (*it == "]")
            depth--;

        if (++it == lst.end() || !isNum(*it) || depth < 5)
            continue;
        // *it = leftmost number to explode
        it = lst.erase(--it);  // left parenthesis
        auto lhs = it;
        string lhsNum = *lhs;
        while (lhs-- != lst.begin()) {  // update first number on the left
            if (isNum(*lhs)) {
                *lhs = sumStr(lhsNum, *lhs);
                break;
            }
        }
        it = lst.erase(it);  // erase lhs
        it = lst.erase(it);  // comma
        auto rhs = it;
        string rhsNum = *rhs;
        while (++rhs != lst.end()) {  // update first number on the right
            if (isNum(*rhs)) {
                *rhs = sumStr(rhsNum, *rhs);
                break;
            }
        }
        *it = "0";
        it = lst.erase(++it);  // right parenthesis
        return true;
    }
    return false;  // no explodes have been performed
}

bool splitNum(list<string>& lst) {
    auto it = lst.begin();
    while (++it != lst.end()) {
        if (!isNum(*it) || atoi((*it).c_str()) < 10)
            continue;

        int num = atoi((*it).c_str());
        int lhs = num / 2;
        int rhs = (num + 1) / 2;
        *it = "[";
        it = lst.insert(++it, to_string(lhs));
        it = lst.insert(++it, ",");
        it = lst.insert(++it, to_string(rhs));
        lst.insert(++it, "]");
        return true;
    }
    return false;
}

void reduceNum(list<string>& lst) {
    while (true) {
        while (explode(lst));
        size_t lstSize = lst.size();
        splitNum(lst);
        if (lstSize == lst.size())
            return;
    }
}

size_t magnitude(list<string>& lst) {
    string first = *lst.begin();
    lst.pop_front();
    if (isNum(first))
        return stoull(first.c_str());
    if (first == "[") {
        size_t lhs = magnitude(lst);
        lst.pop_front();  // remove comma;
        size_t rhs = magnitude(lst);
        lst.pop_front();  // remove "]"
        return 3 * lhs + 2 * rhs;
    }
    return 0;
}

void snailAddition(list<string>& Num, string& snd) {
    auto lst = getStringVec(snd);
    if (Num.size() == 0)
        Num.assign(lst.begin(), lst.end());
    else {
        Num.push_front("[");
        Num.push_back(",");
        Num.insert(Num.end(), lst.begin(), lst.end());
        Num.push_back("]");
    }
}

int main() {
    auto lines = readInput("input.txt");
    list<string> snailNum{};
    for (auto& ln : lines) {
        snailAddition(snailNum, ln);
        reduceNum(snailNum);
    }
    cout << "Part 1: " << magnitude(snailNum) << endl;

    size_t maxSum = 0;
    for (size_t i = 0; i < lines.size() - 1; i++) {
        for (size_t j = i + 1; j < lines.size(); j++) {
            snailNum.clear();
            snailAddition(snailNum, lines[i]);
            snailAddition(snailNum, lines[j]);
            reduceNum(snailNum);
            maxSum = max(maxSum, magnitude(snailNum));
            // I should check for the inverse op: lines[j] + lines[i] but on my input
            // the best pair occurs in natural order
        }
    }
    cout << "Part 2: " << maxSum << endl;
}