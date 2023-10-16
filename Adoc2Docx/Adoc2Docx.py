#!/usr/bin/env python3

# import standard lib
import os
import sys
from pdb import set_trace
import argparse
from subprocess import check_call
# import custom lib

def check_adoc_suffix(file_name):
    '''
    Returns: a tuple. (flag, data)
        flag: True/False
        data: a string.
    '''
    adoc_suffixes = {"adoc"}
    for suffix in adoc_suffixes:
        if file_name.endswith(suffix):
            return (True, suffix)
    return (False, "")

def Adoc2Docx(adoc_file):
    flag, suffix = check_adoc_suffix(adoc_file)
    if not flag:
        print("Error: Wrong file suffix: ", adoc_file)
        return -1

    tmp_format = "docbook" # "docbook5"
    tmp_suffix = "docbook"
    body_name = adoc_file[:-(len(suffix)+1)]
    tmp_name = body_name + '_' + tmp_format + '.' + tmp_suffix

    cmd = 'asciidoctor -b ' + tmp_format + ' -o ' + tmp_name + ' ' + adoc_file
    check_call(cmd, shell = True)

    docx_name = body_name + '.docx'
    cmd = 'pandoc -f ' + tmp_format + ' -t docx -o ' + docx_name + ' ' + tmp_name
    check_call(cmd, shell = True)

    os.remove(tmp_name)
    print("Create file: ", docx_name)

    return 0

def main():
    parser = argparse.ArgumentParser(description='convert adoc to docx.')
    parser.add_argument("file", help="path to asciidoc file")
    args = parser.parse_args()
    if args.file:
        result = Adoc2Docx(args.file)

    return result

if __name__ == '__main__':
    result = main()
    sys.exit(result)
