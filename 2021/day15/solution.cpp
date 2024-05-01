#include <map>
#include <queue>

#include "../util/inputUtils.cpp"

using namespace std;

struct State {
    Coord coord;
    uint risk;
};

uint solve(auto& mat) {
    Coord goal{mat.size() - 1, mat[0].size() - 1};

    vector<uint> distances(mat.size() * mat.size(), 10000);

    auto cmp = [](State& a, State& b) { return a.risk > b.risk; };
    priority_queue<State, vector<State>, decltype(cmp)> toVisit(cmp);
    toVisit.push(State{Coord{0, 0}, 0});

    while (toVisit.size() > 0) {
        auto [coord, risk] = toVisit.top();
        toVisit.pop();
        for (const Coord& nb : conn4Neighbors(coord, goal)) {
            size_t nbIdx = nb.y * mat.size() + nb.x;
            uint distToNbr = risk + mat[nb.y][nb.x];
            if (distances[nbIdx] <= distToNbr) continue;
            distances[nbIdx] = distToNbr;
            toVisit.push(State{nb, distToNbr});
        }
    }
    return distances.back();
}

vector<vector<int>> enlargeMat(auto& mat) {
    vector<vector<int>> res{};
    for (int i = 0; i < 5; i++) {  // adding rows
        for (auto& row : mat) {
            vector<int> newRow{};
            for (auto& n : row)
                newRow.push_back((n + i > 9) ? (n + i) % 10 + 1 : n + i);
            res.push_back(newRow);
        }
    }
    for (size_t i = 0; i < res.size(); i++) {  // adding columns
        for (size_t j = 1; j < 5; j++) {
            for (size_t k = 0; k < mat[0].size(); k++) {
                int n = res[i][k];
                res[i].push_back((n + j > 9) ? (n + j) % 10 + 1 : n + j);
            }
        }
    }
    return res;
}

int main() {
    auto lines = readInput("input.txt");
    vector<vector<int>> mat{};
    for (auto& row : lines) mat.push_back(getIntsFromString(row, ""));

    cout << "Part 1: " << solve(mat) << endl;
    mat = enlargeMat(mat);
    cout << "Part 2: " << solve(mat) << endl;
}