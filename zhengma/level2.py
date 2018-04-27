import os
def printTop10(l):
    for i in range(10):
        print(l[i], end='_')
    print("\nend")
def main():
    path=os.sys.argv[1]
    fr=open(path, "r", encoding='utf-8-sig', errors='replace')
    data=fr.read()
    fr.close()
    l=data.split()
    printTop10(l)
    

if __name__ == '__main__':
    main()
