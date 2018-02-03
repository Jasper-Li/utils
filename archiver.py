import sys
import os.path
import time
def getDstName():
	src=sys.argv[1]
	srcBody= (os.path.splitext(os.path.basename(src)))[0]
	dstDir="~/tmp/"
	timeStamp=time.strftime("%Y%m%d-%H%M%S")
	dst=dstDir + srcBody + timeStamp + ".7z"
	return dst


def main():
	dst=getDstName()
	print(dst)

if __name__ == '__main__':
	main()
