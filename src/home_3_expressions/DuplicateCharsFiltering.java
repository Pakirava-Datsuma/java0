package home_3_expressions;

/**
 * Created by swanta on 11.06.16.
 */
public class DuplicateCharsFiltering {
    public static void main(String[] args) {
        String testString = new String("Hello! My name is Mitya. " +
                "Let me introduce myself. Oh, man... I'm late. See you next time!");
        char testChar = '*';
        System.out.println("Unfiltered string:\n" + testString);
        System.out.println("\nFiltered string:\n" + filterDuplicateChars(testString));
        System.out.println("\nCheck for one-char-length string: "+testChar);
        System.out.println("Have to be the same: "+ filterDuplicateChars(""+testChar));
    }

    static String filterDuplicateChars ( String string) {
        StringBuilder filteredString = new StringBuilder(string);
        if (string.length()< 2) //nothing to compare
            return string;
        else {
            //last character no need to compare
            for (int i = 0; i < (filteredString.length()- 1); i++) {
                //compare from next character
                for (int j = i + 1; j < filteredString.length(); ) {
                    if (filteredString.charAt(i) == filteredString.charAt(j))
                        filteredString.deleteCharAt(j);
                        //don't increment j because j+1 char became j
                        // after deleting current char
                    else
                        j++;
                }
            }
        }
        return filteredString.toString();
    }
}
