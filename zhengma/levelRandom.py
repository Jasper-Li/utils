import os
import pdb
import random
def data2list(data):
    data=data.split()
    return data
def printOut(l):
    lineLen=10
    for i in range(len(l)):
        if i!=0 and i%10 == 0:
            print('\n'*2)
        print(l[i], end=' ')
    print('\n'*2)
def main():
    path = os.sys.argv[1]
    fr=open(path, "r", encoding='utf-8-sig', errors='replace')
    data=fr.read()
    fr.close()
    #pdb.set_trace()
    l=data2list(data)
    lRand=random.sample(l, len(l))
    print("Random data:")
    printOut(lRand)
if __name__ == '__main__':
    main()
