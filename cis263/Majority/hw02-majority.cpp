#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>

using namespace std;

/**
 * The following recursive function returns the majority element in a vector of integers
 * @param a input vector consisting of positive integers
 * @return the majority element or -1 if none is found
 */

int majority (vector<int> a)
{
    /* write your code here, based on the detailed explanation of exercise 2.26 */
    // Using clog for debugging output
    clog << "Input vector is: ";
    for (auto& x : a)
        clog << x << " ";
    clog << endl;

    int potentialCandidate = 0;
    int chosenCandidate = -1;
    double nDividedByTwo = (double) a.size() / 2.0;
    vector<int> b;

    if (a.size() == 1) {
        return a[0];
    }

    if (a.size() == 0) {
        return -1;
    }

    for (int h = 0; h < a.size(); h++) {
        potentialCandidate = a[h];
        int candidateCounter = 0;
        for (int g = 0; g < a.size(); g++) {

            if (a[h] == a[g]) {
                candidateCounter++;
            }

            if (candidateCounter >= nDividedByTwo) {
                chosenCandidate = potentialCandidate;

            }
        }
    }

    if ((a.size() % 2 == 0) && (a.size() > 1)) {
        for (int x = 0; x < nDividedByTwo; x++) {
            if (a[2 * x] == a[(2 * x) + 1] == chosenCandidate) {
                b.push_back(a[2 * x]);
            }
        }
    }
    else if ((a.size() % 2 == 1) && (a.size() > 1)) {
        for (int z = 0; z < nDividedByTwo; z++) {
            if ((2 * z) + 1 == a.size()) {
                if (a[a.size() - 1] == a[a.size() - 1] == chosenCandidate) {
                    b.push_back(a[a.size() - 1]);
                }
            }
            else {
                if (a[2 * z] == a[(2 * z) + 1]) {
                    b.push_back(a[2 * z]);
                }
            }
        }


    }
    if (b.size() == 1) {
        return b[0];
    }
    else {
        majority(b);
    }
    return -1;  // replace this
}

int main(int argc, char* argv[]) {
    //Use clog for your debugging output
    clog << "This program runs from " << argv[0] << endl;
    if (argc < 2) {
        cerr << "Missing filename argument" << endl;
        exit (0);
    }

    /* The first command line argument is the input filename */
    ifstream inputfile {argv[1]};   /* input file stream */
    if (inputfile.good()) {
        int num_test, num_items_per_test;
        vector<int> testVector;

        inputfile >> num_test;  // read the number of test cases

        //Use clog for your debugging
        clog << "Number of test cases: " << num_test << endl;
        for (int k = 0; k < num_test; k++) {
            inputfile >> num_items_per_test;
            clog << "Test " << k << " has " << num_test << " data items" << endl;
            testVector.clear();
            for (int m = 0; m < num_items_per_test; m++) {
                int val;
                inputfile >> val;
                testVector.push_back(val);
            }
            // Use cout for the ACTUAL output
            cout << "Majority: " << majority(testVector) << endl;
        }
    } else {
        cerr << "Can't open data file " << argv[1] << endl;
    }
    return 0;
}