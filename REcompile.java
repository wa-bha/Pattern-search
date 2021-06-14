//
// Names:   Eugene Chew   ,   Bhavit Wadhwa
// IDs:     1351553       ,   1516846
//

import java.io.Console;
import java.lang.*;
import java.util.*;
import java.io.*;

public class REcompile {
    // test regex: ab*a+cd
    static String regex = "";
    static String[] ch;
    static int[] nxt1;
    static int[] nxt2;
    static int startState = 0;
    static int state = 1;
    static int index = 0;
    static int altState = -1;

    public static void main(String[] args) {
        String usgMsg = new String("Usage: java REcompile \"regex\"");
        int initial = 0;
        if (args.length > 1) {
            System.out.println(usgMsg);
            return;
        }
        try {
            regex = args[0];
            // regex = "a*";
            ch = new String[regex.length() + 2];
            nxt1 = new int[regex.length() + 2];
            nxt2 = new int[regex.length() + 2];
            initial = expression();
            if (initial == -1) {
                System.out.println("The Regular Expression: " + regex + " is not valid");
                return;
            } else {
                if (altState != -1) {
                    setState(startState, "br", altState, altState); // initialise a blank state
                    printFSM();
                    System.out.println("Regular Expression: " + regex);
                } else {
                    setState(startState, "br", initial, initial); // initialise a blank state
                    printFSM();
                    System.out.println("Regular Expression: " + regex);
                }
            }
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
        // check if we still have expressions
        if (index < regex.length() && r != -1) {

            expression();

        }
        return r;
    }

    public static int term() {
        int r, t1, t2, f;
        r = factor();
        t1 = r; // the start state
        f = state - 1; // the end state / latest state that was built
        // check if the current index is within the bounds of the regex length
        if (index < regex.length()) {

            // check special case of alternation

            // check Kleene closure
            if (regex.charAt(index) == '*') {
                // store
                int tempS = state - 2;
                String tempS_val = new String(ch[tempS] + " ");

                // create the branching state
                setState(state, "br", t1, state + 1);

                // change previous state values
                if (ch[tempS] == "br") {
                    setState(tempS, ch[tempS], nxt1[tempS], state);
                    index++;
                    r = state;
                    state++;
                } else if (isVocab(tempS_val.charAt(0)) || isSpecial(tempS_val.charAt(0))) { // check literals
                    setState(tempS, ch[tempS], state, state);
                    index++;
                    r = state;
                    state++;
                }
                return r;
            } else if (regex.charAt(index) == '+') { // check preceding regexp 1 or more
                // create the branching state
                setState(state, "br", t1, state + 1);
                index++;
                r = state;
                state++;

                return r;
            } else if (regex.charAt(index) == '?') { // check preceding regexp 0 or 1
                // store
                int tempS = state - 2;
                String tempS_val = new String(ch[tempS] + " ");

                // create the branching state
                setState(state, "br", t1, state + 1);

                // change previous state values
                if (ch[tempS] == "br") {
                    setState(tempS, ch[tempS], nxt1[tempS], state); // change the previous 2 state
                    setState(state - 1, ch[state - 1], state + 1, state + 1); // change the previous state
                    index++;
                    r = state;
                    state++;
                } else if (isVocab(tempS_val.charAt(0)) || isSpecial(tempS_val.charAt(0))) {
                    setState(tempS, ch[tempS], state, state);
                    setState(state - 1, ch[state - 1], state + 1, state + 1); // change the previous state
                    index++;
                    r = state;
                    state++;
                }
                return r;
            }
            if (regex.charAt(index) == '|' && index < regex.length()) { // check for alternation

                // create alternation branch
                setState(state, "br", r, state + 1);

                if ((ch[r - 1] == "br" && r - 1 == 0)) {
                    setState(r - 1, ch[r - 1], state, state);
                } else if (ch[r - 1] == "br" && r - 1 != 0) {
                    setState(r - 1, ch[r - 1], nxt1[r - 1], state);
                } else if (ch[r - 1] != "br") {
                    setState(r - 2, ch[r - 2], state, state);
                }
                // set initial starting state to point to our alternation branch
                index++;
                state++;
                // gets the
                t2 = term();

                // change the previous linked states next states
                if (ch[f] != "br") {
                    setState(f, ch[f], state, state);
                } else if (ch[f] == "br") {
                    setState(f, ch[f], nxt1[f], state);
                }

                r = t2 - 1;

                return r;
            }

        }
        return r;
    }

    public static int factor() {
        int r; // the result of the state that was just built
        if (isVocab(regex.charAt(index)) && regex.charAt(index) != '.' && (int) regex.charAt(index) != 92) {
            setState(state, regex.charAt(index) + "", state + 1, state + 1);
            index++;
            r = state;
            state++;
        } else if (regex.charAt(index) == '.') {
            setState(state, "dt", state + 1, state + 1);
            index++;
            r = state;
            state++;
        } else if ((int) regex.charAt(index) == 92) { // escape character
            String escapedChar = "";
            // skip over the \ character
            index++;
            escapedChar = String.valueOf(regex.charAt(index));
            System.out.println(escapedChar);
            // add the escaped character
            setState(state, escapedChar, state + 1, state + 1);
            index++;
            r = state;
            state++;

            // add the escaped char
        } else {
            if (regex.charAt(index) == '(') {
                index++;
                r = expression();

                if (regex.charAt(index) == ')') {
                    index++;
                } else {
                    return -1;
                }
            } else if (regex.charAt(index) == '[') {
                index++;
                r = addList();
            } else {
                System.out.println("Invalid Expression!");
                return -1;
            }
        }
        // returns the state that was built
        return r;
    }

    // handles alteration for []
    public static int addList() {
        int r;
        String s = "";
        while (regex.charAt(index) != ']') {
            s = s.concat(regex.charAt(index) + "");
            index++;
        }
        setState(state, s, state + 1, state + 1);
        index++;
        r = state;
        state++;
        return r;
    }

    // check is a vocabulary
    public static boolean isVocab(char c) {
        String specialChar = "*+?|()[]";
        if (!specialChar.contains(c + "")) {
            return true;
        } else {
            return false;
        }
    }

    // check is special character
    public static boolean isSpecial(char c) {
        String specialChar = "*+?|()[]";
        if (specialChar.contains(c + "")) {
            return true;
        } else {
            return false;
        }
    }

    // set state method
    public static void setState(int s, String c, int n1, int n2) {
        ch[s] = c;
        nxt1[s] = n1;
        nxt2[s] = n2;
    }

    // displays the FSM to console
    public static void printFSM() {
        System.out.println("s\tch\t1\t2");
        System.out.println("--------------------------");
        for (int i = 0; i < ch.length; i++) {
            System.out.println(i + "\t" + ch[i] + "\t" + nxt1[i] + "\t" + nxt2[i]);
        }
        System.out.println("--------------------------");
    }
}