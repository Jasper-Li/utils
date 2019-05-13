#!/usr/bin/env python3

# import standard lib
import os
import sys
from pdb import set_trace
import xml.etree.ElementTree as ET
from xml.etree.ElementTree import Element

# import custom lib
g_workflow_oem_merged_key = {
    "attrib_key":'oem_workflow_modify_flag',
    "index_key":'oem_workflow_modify_index'
}


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

    attrib_key = g_workflow_oem_merged_key['attrib_key']
    index_key = g_workflow_oem_merged_key['index_key']
    for sub_element in list(workflow):
        flag = sub_element.attrib.get(attrib_key, 'false')
        if flag == 'true':
            index = sub_element.attrib.get(index_key, -1)
            #print("index is ", index)
            if index != -1:
                oem_mods.append(sub_element)

    return oem_mods

def print_xml_element(elem):
    print('tag:', elem.tag)
    if elem.attrib:
        print('attrib:', elem.attrib)

    if not elem.text.startswith('\n  '):
        print('text:', elem.text)
    print()

def print_xml_tree(root, level=0):
    '''
    Args:
        root: type is xml.etree.ElementTree.Element. the root element.
    '''
    print('\t'*level)
    print_xml_element(root)

    for sub in list(root):
        print_xml(sub, level +1)

def get_tag_names_of_sub_elements(e):
    '''
    Args:
        e: Element instance.
    Returns:
        names: a set of strings.
    '''
    names = set()
    for i in list(e):
        names.add(i.tag)
    return names

def check_elements_analogues(this, merge):
    '''
    Return True, when these values are equal.
    1. number and tag names of sub elements.
    2. same attributes. skip keys in g_workflow_oem_merged_key

    Args:
        this, merge: Element instance.
    Returns:
       result: boolean value
    '''
    global g_workflow_oem_merged_key
    sub_this = get_tag_names_of_sub_elements(this)
    sub_merge = get_tag_names_of_sub_elements(merge)

    if sub_this != sub_merge:
        print("diff sub elements, a: {} b {}".format(sub_this, sub_merge))
        return False

    merge_attrib = merge.attrib.copy()
    for key in g_workflow_oem_merged_key.keys():
        value = g_workflow_oem_merged_key[key]
        if value in merge_attrib.keys():
            del merge_attrib[value]

    result = this.attrib == merge_attrib
    if not result:
        print("this.attrib: ", this.attrib)
        print("merge_attrib: ", merge_attrib)
    return result


def oem_merge_workflow_part(root, elements):
    '''
    Args:
        root: root element of base xml file.
        elements: a list of Element to be merged. returned by get_workflow_modification.
    Throws:
        runtime error.
    Returns:
        result = the new root
    '''
    check_path = 'workflow'
    workflow = root.find(check_path)
    if not workflow:
        raise RuntimeError('contents base xml file WRONG: there is no path ', check_path)

    workflow = list(workflow)
    for e in elements:
        default_idx = -1
        idx = int(e.get('oem_workflow_modify_index', default_idx))
        if idx < 0:
            raise RuntimeError("workflow oem merged element has wrong idx: ", idx)
        base_element = workflow[idx]
        if not check_elements_analogues(base_element, e):
            print("base:")
            print_xml_element(base_element)
            print("to be merged:")
            print_xml_element(e)
            raise RuntimeError('WRONG workflow oem merged element')

        for sub_base in base_element:
            base_element.remove(sub_base)

        base_element.extend(list(e))
    return root

def main():
    print("Not implement yet!")
    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
