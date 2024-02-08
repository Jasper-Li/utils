package org.example;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.out;

public class Commander {
    public ProcessBuilder processBuilder;
    public Process process;
    public final String[] cmd;
    public Commander(String[] cmd) {
        this(cmd, new File(System.getProperty("user.dir")));
        out.println(STR."cwd: \{System.getProperty("user.dir")}");
    }
    public Commander(String[] cmd, File directory) {
        this.cmd = cmd;
        this.processBuilder = new ProcessBuilder(cmd);
        processBuilder.directory(directory);
    }
    public void run() {
        try {
            process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            out.println(STR."Failed to run cmd: \{cmd}. Get exception: \{e}");
        }

    }
    public boolean hasRun() {return process!= null;}
    int exitValue() { return process.exitValue();}
    String output() {
        try {
            return new String(process.getInputStream().readAllBytes());
        } catch (IOException e) {
            return STR."Got IOException: \{e}";
        }
    }
    String error() {
        try {
            return new String(process.getErrorStream().readAllBytes());
        } catch (IOException e) {
            return STR."Got IOException: \{e}";
        }
    }
    void printCmd() {
        out.println(STR."Cmd: \{Arrays.toString(cmd)}");
        out.println(STR."Wording directory: \{processBuilder.directory()}");
    }
    void printResult() {
        if(hasRun()) {
            var status = exitValue();
            out.println(STR."exitValue: \{status}");
            if (status == 0) {
                out.println(STR."output: \{output()}");
            } else {
                out.println(STR."error: \{error()}");
            }
        }
    }
    void printStatus() {
        printCmd();
        printResult();
    }
}
