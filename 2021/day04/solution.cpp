#include <bits/stdc++.h>

#include "../util/inputUtils.cpp"
using namespace std;

class Bingo {
   public:
    Bingo(const vector<string> &lines) {
        for (auto &line : lines)
            board.push_back(getIntsFromString(line));
    }

    bool isWinning(const unordered_set<int> &numbers) {
        for (size_t i = 0; i < ncols; i++) {
            uint rowCount = 0;
            uint colCount = 0;
            for (size_t j = 0; j < ncols; j++) {
                rowCount += numbers.contains(board[i][j]);
                colCount += numbers.contains(board[j][i]);
            }
            if (rowCount == 5 || colCount == 5)
                return true;
        }
        return false;
    }

    int getScore(const unordered_set<int> &numbers, int lastNumber) {
        uint score = 0;
        for (auto &row : board) {
            for (int num : row)
                if (!numbers.contains(num))
                    score += num;
        }
        return score * lastNumber;
    }

   private:
    uint ncols{5};
    vector<vector<int>> board{};
};

int part2(const vector<int> &numVec, vector<Bingo>& boards) {
    unordered_set<int> numbers{};
    for (int num : numVec) {
        numbers.insert(num);
        if (size(boards) == 1 && boards[0].isWinning(numbers))
            return boards[0].getScore(numbers, num);

        for (size_t i = 0; i < size(boards); i++)
            if (boards[i].isWinning(numbers))
                boards.erase(boards.begin() + i);
    }

    return -1;
}

int part1(const vector<int> &numVec, vector<Bingo>& boards) {
    unordered_set<int> numbers{};
    for (int num : numVec) {
        numbers.insert(num);
        for (Bingo& b : boards)
            if (b.isWinning(numbers))
                return b.getScore(numbers, num);
    }

    return -1;
}

int main() {
    vector<string> lines = readInput("input.txt");
    vector<int> numVec = getIntsFromString(lines[0], ",");

    vector<Bingo> boards{};
    for (size_t i = 2; i < size(lines); i += 6) {
        const auto &offset = lines.begin() + i;
        boards.push_back(Bingo({offset, offset + 5}));
    }


    cout << "Part 1: " << part1(numVec, boards) << endl;
    cout << "Part 2: " << part2(numVec, boards) << endl;

    return 0;
}