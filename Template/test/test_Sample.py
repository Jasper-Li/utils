#!/usr/bin/env python3

# import standard lib
import sys
from pdb import set_trace
from unittest import main, TestCase

# import custom lib
sys.path.append('..')
from Sample import Sample

class SampleTestCase(TestCase):
    def test_True(self):
        self.assertFalse(Sample.main())

if __name__ == '__main__':
    result = main()
    sys.exit(result)
