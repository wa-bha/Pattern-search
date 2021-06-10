import java.io.Console;
import java.lang.Thread.State;
import java.util.*;
import java.io.*;

public class REcompile {
    static String regex = "ab*a+cd";
    static String[] ch;
    static int[] nxt1;
    static int[] nxt2;
    static int startState = 1; // the official starting state
    static int state = 0;
    static int index = 0;
    public static void main(String[] args) {
        String usgMsg = new String("Usage: java REcompile \"regex\"");
        if(args.length > 1) {
            System.out.println(usgMsg);
            return;
        }
        try {
            regex = args[0];
            ch = new String[regex.length()*2];
            nxt1 = new int[regex.length()*2];
            nxt2 = new int[regex.length()*2];
            startState = 1;
            expression();
            System.out.println(regex);
        } catch (Exception e) {
            System.err.println(e);
            System.err.println(usgMsg);
            e.printStackTrace();
            return;
        }
    }

    public static int expression() {
        int r;
        r = term();

        return r;
    }

    public static int term() {
        int r;
        r = factor();
        return r;
    }

    public static int factor(){
        char c = regex.charAt(index);
        int r;
        state = startState;
        //If the factor is a literal
        if(isVocab(regex.charAt(index))) {
            setState(state, regex.charAt(index), state+1, state+1);
            index++; 
            r = state;
            state++;
            startState++;
        }
        else {
            
        }

        return r;
    }

    public static boolean isVocab(char c) {
        if(Character.isLetter(c)) {
            return true;
        } else {
            return false;
        }
    }

    public static void setState(int s, char c, int n1, int n2) {
        ch[s]=c+"";
        nxt1[s]=n1;
        nxt2[s]=n2; 
    }
}