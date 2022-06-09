using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.IO;
using System.Diagnostics;
//using static System.Diagnostics.Debug;
namespace Asciidoc2Markdown;

[TestClass]
public class UnitTest1
{
    [TestMethod]
    public void TestAsciidoc2Markdown()
    {
        string testDir = "test_files";
        string testFileNameBody = "my_adoc_sample";
        string adocFile = Path.Join(testDir, "my_adoc_sample.adoc");
        string mdExpect = Path.Join(testDir, "my_adoc_sample.md");
        string mdExpectCheck = Path.Join(testDir, "my_adoc_sample_expected.md");
        if (File.Exists(mdExpect)){
            File.Delete(mdExpect);
        }

        Assert.AreEqual(mdExpect, Asciidoc2Markdown.GetMdFile(adocFile));
        
        Asciidoc2Markdown.Run(adocFile);

        Assert.IsTrue(File.Exists(mdExpect));
        string mdContentExpect = File.ReadAllText(mdExpectCheck);
        Assert.AreEqual(mdContentExpect, File.ReadAllText(mdExpect));
    }
}