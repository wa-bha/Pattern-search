import java.io.*;
import java.util.*;

public class REsearch {

    //Storing states from the FSM
    static ArrayList<stateNode> allStates = new ArrayList<stateNode>();
    static Integer pointer = 0;
    static Integer marker = 0;
    static Deque deque;
    static Integer SCAN_STATE = -5;
    static Boolean foundMatch = false;

    public static void main(String[] args) {
        //Check correct usage and standard input
        if (args.length == 0) {
            System.err.println("Please enter a valid input text file");
            System.err.println("Usage testing example: cat brownTestFSM.txt | java Dequeue brownTestInput.txt > out.txt\n");
            System.exit(0);
        }
        getFSM();
        
        //DELETE HERE
        testPrintFSM();

        // Get the file into the buffered reader and read next line
        String nextLine;
        String filename = args[0];

        //DEBUG FILEREADER
        //try (BufferedReader fileReader = new BufferedReader(new FileReader("brownTestInput.txt"))){

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))){
            nextLine = fileReader.readLine();
            
            while (nextLine != null) {

                String[] splitChars = nextLine.split("");
                executePatternSearch(splitChars);
                System.err.println(foundMatch);

                if (foundMatch) {
                    System.err.println("Match found!!!");
                    System.err.println(nextLine);
                    System.out.println(nextLine);
                    foundMatch = false;
                }
                nextLine = fileReader.readLine();
            }

        } catch (Exception e) {
            System.err.println("Invalid file");
            e.printStackTrace();
        }
    }

    // Recieves a string array containing split line from text file
    // Checks a substring can go through FSM and reach the final state -> contains pattern)
    public static void executePatternSearch(String[] splitLine) {
        try {
            //While we have not reached the end of the 
            while (marker <= splitLine.length) {
                //Create dequeue and initialize "SCAN", "START STATE"
                deque = new Deque();
                deque.addHead(SCAN_STATE);
                deque.addHead(0);
                
                // Get the next head()
                int currentStateValue = deque.getHead();

                System.err.println(currentStateValue);

                // While the head is not the "SCAN" state
                while (currentStateValue != SCAN_STATE) {
                    stateNode currentNode = allStates.get(currentStateValue);

                    //If it is a BRANCH state
                    if (currentNode.ch == "br") {

                        //If it is the start state
                        if (currentNode.next1 == currentNode.next2) {
                            deque.addTail(currentNode.next1);
                            deque.getHead();

                        }
                        //Else, add both .next values to deque
                        deque.addTail(currentNode.next1);
                        deque.addTail(currentNode.next2);
                        deque.getHead();
                    }

                    //Otherwise it is a CHARACTER state - check CHAR == INPUT
                    if (currentNode.ch == splitLine[pointer]) {

                        //If the next state is the end of the FSM (char == null)
                        if (allStates.get(currentNode.next1).ch == null) {
                            foundMatch = true;
                        }
                        pointer++;
                        deque.addTail(currentNode.next1);
                        deque.getHead();
                    }

                    //Else, pattern will not match - try next character set
                    else {
                        //Increment primary pointer and reset secondary
                        marker++;
                        pointer = marker;
                    }
                }
                if (deque.head.next != null) {
                    deque.getHead();
                    deque.addTail(SCAN_STATE);
                }
            }

        } catch (Exception e) {
            System.err.println("Invalid file");
            e.printStackTrace();
        }
    }

    // Test print the FSM
    public static void testPrintFSM() {
        System.err.println("s\tch\tn1\tn2");
        System.err.println("--------------------------");
        int nodeCount = 0;
        for (stateNode stateNode : allStates) {
            System.err.println(nodeCount + "\t" + stateNode.ch + "\t" + stateNode.next1 + "\t" + stateNode.next2);
            nodeCount++;
        }
        System.err.println();
    }

    // Read standard input to populate the FSM arrays
    public static void getFSM() {

        String line = " ";
        String[] split;

        //DEBUG FILEREADER
        //try (BufferedReader fileReader = new BufferedReader(new FileReader("brownTestInput.txt"))){


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            line = reader.readLine();
            while (line != null) {
                split = line.split(",");
                allStates.add(new stateNode(split[0].toString(), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
                line = reader.readLine();
            }
        } catch(Exception e) {
            System.err.println("Invalid piped in FSM");
            e.printStackTrace();
        }
    }
}