#!/usr/bin/env python3
'''
Dependency:
1. 7z

'''
# import standard lib
import os
import sys
from pdb import set_trace
import argparse
from subprocess import check_call

# import custom lib

def create_mirror_archive(args):
    print('src: ', args.dir)

def main():
    dsc = 'Mirgrate git code from one place to another!'
    version_major = 1
    version_minor = 0
    parser = argparse.ArgumentParser(description=dsc)
    parser.add_argument('-v', '--version', action='version', version='%d.%d'%(version_major, version_minor),
        help='version')
    sb = parser.add_subparsers(help='sub command')

    mirror_archive = sb.add_parser('c', help='create git mirror archive')
    mirror_archive.add_argument('dir', help='input any subdir of target git repo.')
    mirror_archive.set_defaults(func=create_mirror_archive)

    args = parser.parse_args()
    args.func(args)

    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
