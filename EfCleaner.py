#!/usr/bin/env python3

# import standard lib
import os
import os.path as op
import sys
from pdb import set_trace
import argparse
from subprocess import check_call
import re

pattern = re.compile(R"^\s*$")

def is_empty(line):
    return pattern.fullmatch(line) != None

# import custom lib
def convert(input):
    if not op.isfile(input):
        raise FileNotFoundError(f'{input}')
    content = []
    with open(input, errors='replace', encoding='utf-8') as f:
        start = False
        buf = ''
        line_num = 0
        for line in f:
            print(f'line: "{line}"')
            line_num += 1
            # set_trace()
            if is_empty(line):
                print(f'line {line_num} is empty')
                if start:
                    print(f'line {line_num} is start end')
                    start = False 
                    buf += '\n'
                    content.append(buf)
                    buf = ''
            elif 'EffortlessEnglishClub.com' in line:
                print(f'line {line_num} has key')
                continue
            elif line.endswith(' \n'):
                start = True
                buf += line[:-1]
    if len(content) == 0:
        raise RuntimeError("content is empty")
    outpath = get_path_outfile(input)           
    with open(outpath, "wt", errors='replace', encoding='utf-8') as f:
        for line in content:
            f.write(line)
    print(f'write to {outpath}')                

def get_path_outfile(input):
    inbase, ext = op.splitext(input)
    outbase = inbase + "_out"
    i = 0
    out = f'{outbase}_{i}{ext}'
    while(op.isfile(out)):
        i += 1
        out = f'{outbase}_{i}{ext}'
    return out


def main():
    dsc = 'convert text on learning Eng!'
    version_major = 1
    version_minor = 0
    parser = argparse.ArgumentParser(description=dsc)
    parser.add_argument('-v', '--version', action='version', version='%d.%d'%(version_major, version_minor),
        help='version')
    parser.add_argument('input')
    args = parser.parse_args()

    convert(args.input)
    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
