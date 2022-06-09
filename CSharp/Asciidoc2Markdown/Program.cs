using static System.Console;
using System.Diagnostics;
using LxhBase;

namespace Asciidoc2Markdown;

public class Asciidoc2Markdown {
    public static int Run(string adocFile) {

        string tmpFile = Path.GetTempFileName();
        string mdFile = GetMdFile(adocFile);
        try {
            Cmd.Run("asciidoctor", $"-b docbook5 {adocFile} -o {tmpFile}");
            Cmd.Run("pandoc", $"-f docbook -t GFM {tmpFile} -o {mdFile}");
        } finally {
            File.Delete(tmpFile);
        }

        string msg = $"create markdown file: {mdFile}";
        WriteLine(msg);
        Debug.WriteLine(msg);
        return 0;
    }
    public static string GetMdFile(string adocFile) {
        return adocFile[0..^(Path.GetExtension(adocFile).Length)] + ".md";
    }
    public static void Main(string[] args){
        if (args.Length < 1) {
            WriteLine("exe file1 file2...");
        }
        foreach(var f in args){
            Run(f);
        }
    }
}