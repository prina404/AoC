#include <ranges>
#include <unordered_set>

#include "../util/inputUtils.cpp"

using namespace std;

struct Fold {
    bool isHorizontal;
    size_t planeHeight;
};

void makeFold(unordered_set<Coord>& coords, Fold& fold) {
    auto setCopy = coords;
    for (auto& c : setCopy) {
        if (fold.isHorizontal && c.y > fold.planeHeight) {
            coords.insert({c.x, (size_t)2 * fold.planeHeight - c.y});
            coords.erase(c);
        }
        if (!fold.isHorizontal && c.x > fold.planeHeight) {
            coords.insert({2 * fold.planeHeight - c.x, c.y});
            coords.erase(c);
        }
    }
}

void printRes(auto& coords) {
    for (size_t i = 0; i < 6; ++i) {
        for (size_t j = 0; j < 45; ++j) {
            if (coords.contains(Coord{j, i}))
                cout << "#";
            else
                cout << ".";
        }
        cout << endl;
    }
}

int solve(vector<string>& lines, bool part1) {
    unordered_set<Coord> coords{};
    vector<Fold> folds{};
    auto it = lines.begin();
    for (; *it != ""; it++) {
        auto point = getIntsFromString(*it, ",");
        coords.insert({(size_t)point[0], (size_t)point[1]});
    }
    for (++it; it != lines.end(); it++) {
        string fold = split(*it, " ")[2];
        size_t foldH = getIntsFromString(fold, "=")[0];
        folds.push_back({fold[0] == 'y', foldH});
    }

    for (auto& f : folds) {
        if (part1) {
            makeFold(coords, folds[0]);
            return coords.size();
        }
        makeFold(coords, f);
    }
    printRes(coords);
    return 0;
}

int main() {
    auto lines = readInput("input.txt");

    cout << "Part 1: " << solve(lines, true) << endl;
    cout << "Part 2: \n" << solve(lines, false) << endl;
}