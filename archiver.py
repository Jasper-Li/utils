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
def checkFile(path):
	tailSets={
		"py",
		"c",
		"cpp",
		"java",
		"h",
		"sh",
		"pl",
	}
	fileTail= (os.path.splitext(path))[1][1:]
	res = not (fileTail in tailSets)
	if res == False:
		print("path:%s fileTail:%s res:%s" % (path, fileTail, res))
	return res
def checkSrc(path):
	if os.path.exists(path) == False:
		print(path + " not exits")
		return False
	
	if os.path.isfile(path):
		return checkFile(path)

def main():
	dst=getDstName()
	print("dst name is: " + dst)

if __name__ == '__main__':
	main()
