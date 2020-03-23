#!/usr/bin/env python3

# import standard lib
import os
import sys
from pdb import set_trace
import argparse
from subprocess import check_call

# import custom lib

def main():
    dsc = 'Not implement yet!'
    version_major = 1
    version_minor = 0
    parser = argparse.ArgumentParser(description=dsc)
    parser.add_argument('-v', '--version', action='version', version='%d.%d'%(version_major, version_minor),
        help='version')
    args = parser.parse_args()


    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
