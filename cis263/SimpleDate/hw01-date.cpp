#include <iostream>
#include "SimpleDate.h"

using namespace std;

int main() {
    std::cout << "Hello, World!" << std::endl;

    SimpleDate d1(7,11,1998);
    SimpleDate d2(7,12,2019);

    cout << d1.getMonth() << endl;
    cout << d1.getDay() << endl;
    cout << d1.getYear() << endl;
    cout << d1.compareTo(d2) << endl;
    cout << d1.dayOfWeek() << endl;
    d2.daysFromNow(5);
    cout << d2.getMonth() << endl;
    cout << d2.getDay() << endl;
    cout << d2.getYear() << endl;
    cout << d1.isLeapYear() << endl;
    cout << SimpleDate::isLeapYear(2019) << endl;
    cout << d1.ordinalDate() << endl;




    return 0;
}