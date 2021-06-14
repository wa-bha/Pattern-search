
//
// Names:   Eugene Chew   ,   Bhavit Wadhwa
// IDs:     1351553       ,   1516846
//
import java.io.*;
import java.util.*;

public class REsearch {
    // Storing states from the FSM
    static ArrayList<stateNode> fsmNodes = new ArrayList<stateNode>();

    static Integer SCAN_STATE = -5;

    public static void main(String[] args) {

        // Check correct usage and standard input
        if (args.length == 0) {
            System.err.println("Please enter a valid *TEST* input files:");
            System.err.println("_______________________________________________________");
            System.err.println("STANDALONE usage examples: (copy/paste)");
            System.err.println(
                    "#1: REGEX: 'a(bra)+cada(bra)+'      : cat abracadabraFSM.txt | java REsearch abraTestInput.txt > out.txt");
            System.err.println(
                    "#2: REGEX: 'brown|test'             : cat brownTestFSM.txt | java REsearch abraTestInput.txt > out.txt\n");
            System.err.println("OR (with valid compiler):");
            System.err.println(
                    "#3: Usage testing example           : REcompile <regexExpressionHere> | java REsearch <inputTextFileHere> > out.txt\n");

            System.exit(0);
        }

        getFSM();

        // Test print the current inputted FSM
        // testPrintFSM();

        // Get the file into the buffered reader and read next line
        String nextLine;
        String filename = args[0];

        // DEBUG FILEREADER
        // try (BufferedReader fileReader = new BufferedReader(new
        // FileReader("abraTestInput.txt"))){

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(filename));
            nextLine = fileReader.readLine();

            // While there are still lines to read from the file

            while (nextLine != null) {
                String[] splitChars = nextLine.split("");

                // Check if the pattern matches the split chars
                if (executePatternSearch(splitChars)) {
                    System.err.println("Success: " + nextLine);
                    System.out.println(nextLine);
                } else {
                    System.err.println("Failed: " + nextLine);
                }
                nextLine = fileReader.readLine();
            }
            fileReader.close();

        } catch (Exception e) {
            System.err.println("Exception!");
            e.printStackTrace();
        }
    }

    // Recieves a string array containing split line from text file

    // Checks a substring can go through FSM and reach the final state -> contains
    // pattern)
    public static boolean executePatternSearch(String[] splitLine) {

        // Loop while there are still charcters to read
        // Update marker only if
        for (int marker = 0; marker < splitLine.length; marker++) {

            // Create dequeue and initialize "SCAN", "START STATE"
            Deque deque = new Deque();
            deque.addHead(0);

            // Loop for potential valid matches
            for (int pointer = marker; deque.checkHead(); pointer++) {
                deque.addTail(SCAN_STATE);

                // Loop each possible next state
                for (int currentStateValue = deque.getHead(); currentStateValue != SCAN_STATE; currentStateValue = deque
                        .getHead()) {
                    stateNode currentNode = fsmNodes.get(currentStateValue);

                    // If it is a BRANCH state
                    if (currentNode.ch.equals("br")) {

                        // START STATE (expected dummy state)
                        if (currentNode.next1 == currentNode.next2) {
                            deque.addHead(currentNode.next1);
                        }
                        // BRANCH STATE
                        else {
                            deque.addHead(currentNode.next1);
                            deque.addHead(currentNode.next2);
                        }

                        // If the next node is a final state, then it we found a match to the FSM
                        if (fsmNodes.get(currentNode.next1).ch.equals("null")
                                || fsmNodes.get(currentNode.next2).ch.equals("null")) {
                            return true;
                        }
                    }

                    // If it is a branch state, you should be able to continue
                    // even if there are no more characters that are in the line
                    else if (pointer >= splitLine.length) {
                        continue;
                    }

                    // Otherwise it is a CHARACTER state - check CHAR == INPUT
                    else if (currentNode.ch.equals(splitLine[pointer])) {

                        // If the next state is the end of the FSM (char == null)
                        if (fsmNodes.get(currentNode.next1).ch.equals("null")) {
                            return true;

                        }
                        deque.addTail(currentNode.next1);
                    }
                }
            }
        }

        return false;
    }

    // Test print the FSM
    public static void testPrintFSM() {
        System.err.println("s\tch\tn1\tn2");
        System.err.println("--------------------------");
        int nodeCount = 0;
        for (stateNode stateNode : fsmNodes) {
            System.err.println(nodeCount + "\t" + stateNode.ch + "\t" + stateNode.next1 + "\t" + stateNode.next2);
            nodeCount++;
        }
        System.err.println();
    }

    // Read standard input to populate the FSM arrays
    public static void getFSM() {

        String line = " ";
        String[] split;

        // DEBUG FILEREADER
        // try (BufferedReader reader = new BufferedReader(new
        // FileReader("abracadabraFSM.txt"))){

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            line = reader.readLine();
            int counter = 0;

            while (line != null) {
                split = line.split(",");

                fsmNodes.add(new stateNode(split[0].toString(), Integer.parseInt(split[1]), Integer.parseInt(split[2]),
                        counter));
                line = reader.readLine();
                counter++;
            }
        } catch (Exception e) {
            System.err.println("Invalid piped in FSM file");

            e.printStackTrace();
        }
    }
}