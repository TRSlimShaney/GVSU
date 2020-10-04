//
// Created by kurts on 8/30/2018.
//

#ifndef HW01DATE_SIMPLEDATE_H
#define HW01DATE_SIMPLEDATE_H

class SimpleDate {
public:
    SimpleDate(int month, int day, int year);
    ~SimpleDate();
    int getMonth() const;
    int getDay() const;
    int getYear() const;
    int compareTo(SimpleDate other) const;
    int dayOfWeek() const;
    SimpleDate daysFromNow(int n);
    bool isLeapYear() const;
    static bool isLeapYear(int year);
    int ordinalDate() const;
    static const int MIN_YEAR = 1753;


private:
    static const int NUM_MONTHS = 12;
    static const int DAYS_IN_MONTH[];
    static const int DAYS_THUS_FAR[];
    int month;
    int day;
    int year;
    int daysInYear(int year) const;
    static bool isValidDate(int month, int day, int year);
    static int daysInMonth(int month, int year);
    SimpleDate nextDate();
};


#endif //HW01DATE_SIMPLEDATE_H
