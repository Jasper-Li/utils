#!/usr/bin/env python3
# -*- coding: utf-8 -*-

# import standard lib
import os, os.path
import sys
from pdb import set_trace

# import custom lib
def rename_files_in_folder(folder):
    if not os.path.isdir(folder):
        print("Not a folder: ", folder)
        return
    names = os.listdir(folder)
    for name in names:
        path = os.path.join(folder, name)
        rename_file(path)

def get_new_name(name):
    tail = '（关注公众号《霞浦007》领取更多有声小说）'
    tail_idx_start = name.find(tail)
    if tail_idx_start == -1:
        print("no tail in path")
        return ''
    new_name = name[:tail_idx_start] + name[tail_idx_start + len(tail):]
    return new_name

def rename_file(name):
    '''
    Returns:
        new_path: a string.
    '''
    if not os.path.isfile(name):
        print('It isnot a file: ', name)
        return ''
    new_name = get_new_name(name)
    if len(new_name) == 0:
        return
    if os.path.isfile(new_name):
        print("file already exists: ", new_name)
        return new_name

    os.rename(name, new_name)
    print(name, ' --> ', new_name)
    return new_name
    
def main():
    rename_files_in_folder(sys.argv[1])
    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
