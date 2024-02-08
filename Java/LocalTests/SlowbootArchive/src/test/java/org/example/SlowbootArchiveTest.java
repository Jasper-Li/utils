package org.example;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.lang.System.out;
import static java.util.FormatProcessor.FMT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SlowbootArchiveTest {
    @Test
    void fullFlow() {
        var archive = new SlowbootArchive();
        out.println(STR."\{archive.cmdCompess }");
        archive.run();
    }
    @Test
    void currentDirectory() {
        final var userDir = "user.dir";
        final var workingDirectory =  System.getProperty(userDir);
        out.println(STR."Default Working Directory = \{workingDirectory}");

        final var debugOut = "E:\\git\\boot-documentation\\DownloadTools\\wxSlowBoot\\vc_mswud";
        System.setProperty(userDir, debugOut);
        Path path = Path.of(debugOut).resolve("SecureSlowBoot.exe");
        final var workingDirectoryNew =  System.getProperty(userDir);
        assertEquals(debugOut, workingDirectoryNew);

        var status = Files.exists(path);
        out.println(STR."status: \{status}");
    }
    @Test
    void archiveName() {
        var name = "slowboot-20240123-114156.7z";
        assertEquals(27, name.length());
        final var archiveName = SlowbootArchive.archiveName();
        out.println(archiveName);
        assertThat(27, lessThanOrEqualTo(archiveName.length()));
    }

    @Test
    void stringTemplateFormat() {
        int v = 1;
        final var value = FMT."%03d\{v}";
        assertEquals("001", value);
    }
}