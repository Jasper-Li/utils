import unittest
from archiver import checkSrc

class TestArchiver(unittest.TestCase):
	def fileCheck(self, path, shouldBe):
		if shouldBe:
			self.assertTrue(checkSrc(path))
		else:
			self.assertFalse(checkSrc(path))
	def fileListCheck(self, paths, shouldBe):
		if shouldBe:
			for path in paths:
				self.assertTrue(checkSrc(path))
		else:
			for path in paths:
				self.assertFalse(checkSrc(path), path + " should be False")

	def testCheckPath(self):
		oks=[
			"test/1.img",
			]
		nos = [
			"archiver-2.py"
			"archiver.py",
			"test/1.c",
			"test/1.cpp",
			"test/1.h",
			"test/1.java",
			"test/1.py",
			"test/1.sh",
			]
		self.fileListCheck(oks, True)
		self.fileListCheck(nos, False)


if __name__ == '__main__':
	    unittest.main()
