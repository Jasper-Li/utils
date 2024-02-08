package org.example;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {

        var archive = new SlowbootArchive();
        archive.run();
        System.out.println(STR."outfile: \{archive.outfile}");
    }
}