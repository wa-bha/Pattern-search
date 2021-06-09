import java.io.Console;
import java.util.*;
import java.io.*;

public class REcompile {
    static String regex = "";
    static int j = 0;
    public static void main(String[] args) {
        String usgMsg = new String("Usage: java REcompile \"regex\"");
        if(args.length > 1) {
            System.out.println(usgMsg);
            return;
        }
        try {
            regex = args[0];
            System.out.println(regex);
        } catch (Exception e) {
            System.err.println(e);
            System.err.println(usgMsg);
            e.printStackTrace();
            return;
        }
    }

    public static void expression() {
        term();
    }

    public static void term() {
        factor();
    }

    public static void factor(){
        if(isVocab(regex.charAt(j))) {
            j++; // consuming literals
            
        }
    }

    public static boolean isVocab(char c) {
        if(Character.isLetter(c)) {
            return true;
        } else {
            return false;
        }
    }
}