#include <unordered_map>
#include <unordered_set>

#include "../util/inputUtils.cpp"

using namespace std;

bool canReVisit(vector<string>& path, const string& next) {
    if (!isStrLower(next)) return true;
    if (next == "start") return false;
    unordered_map<string, int> counts{};
    for (string& node : path)
        if (isStrLower(node)) counts[node] += 1;

    for (string& node : path)
        if (counts[node] > 1 && counts.contains(next)) return false;
    return true;
}

//TODO: should use trees to improve perf
int solve(auto& edges, bool part1 = true) {
    vector<string> startPath = {"start"};
    vector<vector<string>> toComplete{startPath};
    int completePaths{};

    while (toComplete.size() > 0) {
        auto current = toComplete.back();
        toComplete.pop_back();
        for (auto& nbr : edges[current.back()]) {
            if (nbr == "end"){
                completePaths++;
                continue;
            }
            if (part1 && isStrLower(nbr) && contains(current, nbr)) continue;
            if (!canReVisit(current, nbr)) continue;
            auto newPath{current};  
            newPath.push_back(nbr);
            toComplete.push_back(newPath);
        }
    }
    return completePaths;
}

int main() {
    auto lines = readInput("input.txt");
    unordered_map<string, unordered_set<string>> edges{};
    for (const string& ln : lines) {
        auto edge = split(ln, "-");
        edges[edge[0]].insert(edge[1]);
        edges[edge[1]].insert(edge[0]);
    }

    cout << "Part 1:" << solve(edges) << endl;
    cout << "Part 2:" << solve(edges, false) << endl;
}
