import java.io.*;
import java.util.*;

public class REsearch {

    //Storing FSM
    static ArrayList<String> ch = new ArrayList<String>();  
    static ArrayList<Integer> next1 = new ArrayList<Integer>();
    static ArrayList<Integer> next2 = new ArrayList<Integer>();

    public static void main(String[] args) {
        //Check correct usage and standard input
        if (args.length == 0) {
            System.err.println("Please enter a valid input text file");
            System.err.println("Usage example: testFSM.txt | java Dequeue MobyDick.txt\n");
            System.exit(0);
        }
        getFSM();
        
        //DELETE HERE
        testPrintFSM();

        //Create dequeue and initialize "SCAN"
        //Deque deque = new Deque();

        // Get the file into the buffered reader and read next line
        String nextLine;
        String filename = args[0];
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))){
            nextLine = fileReader.readLine();
            
            //While there are still lines in the file

            while (nextLine != null) {
                //If a matching pattern is found in the line ........
                if (executePatternSearch(nextLine)) {
                    System.out.println("Match found!!!");
                }

                nextLine = fileReader.readLine();
            }

        } catch (Exception e) {
            System.err.println("Invalid file");
            e.printStackTrace();
        }
    }

    // Recieves a string containing line from text file
    // Checks a substring can go through FSM and reach the final state -> contains pattern)
    public static boolean executePatternSearch(String line) {
        return true;
        //Check state character if branch
        //move to next state

        //If not brnach
        //match character
        //if the character matches, -
        // 1. move to next character in line
        // 2. move to next state in FSM as current state has matched
        // 3. match character again (loop)

        //if character not matched, shift marker to try next character set
        
        // Same loop again until final state in FSM (null)
        
        
    }

    public static void testPrintFSM() {
        System.out.println("s\tch\tn1\tn2");
        System.out.println("--------------------------");
        for(int i = 0; i < ch.size(); i++) {
        System.out.println(i + "\t" + ch.get(i) + "\t" + next1.get(i) + "\t" + next2.get(i));
        }
    }

    //Read standard input to populate the FSM arrays
    public static void getFSM() {

        String line = " ";
        String[] split;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            line = reader.readLine();
            while (line != null) {
                split = line.split(",");
                ch.add(split[0]);
                next1.add(Integer.parseInt(split[1]));
                next2.add(Integer.parseInt(split[2]));
                line = reader.readLine();
            }
        } catch(Exception e) {
            System.err.println("Invalid piped in FSM");
            e.printStackTrace();
        }
    }
}