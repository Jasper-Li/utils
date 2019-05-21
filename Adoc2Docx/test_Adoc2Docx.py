#!/usr/bin/env python3

# import standard lib
import sys
from pdb import set_trace
from unittest import main, TestCase
import os.path

# import custom lib
from Adoc2Docx import Adoc2Docx

class SampleTestCase(TestCase):
    def test_Adoc2Docx(self):

        f = 'test/non.txt'
        result = Adoc2Docx(f)
        self.assertEqual(-1, result)
        
        f = 'test/my_adoc_sample.adoc'
        result = Adoc2Docx(f)
        
        f = 'my_adoc_sample_hightlihgts.adoc'
        result = Adoc2Docx(f)

        self.assertEqual(0, result)
        tmp_file = 'test/my_adoc_sample_docbook.docbook'
        out_file = 'test/my_adoc_sample.docx'
        self.assertFalse(os.path.isfile(tmp_file), "should no file: " + tmp_file)
        self.assertTrue(os.path.isfile(out_file), "should no file: " + tmp_file)


if __name__ == '__main__':
    result = main()
    sys.exit(result)
