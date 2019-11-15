#!/usr/bin/env python3
# -*- coding: utf-8 -*-
# import standard lib
import sys
from pdb import set_trace
from unittest import main, TestCase, skip
import shutil
import os, os.path
import subprocess

# import custom lib
import Rename

class SampleTestCase(TestCase):
    def test_get_new_name(self):
        name = 'dir_in/0350.赘 X（关注公众号《霞浦007》领取更多有声小说）.m4a'
        print('name: ', name)
        new_name = Rename.get_new_name(name)
        should = 'dir_in/0350.赘 X.m4a'
        self.assertEqual(new_name, should)
        name = '001-002.txt'
        should = name
        self.
    #@skip('dev')
    def test_rename_file(self):
        name = '0350.赘 X（关注公众号《霞浦007》领取更多有声小说）.m4a'
        shutil.copy(os.path.join('dir_in',name), 'dir_out')
        name = os.path.join('dir_out', name)
        new_path =  Rename.rename_file(name)
        self.assertTrue(os.path.isfile(new_path))
        subprocess.run('rm dir_out/* -v', shell=True)

    #@skip('dev')
    def test_rename_files_in_folder(self):
        new_dir = 'dir_out2'
        if os.path.isdir(new_dir):
            shutil.rmtree(new_dir)
        cmd = 'cp -r dir_in -v ' + new_dir
        subprocess.check_call(cmd)
        
        Rename.rename_files_in_folder(new_dir)

if __name__ == '__main__':
    result = main()
    sys.exit(result)
