#!/usr/bin/env python3

# import standard lib
import sys
from pdb import set_trace
from unittest import main, TestCase
import xml.etree.ElementTree as ET

# import custom lib
import XmlMerge

class SampleTestCase(TestCase):
    def test_ET(self):
        path = 'build_test/contents_base.xml'
        base_tree = ET.parse(path)
        base_root = base_tree.getroot()

        #XmlMerge.print_xml(base_root)

    def test_get_workflow_modification(self):
        path = 'build_test/contents_base.xml'
        result = XmlMerge.get_workflow_modification(path)
        self.assertEqual(0, len(result))

        path = 'build_test/contents_merge_none.xml'
        result = XmlMerge.get_workflow_modification(path)
        self.assertEqual(0, len(result))

        path = 'build_test/contents_merge.xml'
        result = XmlMerge.get_workflow_modification(path)
        self.assertEqual(1, len(result), "result is {}".format(result))

if __name__ == '__main__':
    result = main()
    sys.exit(result)
