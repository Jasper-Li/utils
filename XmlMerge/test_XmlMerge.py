#!/usr/bin/env python3

# import standard lib
import sys
from pdb import set_trace
from unittest import main, TestCase, skip
import xml.etree.ElementTree as ET

# import custom lib
import XmlMerge

class SampleTestCase(TestCase):
    #@skip("dev")
    def test_ET(self):
        base_path = 'build_test/contents_base.xml'
        base_tree = ET.parse(base_path)
        copy_path = base_path[:-4] + '_copy.xml'
        base_tree.write(copy_path)
        copy_tree = ET.parse(copy_path)

        base_root = base_tree.getroot()
        merge_path = 'build_test/contents_merge.xml'
        merge_elements = XmlMerge.get_workflow_modification(merge_path)
        base_root = XmlMerge.oem_merge_workflow_part(base_root, merge_elements)

        final_path = base_path[:-4] + '_out.xml'
        base_tree.write(final_path)

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

    def test_check_elements_analogues(self):
        path = 'build_test/contents_base.xml'
        base_tree = ET.parse(path)
        base_root = base_tree.getroot()

        workflow_elems = list(base_root[0])
        self.assertFalse(XmlMerge.check_elements_analogues(workflow_elems[0], workflow_elems[1]))
        self.assertTrue(XmlMerge.check_elements_analogues(workflow_elems[1], workflow_elems[2]))
        self.assertFalse(XmlMerge.check_elements_analogues(workflow_elems[3], workflow_elems[2]))

        base = ET.Element('base', {1:1})
        attrib = {1:1, 'oem_workflow_modify_flag': 'true', 'oem_workflow_modify_index': '2'}
        merged = ET.Element('merged', attrib)
        self.assertTrue(XmlMerge.check_elements_analogues(base, merged))

if __name__ == '__main__':
    result = main()
    sys.exit(result)
