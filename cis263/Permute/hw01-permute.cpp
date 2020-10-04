#include <iostream>
#include <sstream>
#include <string>
using namespace std;
void perm_helper (const string& input, int start)
{
string sample = input;
int position = start;
char tee = sample[position];
int L = sample.size();
int M = 0;

string part = sample.erase(position, L - (L - 1));
    //cout << part << endl;
for (int x = 0; x < L - 1; x++) {

    cout << tee << part << endl;
    if (L == 4) {
        string firstLetter = part.substr(0, 1);
        string secondLetter = part.substr(1, 1);
        string thirdLetter = part.substr(2, 1);
        string swapLetter;
        string swapLetter2;
        swapLetter = secondLetter;
        secondLetter = firstLetter;
        swapLetter2 = thirdLetter;
        thirdLetter = swapLetter;
        firstLetter = swapLetter2;

        part = firstLetter + secondLetter + thirdLetter;
    }
    else {
        string firstLetter = part.substr(0, 1);
        string secondLetter = part.substr(1, 1);
        string swapLetter;
        swapLetter = firstLetter;
        firstLetter = secondLetter;
        secondLetter = swapLetter;
        part = firstLetter + secondLetter;
    }
}

string nextString = tee + part;

if (position <= L - 2) {
    perm_helper(nextString, position + 1);
}
}
void permute (const string& input) {

    perm_helper (input, 0);
}
int main () {
    permute ("STOP"); /* prints 720 lines of different strings */
    return 0;
}