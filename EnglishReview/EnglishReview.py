#!/usr/bin/env python3

# import standard lib
import os
import sys
from pdb import set_trace
from datetime import date, timedelta
from pprint import pprint

# import custom lib

def main():
    lesson1 = date(2020, 1, 28)
    delta = timedelta(7)
    lessons_num = 40
    lessons = list()
    weeks = list()
    for i in range(lessons_num):
        start = lesson1 + i * delta
        end = start + delta - timedelta(1)
        review0 = start + delta
        review1 = review0 + delta
        review2 = review1 + delta * 2
        review3 = review2 + delta * 4
        lesson = (start, review1, review2, review3)
        lessons.append(lesson)

        start = 1 + i
        review0 = start + 1
        review1 = review0 + 1
        review2 = review1 + 2
        review3 = review2 + 4
        week = (start, review1, review2, review3)
        weeks.append(week)

    #print_lessons(lessons)
    print_weeks(weeks)
    return 0

def print_lessons(lessons):
    for l in lessons:
        for d in l:
            print(d, end=', ')
        print()

def print_weeks(weeks):
#    for l in weeks:
#        for d in l:
#            print("{0:2}".format(d), end=', ')
#        print()
    end_char = ','
    for w in weeks:
        i = 0
        for d in range(1, w[-1]+1):
            if d not in w:
            #    print(' ' * 2, end=end_char)
                print(end=end_char)
            else:
                print("{0:2}".format(w[i]), end=end_char)
                i += 1
        print()
if __name__ == '__main__':
    result = main()
    sys.exit(result)
