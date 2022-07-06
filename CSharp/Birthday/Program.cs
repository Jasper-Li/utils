using static System.Console;
using System.Text.Json;
using System.Text.Json.Serialization;


string configPath = "config.json";
using FileStream? configFile = File.Open(configPath, FileMode.Open);
if(configFile is null)
{
    WriteLine($"Failed to open {configPath}");
    return;
}
Names? names = JsonSerializer.Deserialize<Names>(configFile);
if (names is null ){
    WriteLine($"Failed to open {configPath}");
    return;
}

Write("你的名字: ");
string? name = ReadLine();

string callback = name switch {
    _ when name == names.She => "2022年生日快乐！",
    _ when name == names.He => "今天不是你的生日",
    _ => "你来错地方啦",
};

WriteLine($"{name}, {callback}.");

class Names {
    public string? She { get; set; }
    public string? He{ get; set; }
}