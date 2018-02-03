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
				self.assertFalse(checkSrc(path))

	def testCheckPath(self):
		oks=[
			"test/1.img",
			"archiver.py",
			]
		nos = [
			"archiver-2.py"
			]
		self.fileListCheck(oks, True)
		self.fileListCheck(nos, False)


if __name__ == '__main__':
	    unittest.main()
