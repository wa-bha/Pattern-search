public class REcompile {

    static String p = "ab*a+cd";
    static int j = 0;
    public static void main(String[] args) {

    }

    public static void expression() {
        term();
    }

    public static void term() {
        factor();
    }

    public static void factor(){
        if(isVocab(p.charAt(j))) {
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