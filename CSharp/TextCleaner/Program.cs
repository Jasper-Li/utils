using static System.Console;
using TextCleaner;

public class Program
{

    public static int Main(string[] args)
    {
        if (args.Length < 1)
        {
            WriteLine("usage: exe file_path");
            return 1;
        }
        Cleaner cleaner = new(args[0]);
        return 0;
    }
}