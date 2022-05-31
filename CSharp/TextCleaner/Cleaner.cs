
using System.Diagnostics;
using System.Text;
using static System.Console;
namespace TextCleaner;

public class Cleaner{
    private enum LineStatus
    {
        NewLine,
        Concat,
    };
    public static string GetOutputFile(string input){
        return Path.Combine(Path.GetDirectoryName(input),
            Path.GetFileNameWithoutExtension(input) + "_out" + Path.GetExtension(input));

    }
    public Cleaner(string file)
    {
        if (!File.Exists(file)) {
            WriteLine("No such file: {file}");
        }
        StringBuilder sb = new();
        LineStatus prev = LineStatus.NewLine;
        LineStatus current = LineStatus.NewLine;
        string? buf = null;
        foreach(string line in File.ReadLines(file)){
            if (line.Length == 0 ||line.Contains("EffortlessEnglishClub.com")) {
                current= LineStatus.NewLine;
            } else
            {
                buf = line.TrimStart();
                if (buf.Length == 0 ) {
                    current= LineStatus.NewLine;
                } else {
                    current = LineStatus.Concat;
                }
            }
            if (current == LineStatus.Concat)
            {
                sb.Append(buf);
            } else if(prev == LineStatus.Concat){
                sb.Append('\n');
            }
            prev = current;
        }
        using StreamWriter writer = new(GetOutputFile(file));
        writer.Write(sb);
        //Debug.WriteLine(sb.ToString());
    }
}