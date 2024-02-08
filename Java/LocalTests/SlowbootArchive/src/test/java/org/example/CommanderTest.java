package org.example;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class CommanderTest {
    @Test
    void basic() {
        String[] cmd = {SlowbootArchive.ARCHIVER, "--help"};
        Commander commander = new Commander(cmd);
        assertFalse(commander.hasRun());
        out.println("Before Run");
        commander.printCmd();
        commander.run();
        assertEquals(0, commander.exitValue());
        out.println("After Run");
        commander.printResult();
    }
}