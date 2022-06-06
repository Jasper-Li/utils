using System.Diagnostics;

namespace LxhBase;
public class Cmd
{
    public static int Run(string exe, string? args)
    {
        Debug.WriteLine($"Cmd: {exe} {args}");
        ProcessStartInfo startInfo = new()
        {
            RedirectStandardOutput = true,
            RedirectStandardError = true,
            UseShellExecute = false,
            CreateNoWindow = true,
            FileName = "cmd.exe",
            Arguments = $"/c {exe} {args}",
        };

        if (startInfo == null)
        {
            throw new Exception("Failed to create ProcessStartInfo");
        }
        PrintVerbs(startInfo);
        string? output = null;
        using Process? p = Process.Start(startInfo);
        if (p == null)
        {
            throw new Exception($"Failed to execute {exe} {args}");
        }
        output = p.StandardOutput.ReadToEnd();
        p.WaitForExit();
        Debug.Write($"cmd finished. status: {p.ExitCode}. ");
        if (p.ExitCode == 0){
         Debug.WriteLine($"output: {output}");
        }
        else {
            string error = p.StandardError.ReadToEnd();
            Debug.WriteLine($"error: {error}");
        }
        return p.ExitCode;
    }
    private static void PrintVerbs(ProcessStartInfo startInfo) {
        Debug.WriteLine($"{startInfo.FileName} verb: {startInfo.Verb}, Verbs count: {startInfo.Verbs.Length}");
        int i = 0;
        foreach (var verb in startInfo.Verbs) {
            Debug.WriteLine($"{i++}: {verb}");
        }

    }
}
