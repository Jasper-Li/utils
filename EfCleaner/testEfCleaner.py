#!/usr/bin/env python3

# import standard lib
import sys
#from pdb import set_trace
from unittest import main, TestCase
import EfCleaner

# import custom lib

class EfCleanerTestCase(TestCase):
    def test_convert(self):
        input_file = 'test/sample1.txt'
        sample_1_out = EfCleaner.convert(input_file)
        print(sample_1_out)

if __name__ == '__main__':
    result = main()
    sys.exit(result)
