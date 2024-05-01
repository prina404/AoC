#include <algorithm>
#include <fstream>
#include <iostream>
#include <optional>
#include <string>
#include <vector>

std::vector<std::string> readInput(const std::string &path);
std::vector<std::string> split(const std::string &str,
                               const std::string &sep = " ");
std::vector<std::string> split(const std::vector<std::string> &vec,
                               const std::string &sep = " ");
std::vector<int> getIntsFromString(const std::string &str,
                                   const std::string &sep = " ");
std::vector<std::string> charSplit(const std::string &str);
bool isStrLower(const std::string &str);

struct Coord {
    size_t x;
    size_t y;
    auto operator<=>(const Coord &) const = default;
    bool operator==(const Coord &rhs) const { return x == rhs.x && y == rhs.y; }
    friend std::ostream &operator<<(std::ostream &os, const Coord &c) {
        os << "(" << c.x << ", " << c.y << ")";
        return os;
    }
};

template <>
struct std::hash<Coord> {
    std::size_t operator()(const Coord &k) const {
        using std::hash;
        using std::size_t;
        return ((hash<size_t>()(k.x) ^ (hash<size_t>()(k.y) << 1)));
    }
};


std::vector<std::string> readInput(const std::string &path) {
    std::vector<std::string> inputList{};
    std::fstream inputStream{path};
    inputStream.exceptions(std::fstream::badbit);
    try {
        std::string line;
        while (std::getline(inputStream, line)) inputList.push_back(line);

        inputStream.close();
    } catch (std::fstream::failure &e) {
        std::cout << "Failed to read file: " << path
                  << "\n Exception: " << e.what() << std::endl;
    }
    return inputList;
}

/*
 * Given a string and a sep_string, return the list of strings that are
 * separated by the sep_string
 */
std::vector<std::string> split(const std::string &str, const std::string &sep) {
    std::vector<std::string> tokens{};
    size_t last = 0;
    size_t next = 0;
    while ((next = str.find(sep, last)) != std::string::npos) {
        std::string substr = str.substr(last, next - last);
        if (substr != "") tokens.push_back(substr);
        last = next + sep.length();
    }
    tokens.push_back(str.substr(last));
    return tokens;
}

/*
 * Given a list of strings, and a sep_string, return a single list of strings
 * separated by sep_string
 */
std::vector<std::string> split(const std::vector<std::string> &vec,
                               const std::string &sep) {
    std::vector<std::string> tokens{};
    for (const auto &str : vec) {
        auto s = split(str, sep);
        tokens.insert(tokens.end(), s.begin(), s.end());
    }
    return tokens;
}

/*
 * Given a string and a separator, perform split(string, separator) and then
 * return only the tokens that are interpretable as integers
 */
std::vector<int> getIntsFromString(const std::string &str,
                                   const std::string &sep) {
    std::vector<std::string> tokens;
    if (sep == "")
        tokens = charSplit(str);
    else
        tokens = split(str, sep);

    std::vector<int> result{};
    for (auto &token : tokens) {
        try {
            result.push_back(std::stoi(token));
        } catch (std::exception &e) {
        }
    }
    return result;
}

std::vector<std::string> charSplit(const std::string &str) {
    std::vector<std::string> vec{};
    for (auto &c : str) vec.push_back(std::string{c});
    return vec;
}

bool isStrLower(const std::string &str) {
    for (const char &c : str)
        if (std::isupper(c)) return false;
    return true;
}

template <typename T>
size_t contains(const std::vector<T> &vec, const T &elem) {
    return std::ranges::find(vec, elem) != vec.end();
}

template <typename T>
size_t indexOf(const std::vector<T> &vec, const T &elem) {
    return vec.size() - (vec.end() - std::ranges::find(vec, elem));
}

template <typename T>
void printVec(const T &vec) {
    for (const auto &el : vec) std::cout << el << ", ";
    std::cout << std::endl;
}

template <typename T>
void printMat(const std::vector<std::vector<T>> &mat) {
    for (const auto &row : mat) printVec(row);
}

template <typename T>
int sgn(T val) {
    return (T(0) < val) - (val < T(0));
}

std::vector<Coord> conn4Neighbors(
    Coord c, std::optional<Coord> maxCoord = std::nullopt) {
    if (!maxCoord)
        return {{c.x + 1, c.y}, {c.x - 1, c.y}, {c.x, c.y + 1}, {c.x, c.y - 1}};
    std::vector<Coord> res{};
    if (c.x < maxCoord.value().x) res.push_back({c.x + 1, c.y});
    if (c.y < maxCoord.value().y) res.push_back({c.x, c.y + 1});
    if (c.x > 0) res.push_back({c.x - 1, c.y});
    if (c.y > 0) res.push_back({c.x, c.y - 1});
    return res;
}

std::vector<Coord> conn8Neighbors(
    Coord c, std::optional<Coord> maxCoord = std::nullopt) {
    std::vector<Coord> res = conn4Neighbors(c, maxCoord);
    if (!maxCoord) {
        std::vector<Coord> v2{{c.x + 1, c.y + 1},
                              {c.x - 1, c.y + 1},
                              {c.x + 1, c.y - 1},
                              {c.x - 1, c.y - 1}};
        res.insert(res.end(), v2.begin(), v2.end());
        return res;
    }
    if (c.x < maxCoord.value().x && c.y < maxCoord.value().y)
        res.push_back({c.x + 1, c.y + 1});
    if (c.x < maxCoord.value().x && c.y > 0) res.push_back({c.x + 1, c.y - 1});
    if (c.x > 0 && c.y < maxCoord.value().y) res.push_back({c.x - 1, c.y + 1});
    if (c.x > 0 && c.y > 0) res.push_back({c.x - 1, c.y - 1});
    return res;
}
