#!/usr/bin/env python3

# import standard lib
import os,os.path
import sys
from pdb import set_trace

current = os.path.abspath(__file__)
cur_dir = os.path.dirname(current)
parent_dir = os.path.dirname(cur_dir)
if parent_dir not in sys.path:
    sys.path.append(parent_dir)
    #print('appending sys.path', parent_dir)

def main():
    print("Not implement yet!")
    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
