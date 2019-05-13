#!/usr/bin/env python3

# import standard lib
import os
import sys
from pdb import set_trace
import xml.etree.ElementTree as ET
from xml.etree.ElementTree import Element

# import custom lib
def get_workflow_modification(oem_merge_file):
    '''
    What does it find:
        1. path: contents/workflow/
        2. elements has both attributes:
            (1) oem_workflow_modify_flag: true/false. we choose true only.
            (2) oem_workflow_modify_index: an integer for index of element which to be modified.
              1. The integer starts from 1.
              2. the number of sub-elements should be the same as the base one.

              Because there are elements with the same tags and attributes in contents/workflow.
              We use the index to locate which one we will modify.
              this element will replace the content of base version

    Args:
        oem_merge_file: path to *oem_contents.xml which oem will modified

    Returns:
        oem_mods: a list of xml.etree.ElementTree.Element.
    '''
    oem_mods = list()
    tree = ET.parse(oem_merge_file)
    root= tree.getroot()

    if root.tag != 'contents':
        print("Warning: root.tag should be contents. now: ", root.tag)

    workflow = root.find('workflow')
    if not workflow:
        print("no workflow in root.", workflow)
        return oem_mods

    attrib_key = 'oem_workflow_modify_flag'
    index_key = 'oem_workflow_modify_index'
    for sub_element in list(workflow):
        flag = sub_element.attrib.get(attrib_key, 'false')
        if flag == 'true':
            index = sub_element.attrib.get(index_key, -1)
            #print("index is ", index)
            if index != -1:
                oem_mods.append(sub_element)

    return oem_mods

def print_xml(root, level=0):
    '''
    Args:
        root: type is xml.etree.ElementTree.Element. the root element.
    '''
    print('\t'*level, end='')
    print('tag:', root.tag, end=' ')
    if root.attrib:
        print('attrib:', root.attrib, end=' ')

    if not root.text.startswith('\n  '):
        print('text:', root.text, end='')
    print()

    for sub in list(root):
        print_xml(sub, level +1)

def main():
    print("Not implement yet!")
    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
