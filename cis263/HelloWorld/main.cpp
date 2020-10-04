#include <iostream>

using namespace std;

int main() {
    string input;
    string result;
    input = "Shane-The-Pain";

    for (int c = 0; c < input.size(); c++) {
        if (isupper(input[c])) {
            result.push_back(input[c]);
        }
    }
    cout << result << endl;
}