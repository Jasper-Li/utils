using System.Diagnostics;

namespace LxhBase;
public class Cmd {
    public static (int status, string output) Run(string exe, string? args) {
        Debug.WriteLine($"Cmd: {exe} {args}");
        ProcessStartInfo startInfo = new() {
            RedirectStandardOutput = true,
            RedirectStandardError = true,
            UseShellExecute = false,
            CreateNoWindow = true,
            FileName = "cmd.exe",
            Arguments = $"/c {exe} {args}",
        };

        if (startInfo == null) {
            throw new Exception("Failed to create ProcessStartInfo");
        }
        // PrintVerbs(startInfo);
        using Process? p = Process.Start(startInfo);
        if (p == null) {
            throw new Exception($"Failed to execute {exe} {args}");
        }
        p.WaitForExit();
        if (p.ExitCode != 0) {
            string error = p.StandardError.ReadToEnd();
            string msg = $"Failed to run cmd: '{startInfo.FileName} {startInfo.Arguments}'. status: {p.ExitCode}. error msg: {error}";
            Debug.WriteLine(msg);
            throw new Exception(msg);
        }
        string output = p.StandardOutput.ReadToEnd();
        return (p.ExitCode, output);
    }
    /*
    private static void PrintVerbs(ProcessStartInfo startInfo) {
        Debug.WriteLine($"{startInfo.FileName} verb: {startInfo.Verb}, Verbs count: {startInfo.Verbs.Length}");
        int i = 0;
        foreach (var verb in startInfo.Verbs) {
            Debug.WriteLine($"{i++}: {verb}");
        }
    }
    */
}
