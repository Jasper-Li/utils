#!/usr/bin/env python3

# import standard lib
import sys
from pdb import set_trace
from unittest import main, TestCase

# import custom lib
import CtTemp

class SampleTestCase(TestCase):
    def test_load_csv(self):
        csv_file = 'data/上证180_pb_加权平均值20190614_201659.csv'
        result = CtTemp.load_csv(csv_file)
        pb_data_file = 'data/上证180_pb_加权平均值20190614_201659_shouldbe.txt'
        should_be = load_txt(pb_data_file)
        self.assertEqual(result, should_be)
        
        cdf = CtTemp.get_cdf_from_data(result)

def load_txt(txt):
    '''
    Returns:
        data: a list of float point number. or None.
    '''
    with open(txt, "r") as f:
        data = list()
        for line in f:
            data.append(float(line.strip()))
    return data
            
if __name__ == '__main__':
    result = main()
    sys.exit(result)
