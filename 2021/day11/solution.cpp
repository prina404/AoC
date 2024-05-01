#include <cmath>
#include <functional>
#include <unordered_set>

#include "../util/inputUtils.cpp"

#define SIDE 10

using namespace std;

class Octopus {
   public:
    Coord pos;
    size_t energyLevel;
    bool hasFlashed = false;
};

int scoreMatrix(vector<vector<Octopus>>& mat){
    size_t flashes{};
    for (uint i = 0; i < SIDE; i++) {
        for (uint j = 0; j < SIDE; j++) {
            flashes += mat[i][j].hasFlashed;
            mat[i][j].hasFlashed = false;
        }
    }
    return flashes;
}

int iteration(vector<vector<Octopus>>& mat) {
    vector<Octopus*> flashingOctopi{};
    // increment each individual's energy level
    for (uint i = 0; i < SIDE; i++) 
        for (uint j = 0; j < SIDE; j++)
            if (++mat[i][j].energyLevel > 9)
                flashingOctopi.push_back(&mat[i][j]);
    // handle energy transfers between octopi
    while (flashingOctopi.size() > 0) {
        Octopus* current = flashingOctopi.back();
        flashingOctopi.pop_back();
        if (current->hasFlashed) continue;

        current->energyLevel = 0;
        current->hasFlashed = true;
        for (auto& pos :
             conn8Neighbors(current->pos, Coord{SIDE - 1, SIDE - 1})) {
            Octopus& nbr = mat[pos.x][pos.y];
            if (!nbr.hasFlashed) nbr.energyLevel++;
            if (nbr.energyLevel > 9) flashingOctopi.push_back(&nbr);
        }
    }
    return scoreMatrix(mat);
}

int solve(auto& lines, bool part1 = true) {
    vector<vector<Octopus>> mat(SIDE, vector<Octopus>{});
    for (uint i = 0; i < SIDE; i++)
        for (uint j = 0; j < SIDE; j++)
            mat[i].push_back(Octopus({i, j}, (size_t)(lines[i][j] - '0')));

    int flashes{}, i{};
    while (true) {
        int tempScore = iteration(mat);
        flashes += tempScore;

        if (++i == 100 && part1) return flashes;
        if (tempScore == SIDE * SIDE) return i;
    }
    return flashes;
}

int main() {
    auto lines = readInput("input.txt");

    cout << "Part 1: " << solve(lines) << endl;
    cout << "Part 2: " << solve(lines, false) << endl;
};
