#!/usr/bin/env python3

# import standard lib
import sys
from pdb import set_trace
from unittest import main, TestCase, skip

# import custom lib
import CtTemp

class SampleTestCase(TestCase):
    #@skip('dev')
    def test_load_csv(self):
        csv_file = 'data/上证180_pb_加权平均值20190614_201659.csv'
        result = CtTemp.load_csv(csv_file)
        pb_data_file = 'data/上证180_pb_加权平均值20190614_201659_shouldbe.txt'
        should_be = load_txt(pb_data_file)
        self.assertEqual(result, should_be)
        
        cdf = CtTemp.get_cdf_from_data(result)
    def test_get_cdf_from_data(self):
        data = [1,2,3]
        cdf = CtTemp.get_cdf_from_data(data)
        data = [2,1,3]
        cdf = CtTemp.get_cdf_from_data(data)
        self.assertEqual(0.5, cdf)


    #@skip('dev')
    def test_get_CtTemp(self):
        pb_csv = 'data/上证180_pb_5.csv'
        pe_csv = 'data/上证180_pe_5.csv'
        ct_temp = CtTemp.get_CtTemp(pb_csv, pe_csv)

        pb_csv = 'data/上证180_pb_10.csv'
        pe_csv = 'data/上证180_pe_10.csv'
        ct_temp = CtTemp.get_CtTemp(pb_csv, pe_csv)
        
        csv = [
            (
                'data/上证180_pb_day_10y_20190615_073881.csv',
                'data/上证180_pe_ttm_day_10year_20190615_073561.csv'
            ),
            (
                'data/上证180_pb_week_10y_20190615_073881.csv',
                'data/上证180_pe_ttm_week_10year_20190615_073511.csv',
            ),
            (
                'data/上证180_pb_month_10y_20190615_073818.csv',
                'data/上证180_pe_ttm_month_10year_20190615_073525.csv',
            ),
            ('data/上证180_pb_month_3y_20190615_075955.csv',
             'data/上证180_pe_ttm_month_3y_20190615_080191.csv'),
            ('data/上证180_pb_week_3y_20190615_075906.csv',
             'data/上证180_pe_ttm_week_3y_20190615_080170.csv'),
            ('data/上证180_pb_day_3y_20190615_075972.csv',
             'data/上证180_pe_ttm_day_3y_20190615_080166.csv'),
            ('data/上证180_pb_month_all_20190615_080677.csv',
             'data/上证180_pe_ttm_month_all_20190615_080631.csv'),
            ('data/上证180_pb_week_all_20190615_080635.csv',
             'data/上证180_pe_ttm_week_all_20190615_080684.csv'),
            ('data/上证180_pb_day_all_20190615_080670.csv',
             'data/上证180_pe_ttm_day_all_20190615_080695.csv'),
        ]

        for pb_csv,pe_csv in csv:
            ct_temp = CtTemp.get_CtTemp(pb_csv, pe_csv)
            


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
