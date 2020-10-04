//
// Created by kurts on 8/30/2018.
//

#include "SimpleDate.h"
#include <stdexcept>

const int SimpleDate::DAYS_IN_MONTH[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
const int SimpleDate::DAYS_THUS_FAR[] = {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};

SimpleDate::SimpleDate(int month, int day, int year) {
    this->month = month;
    this->day = day;
    this->year = year;

    if (!isValidDate(month, day, year)) {
        throw std::invalid_argument ("invalid values for fields");

    }
}

int SimpleDate::getMonth() const {
    return month;
}

int SimpleDate::getDay() const {
    return day;
}

int SimpleDate::getYear() const {
    return year;
}

int SimpleDate::compareTo(SimpleDate other) const {
    if (this->year != other.year) {
        return this->year - other.year;
    }

    if (this->month != other.month) {
        return this->month - other.month;
    }

    return this->day - other.day;
}

int SimpleDate::dayOfWeek() const {
    int daysElapsed = 0;
    for (int year = MIN_YEAR; year < this->year; year++) {
        daysElapsed += daysInYear(year);
    }

    daysElapsed += ordinalDate();

    return daysElapsed % 7;
}

int SimpleDate::daysInYear(int year) const {
    return isLeapYear (year) ? 366 : 365;
}

SimpleDate SimpleDate::daysFromNow(int n) {
    if (n == 0) {
        return SimpleDate(month,day,year);
    }

    SimpleDate date(1, 1, 2000);

    if (n < 0) {
        throw std::invalid_argument ("n can't be negative");
    } else {
        date = nextDate();
        for (int i = 1; i < n; i++) {
            date = date.nextDate();
        }
    }

    if (date.getYear() < MIN_YEAR)
    throw std::invalid_argument ("Resulting date is before "
                                  "1/1/1753");

    return date;
}

bool SimpleDate::isLeapYear() const {
    return isLeapYear(year);
}

bool SimpleDate::isLeapYear(int year) {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
}

int SimpleDate::ordinalDate() const {
    int answer = DAYS_THUS_FAR[month] + day;
    if (isLeapYear() && month > 2) {
        answer++;
    }
    return answer;
}

bool SimpleDate::isValidDate(int month, int day, int year) {
    if (month < 1 || month > NUM_MONTHS) {
        return false;
    }

    if (day < 1 || day > daysInMonth(month, year)) {
        return false;
    }

    return year >= MIN_YEAR;
}

int SimpleDate::daysInMonth(int month, int year) {
    if (month == 2 && isLeapYear(year)) {
        return 29;
    }
    return DAYS_IN_MONTH[month];
}

SimpleDate SimpleDate::nextDate() {
    SimpleDate next(1,1,2000);

    // handle 31-day months
    if (month == 1 || month == 3 || month == 5 || month == 7 ||
        month == 8 || month == 10) {
        if (day < 31) {
            SimpleDate next(month, day + 1, year);
        } else {
            SimpleDate next(month + 1, 1, year);
        }
    }

    // handle 30-day month
    if (month == 4 || month == 6 || month == 9 || month == 11) {
        if (day < 30) {
            SimpleDate next(month, day + 1, year);
        } else {
            SimpleDate next(month + 1, 1, year);
        }
    }

    // handle February month
    if (month == 2) {
        if (day < 28) {
            SimpleDate next(month, day + 1, year);
        } else {
            if (isLeapYear()) {
                SimpleDate next(month, day + 1, year);
            } else {
                SimpleDate next(month + 1, 1, year);
            }
        }
    }

    // handle December month
    if (month == 12) {
        if (day < 31) {
            SimpleDate next(month, day + 1, year);
        } else {
            SimpleDate next(1, 1, year + 1);
        }
    }

    return next;
}

SimpleDate::~SimpleDate() {

}


