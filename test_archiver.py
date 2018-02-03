import unittest
from archiver import checkSrc

class TestArchiver(unittest.TestCase):
	def testCheckPath(self):
		path="archiver.py"
		nopath="archiver-2.py"
		ok = "test/1.img"
		self.assertFalse(checkSrc(nopath))
		self.assertTrue(checkSrc(path))
		self.assertTrue(checkSrc(ok))

if __name__ == '__main__':
	    unittest.main()
