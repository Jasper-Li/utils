package util;

public class StringUtil {
    public static String NEW_LINE = java.lang.System.getProperty("line.separator");
    private  StringUtil() {}
    public static String appendNewLine(String s) {
        return  s + NEW_LINE;
    }
}
