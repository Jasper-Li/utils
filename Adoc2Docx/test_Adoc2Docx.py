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
        f = 'test/my_adoc_sample.adoc'
        result = Adoc2Docx(f)

        self.assertEqual(0, result)
        tmp_file = 'test/my_adoc_sample_docbook.docbook'
        self.assertTrue(os.path.isfile(tmp_file), "no file: " + tmp_file)


if __name__ == '__main__':
    result = main()
    sys.exit(result)
