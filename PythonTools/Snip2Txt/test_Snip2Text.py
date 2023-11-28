#!/usr/bin/env python3

# import standard lib
import sys
#from pdb import set_trace
from unittest import main, TestCase

# import custom lib

class SampleTestCase(TestCase):
    def test_True(self):
        self.assertTrue(True)

if __name__ == '__main__':
    result = main()
    sys.exit(result)
