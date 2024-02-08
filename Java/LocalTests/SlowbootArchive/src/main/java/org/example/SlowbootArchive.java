package org.example;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.FormatProcessor.FMT;

public class SlowbootArchive {
    public static final String ARCHIVER= "C:\\Program Files\\7-Zip\\7z.exe";
    public static final String defaultDirectory = "E:\\git\\boot-documentation\\DownloadTools\\wxSlowBoot\\vc_mswu";
    public static final List<String> components = List.of(
           "SecureSlowBoot.exe",
            "ExternalExe\\",
            "project_config.conf"
    );
    public List<String> cmdCompess;
    public final String outfile;
    public final Commander commander;
    public SlowbootArchive() {
        outfile = archiveName();
        cmdCompess = new ArrayList<>(List.of(
                ARCHIVER,
                "a",
                outfile,
                "-m0=lzma2"
        ));
        cmdCompess.addAll(components);
        final var userDir = "user.dir";
        final var workingDirectory =  System.getProperty(userDir);
        System.out.println(STR."Current working directory: \{workingDirectory}");
        Path path = Path.of(workingDirectory).resolve("SecureSlowBoot.exe");
        var directory = Files.exists(path) ? workingDirectory.toString() : defaultDirectory;
        this.commander = new Commander(cmdCompess.toArray(new String[0]), new File(directory));
        commander.printCmd();
    }
    public void run() {
        commander.run();
        commander.printResult();
    }
    public static String archiveName() {
        var t = LocalDateTime.now();
        var timestamp =FMT."\{t.getYear()}%02d\{t.getMonthValue()}%02d\{t.getDayOfMonth()}-%02d\{t.getHour()}%02d\{t.getMinute()}%02d\{t.getSecond()}";
        return STR."slowboot-\{timestamp}.7z";
    }
}
