#!/usr/bin/env python3

# import standard lib
import os.path, os
import sys
from pdb import set_trace
import argparse

# import custom lib
def rename(path, old_suffix, new_suffix):
    new_path = path[:-len(old_suffix)] + new_suffix
    if not os.path.isfile(new_path):
        if not os.path.isfile(path):
            print("Error, file not found: ", path)
            sys.exit(-1)
        os.rename(path, new_path)
    
def highlightjs_rename(path):
    '''
    Rename highlight.pack.js -> highlight.min.js
    styles/*.css -> styles/*.min.css
    Args:
        path: a string, path to highlight dir.
    Returns:
        result. 0 for success.
    '''
    print(path)
    if not os.path.isdir(path):
        print("Error, not dir: ", path)
        return -1

    main = os.path.join(path, 'highlight.pack.js')
    old_suffix = '.pack.js'
    new_suffix = '.min.js'
    rename(main, old_suffix, new_suffix)

    old_suffix = '.css'
    new_suffix = '.min.css'
    styles_dir = os.path.join(path, 'styles')
    files = os.listdir(styles_dir)
    for f in files:
        if f.endswith(".css"):
            style_css_file = os.path.join(styles_dir, f)
            rename(style_css_file, old_suffix, new_suffix)

    return 0
def main():
    parser = argparse.ArgumentParser(description='rename high light files.')
    parser.add_argument("dir", help="path to hightlight dir")
    args = parser.parse_args()
    if args.dir:
        result = highlightjs_rename(args.dir)
    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
