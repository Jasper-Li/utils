using System.Diagnostics;

namespace LxhBase;
public class Cmd
{
    public static int Run(string exe, string? args)
    {
        ProcessStartInfo pInfo = new()
        {
            //RedirectStandardOutput = true,
            UseShellExecute = true,
            CreateNoWindow = true,
            FileName = exe,
            Arguments = args,
        };
        if (pInfo == null){
            throw new Exception("Failed to create ProcessStartInfo");
        }
        using Process p = Process.Start(pInfo);
        if(p == null){
            throw new Exception($"Failed to execute {exe} {args}");
        }
        p.WaitForExit();
        return p.ExitCode;
    }
}
