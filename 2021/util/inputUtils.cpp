#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

std::vector<std::string> readInput(const std::string &path);
std::vector<std::string> split(const std::string &str, const std::string &sep = " ");
std::vector<std::string> split(const std::vector<std::string> &vec, const std::string &sep = " ");
std::vector<int> getIntsFromString(const std::string &str, const std::string &sep = " ");
std::vector<std::string> charSplit(const std::string &str);

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
 * Given a string and a sep_string, return the list of strings that are separated by the sep_string
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
 * Given a list of strings, and a sep_string, return a single list of strings separated by sep_string
 */
std::vector<std::string> split(const std::vector<std::string> &vec, const std::string &sep) {
    std::vector<std::string> tokens{};
    for (const auto &str : vec) {
        auto s = split(str, sep);
        tokens.insert(tokens.end(), s.begin(), s.end());
    }
    return tokens;
}

/*
 * Given a string and a separator, perform split(string, separator) and then return only the tokens
 * that are interpretable as integers
 */
std::vector<int> getIntsFromString(const std::string &str, const std::string &sep) {
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
    for (auto &c : str)
        vec.push_back(std::string{c});
    return vec;
}

template <typename T>
size_t indexOf(const std::vector<T> &vec, const T& elem) {
    return vec.size() - (vec.end() - std::ranges::find(vec, elem));
}

template <typename T>
void printVec(const T &vec) {
    for (auto &el : vec)
        std::cout << el << ", ";
    std::cout << std::endl;
}

template <typename T>
int sgn(T val) {
    return (T(0) < val) - (val < T(0));
}