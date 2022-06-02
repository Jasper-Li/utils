using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using LxhBase;

namespace LxhBaseMSTest;

[TestClass]
public class UnitTest1
{
    [TestMethod]
    public void TestMethod1()
    {
            List<TestCmd> checks = new()
        {
            new("pandoc", "--version", 0),
            new("asciidoctor.bat", "--version", 0),
            new("asciidoctor.bat", "-V", 0),
        };
        string? output = null;
        int result;
        foreach(TestCmd t in checks) {
            result  = Cmd.Run(t.Exe, t.Args);
            string? msg = $"Failed to test \"{t.Exe} {t.Args}\", output: {output}";
            Assert.AreEqual(t.Status, result, msg);
        }
    }
}
record TestCmd{
    public string Exe { get; init; }
    public string? Args{ get; init; }
    public int Status { get; init; }
    public string? Output { get; init; }
    public TestCmd(string exe, string? args, int status, string? output=null){
        Exe = exe;
        Args = args;
        Status = status;
        Output = output;
    }

}