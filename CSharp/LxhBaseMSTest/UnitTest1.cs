using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using LxhBase;

namespace LxhBaseMSTest;

[TestClass]
public class UnitTest1 {
    [TestMethod]
    public void TestCmd() {
        TestCmd[] checks = {
            new("pandoc", "--version", 0),
            new("asciidoctor.bat", "--version", 0),
            new("asciidoctor.bat", "-V", 0),
            new("asciidoctor", "-V", 0),
        };
        foreach (TestCmd t in checks) {
            (int result, string output) = Cmd.Run(t.Exe, t.Args);
            string? msg = $"Failed to test \"{t.Exe} {t.Args}\", output: {output}";
            Assert.AreEqual(t.Status, result, msg);
        }
    }
    [TestMethod]
    public void TestCmd2() {
        (string exe, string args, int status, string? output)[] checks = {
            ("pandoc", "--version", 0, null),
            ("asciidoctor", "-version", 0, null), 
            ("asciidoctor.bat", "-version", 0, null), 
        };
        foreach((string exe, string args, int statusExpect, string? outputExpect) in checks) {
            (int status, string output) = Cmd.Run(exe, args);
            string msg = $"Failed to test \"{exe} {args}\", output: {output}";
            Assert.AreEqual(statusExpect, status, msg);
            if(outputExpect is not null) {
                Assert.AreEqual(outputExpect, output);
            }
        }
    }
}
record TestCmd {
    public string Exe { get; init; }
    public string? Args { get; init; }
    public int Status { get; init; }
    public string? Output { get; init; }
    public TestCmd(string exe, string? args, int status, string? output = null) {
        Exe = exe;
        Args = args;
        Status = status;
        Output = output;
    }

}