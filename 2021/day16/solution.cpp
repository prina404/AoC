#include <numeric>
#include <queue>

#include "../util/inputUtils.cpp"

using namespace std;

size_t readNbits(queue<bool>* q, size_t n) {
    size_t res{};
    for (size_t i = 0; i < n; i++) {
        res <<= 1;
        res += q->front();
        q->pop();
    }
    return res;
}

size_t parseLiteral(queue<bool>* bitStr) {
    size_t num{};
    while (true) {
        bool isLast = readNbits(bitStr, 1) == 0;
        num <<= 4;
        num += readNbits(bitStr, 4);
        if (isLast) return num;
    }
}

queue<bool> subseq(queue<bool>* bitStr, size_t len) {
    queue<bool> res{};
    for (size_t i = 0; i < len; i++) {
        res.push(bitStr->front());
        bitStr->pop();
    }
    return res;
}

queue<bool> strToBit(string& str) {
    queue<bool> res{};
    for (auto& ch : str) {
        long n = strtol(string{ch}.c_str(), NULL, 16);
        for (int i = 0; i < 4; i++) res.push((n & (8 >> i)));
    }
    return res;
}

class Packet {
   public:
    size_t version;
    size_t typeID;
    bool isLiteral;
    size_t value{};
    vector<Packet> subpackets{};

    Packet(queue<bool>* bitStr) {
        version = readNbits(bitStr, 3);
        typeID = readNbits(bitStr, 3);
        isLiteral = typeID == 4;

        if (isLiteral) {
            value = parseLiteral(bitStr);
            return;
        }

        if (readNbits(bitStr, 1)) {  // length typeID == 1
            size_t numPackets = readNbits(bitStr, 11);
            for (size_t i = 0; i < numPackets; i++)
                subpackets.push_back(Packet(bitStr));
        } else {
            size_t subStrLen = readNbits(bitStr, 15);
            queue<bool> subSequence = subseq(bitStr, subStrLen);
            while (subSequence.size() > 0)
                subpackets.push_back(Packet(&subSequence));
        }
    }

    size_t getVersionSum() {
        size_t res = version;
        for (auto& p : subpackets)
            res += p.getVersionSum();
        return res;
    }

    size_t getValue() {
        size_t res{};
        auto lt = [](auto& a, auto& b) { return a.getValue() < b.getValue(); };
        auto eq = [](auto& a, auto& b) { return a.getValue() == b.getValue(); };
        switch (typeID) {
            case 0:  // sum
                for (auto& p : subpackets) res += p.getValue();
                return res;
            case 1:  // mul
                res = 1;
                for (auto& p : subpackets) res *= p.getValue();
                return res;
            case 2:  // min
                return min_element(subpackets.begin(), subpackets.end(), lt)->getValue();
            case 3:  // max
                return max_element(subpackets.begin(), subpackets.end(), lt)->getValue();
            case 5:  // gt
                return !lt(subpackets[0], subpackets[1]) && !eq(subpackets[0], subpackets[1]);
            case 6:  // lt
                return lt(subpackets[0], subpackets[1]);
            case 7:  // eq
                return eq(subpackets[0], subpackets[1]);
        }
        return value;
    }
};

int main() {
    auto lines = readInput("input.txt");
    queue<bool> bits = strToBit(lines[0]);
    Packet p = Packet(&bits);
    cout << "Part 1: " << p.getVersionSum() << endl;
    cout << "Part 2: " << p.getValue() << endl;
}