#include <iostream>

using namespace std;

class DecToBin {
public:
    void printNumber(uint8_t val) {
        if (val > 0) {
            printNumber(val - 1);
            string s = to_string(DecToBin::decToBin(val));
            int size = s.length();
            if (size >= 2)
                cout << "Decimal: " << to_string(val) << " Binary: " << s << " Second LSB Bit: " << *(s.end() - 2)
                     << '\n';
            else
                cout << "Decimal: " << to_string(val) << " Binary: " << s << " Second LSB Bit: " << (s)
                     << '\n';
        }
        return;
    }

    int decToBin(uint8_t val) {
        if (val != 0)
            return val % 2 + 10 * decToBin(val / 2);
        else
            return 0;
    }
};

int main() {
    DecToBin g;
    g.printNumber(200);
    return 0;
}




