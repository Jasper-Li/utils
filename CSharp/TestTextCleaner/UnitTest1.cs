using Xunit;
using System;
using System.Diagnostics;
using System.IO;
using TextCleaner;

namespace TestTextCleaner;

public class UnitTest1
{
    static string testFile = Path.Combine("test_files", "origin.txt");
    static string tag = "TestTextCleaner";
    [Fact]
    public void Test1()
    {
        Assert.True(File.Exists(testFile));
        Debug.WriteLine($"[{tag}]test file full path: {Path.GetFullPath(testFile)}");

        Cleaner cleaner = new(testFile);
        Assert.True(File.Exists(Cleaner.GetOutputFile(testFile)));
    }
}