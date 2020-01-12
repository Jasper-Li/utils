#!/usr/bin/env python3

# import standard lib
import sys
from pdb import set_trace
from unittest import main, TestCase
import logging

# import custom lib
import ConfigPath
from Sample import Sample
import LogTools

class SampleTestCase(TestCase):
    def test_True(self):
        LogTools.setup()
        logger = logging.getLogger()
        logger.debug('debug message')
        logger.info('info message')
        logger.warning('warn message')
        logger.error('error message')
        logger.critical('critical message')

if __name__ == '__main__':
    result = main()
    sys.exit(result)
