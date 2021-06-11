import java.io.*;
import java.util.*;

public class REsearch {

    //Storing FSM
    static ArrayList<String> char_ = new ArrayList<String>();  
    static ArrayList<Integer> next1 = new ArrayList<Integer>();
    static ArrayList<Integer> next2 = new ArrayList<Integer>();

    public static void main(String[] args) {

        String nextLine;
        getFSM();

        // Get the file into the buffered reader and read next line
        if (args[0] != null) {
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
    }

    // Recieves a string containing line from text file
    // Checks a substring can go through FSM and reach the final state -> contains pattern)
    public static boolean executePatternSearch(String line) {
        return true;
    }

    //Read standard input to populate the FSM arrays
    public static void getFSM() {

        String line = " ";
        String[] split;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            while (line != null) {
                line = reader.readLine();
                split = line.split(",");
                char_.add(split[0]);
                next1.add(Integer.parseInt(split[1]));
                next2.add(Integer.parseInt(split[2]));
            }
        } catch(Exception e) {
            System.err.println("Invalid piped in FSM");
            e.printStackTrace();
        }
    }
}