#include <vector>
#include <iostream>
#include <fstream>

using namespace std;

/**
 * This function returns the maximum a[j]+a[i] of a pair elements (as described in Ex 2.28a)
 * @param values positive input values
 * @return the maximum result
 */
int maxPlus(vector<int> values) {

    int potentialMaximum = 0;
    int currentMaximum = -1;
    int potentialFirstElement = values[1];
    int potentialSecondElement = values[1];
    int elementNumber = 0;

    for (int h = 0; h < values.size(); h++) {

        if (potentialFirstElement < values[h]) {
            potentialFirstElement = values[h];
            elementNumber = h;
        }
    }

    for (int g = 0; g < values.size(); g++) {

        if (potentialSecondElement < values[g] && g != elementNumber) {
            potentialSecondElement = values[g];
        }

    }

    currentMaximum = potentialFirstElement + potentialSecondElement;

    return currentMaximum; /* replace this */
}

/**
 * This function returns the maximum a[j]-a[i] of a pair elements (as described in Ex 2.28b)
 * @param values positive input values
 * @return the maximum result
 */
int maxMinus(vector<int> values) {
    int potentialMaximum = 0;
    int currentMaximum = -1;
    int potentialFirstElement = values[1];
    int potentialSecondElement = values[1];
    int elementNumber = 0;

    for (int h = 0; h < values.size(); h++) {

        if (values[h] < potentialFirstElement) {
            potentialFirstElement = values[h];
            elementNumber = h;
        }
        if (values[h] > potentialSecondElement && h > elementNumber) {
            potentialSecondElement = values[h];
            currentMaximum = potentialSecondElement - potentialFirstElement;
        }
    }

    return currentMaximum; /* replace this */
}

/**
 * This function returns the maximum a[j]*a[i] of a pair elements (as described in Ex 2.28c)
 * @param values positive input values
 * @return the maximum result
 */
int maxProduct(vector<int> values) {

    int potentialMaximum = 0;
    int currentMaximum = -1;
    int potentialFirstElement = values[1];
    int potentialSecondElement = values[1];
    int elementNumber = 0;

    for (int h = 0; h < values.size(); h++) {

        if (potentialFirstElement < values[h]) {
            potentialFirstElement = values[h];
            elementNumber = h;
        }
    }

    for (int g = 0; g < values.size(); g++) {

        if (potentialSecondElement < values[g] && g != elementNumber) {
            potentialSecondElement = values[g];
        }

    }

    currentMaximum = potentialFirstElement * potentialSecondElement;

    return currentMaximum; /* replace this */
}

/**
 * This function returns the maximum a[j]/a[i] of a pair elements (as described in Ex 2.28c)
 * @param values positive input values
 * @return the maximum result
 */
int maxDivide(vector<int> values) {

    int potentialMaximum = 0;
    int currentMaximum = -1;
    int potentialFirstElement = values[1];
    int potentialSecondElement = values[1];
    int elementNumber = 0;

    for (int h = 0; h < values.size(); h++) {

        if (values[h] < potentialFirstElement) {
            potentialFirstElement = values[h];
            elementNumber = h;
        }
        if (values[h] > potentialSecondElement && h > elementNumber) {
            potentialSecondElement = values[h];
            currentMaximum = potentialSecondElement / potentialFirstElement;
        }
    }

    return currentMaximum; /* replace this */
}

int main(int argc, char* argv[]) {
    //Use clog for your debugging output
    clog << "This program runs from " << argv[0] << endl;
    if (argc < 2) {
        //Use cerr for error messages
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
            cout << "Sum : " << maxPlus(testVector) << endl;
            cout << "Diff: " << maxMinus(testVector) << endl;
            cout << "Prod: " << maxProduct(testVector) << endl;
            cout << "Div : " << maxDivide(testVector) << endl;
        }
    } else {
        cerr << "Can't open data file " << argv[1] << endl;
    }
    return 0;
}