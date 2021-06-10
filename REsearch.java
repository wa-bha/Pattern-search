import java.io.*;
import java.util.*;

public class REsearch {

    static ArrayList<String> char_ = new ArrayList<String>();  
    static ArrayList<Integer> next1 = new ArrayList<Integer>();
    static ArrayList<Integer> next2 = new ArrayList<Integer>();

    public static void main(String[] args) {

        // Get the file into the buffered reader
        if (args[0] != null) {
            String filename = args[0];
            readFile(filename);
        }

        // Get the Finite State Machine (FSM)
        getFSM();

    }

    public static void readFile(String filename) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))){
        } catch (Exception e) {
            System.err.println("Invalid file");
            e.printStackTrace();
        }
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