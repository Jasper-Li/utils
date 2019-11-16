#!/usr/bin/env python3

# import standard lib
import os
import os.path
import sys
from pdb import set_trace
import csv
from pprint import pprint
import statistics
import math
from scipy.stats import norm
# import custom lib

g_keys = {
    'PB(加权平均值)',
    'PE-TTM(加权平均值)',
}
def get_index(line):
    '''
    Returns:
        key,index or None
    '''
    for key in g_keys:
        if key in line:
            index = line.index(key)
            return key,index
    return None,None
        
def print_csv(reader):
    length = 10
    i = 0
    for row in reader:
        print(row)
        i += 1
        if i >= length:
            break

def get_cdf_from_file(csv_file):
    '''
    Returns:
        cdf: a float number
    '''
    data = load_csv(csv_file)
    cdf = get_cdf_from_data(data)
    return cdf

def get_cdf_from_data(data):
    '''
    Returns:
        cdf: a float number
    '''
    mean = statistics.mean(data)
    variance = statistics.variance(data)
    stand_var = math.sqrt(variance)

    cdf = norm.cdf(data[0], mean, stand_var)

    #print('mean:{}, variance:{}, stand_var:{}, cdf:{}'.format(
    #    mean, variance, stand_var, cdf
    #))
    return cdf
    
def load_csv(csv_file):
    '''
    Returns:
        data: a list of float point number. or None.
    '''
    with open(csv_file, "r", encoding='utf-8-sig') as f:
        BOM = '\ufeff'
        len_BOM = len(BOM)
        b = f.read(len_BOM)
        if b == BOM:
            pass
        else:
            f.seek(-1 * len_BOM)

        reader = csv.reader(f)
        
        first_line = next(reader)
        #print('first: ', first_line)
        
        key,index = get_index(first_line)
        if not key:
            print("Error. no key found")
            return None
        #print('key:{} index:{}'.format(key, index))
        line_length = len(first_line)
        data = list()
        for line in reader:
            if len(line) != line_length:
                continue
            data.append(float(line[index]))
        #pprint(data)
        return data 
        #set_trace()

def get_cdf_from_path(csv):
    '''
    Args:
        csv: a string of file path to csv file.
    Returns:
        cdf: a float number, cdf of the csv file.
        or msg: a string.
    '''
    if not os.path.isfile(csv):
        msg = "Not find file: " + csv
        print(msg)
        return msg
    cdf = get_cdf_from_file(csv)
    return cdf

def get_CtTemp(pb_csv, pe_csv):
    '''
    Args:
        pb_csv, pe_csv: file path to csv file
    Returns:
        ct_temp: a float number. Chang Tou Temperature.
    '''
    pb_cdf = get_cdf_from_path(pb_csv)
    if isinstance(pb_cdf, str):
        return pb_cdf
    pe_cdf = get_cdf_from_path(pe_csv)
    if isinstance(pe_cdf, str):
        return pe_cdf
    ct_temp = (pb_cdf + pe_cdf)/2
    #print('\nResult:\nfile: {} {} \nct_temp: {}\n'.format(pb_csv, pe_csv, ct_temp))
    print('{},{},{}'.format(pb_csv, pe_csv, ct_temp))
    return ct_temp

def main():
    pb_cdf = input('Input pb file: ')
    pe_cdf = input('Input pe file: ')
    CtTemp = get_CtTemp(pb_cdf, pe_cdf)
    print('CtTemp: ', CtTemp)

    return 0

if __name__ == '__main__':
    result = main()
    sys.exit(result)
