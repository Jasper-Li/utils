#!/usr/bin/env python3

# import standard lib
import os, os.path
import sys
from pdb import set_trace
import logging, logging.config
import datetime

# import custom lib

def setup():
    log_dir = 'logs'
    if not os.path.isdir(log_dir):
        os.mkdir(log_dir)
    log_base= 'log_'+datetime.datetime.now().strftime("%Y%m%d_%H%M%S")+'.txt'
    log_path = os.path.abspath(os.path.join(
        os.path.dirname(__file__), log_dir, log_base))
    print('setup log_path', log_path)
    config = {
        'version':1,
        'formatters': {
            'detailed': {
                'class': 'logging.Formatter',
                'format': '%(asctime)s %(name)-8s %(levelname)-8s %(processName)-10s %(message)s'
            }
        },
        'handlers': {
            'console': {
                'class': 'logging.StreamHandler',
                'formatter': 'detailed',
            },
            'file': {
                'class': 'logging.FileHandler',
                'filename': log_path,
                'mode': 'w',
                'formatter': 'detailed',
            },
        },
        'root': {
            'level': 'DEBUG',
            'handlers': ['console', 'file']
        },
    }

    logging.config.dictConfig(config)


def main():
    print("Not implement yet!")
    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
