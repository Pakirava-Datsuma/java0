package practice_7.Encryption;

import java.util.Scanner;

/**
 * Created by swanta on 10.07.16.
 */
public class View{
    private static final String ANSWER_EXIT = "exit";
    protected static final String ANSWER_YES = "Y";
    protected static final String ANSWER_NO = "N";

    private Scanner scanner = new Scanner(System.in);

    protected String composeText() {
        final String ANSWER = ANSWER_EXIT;
        println("Type lines of your message (type line '"
                + ANSWER
                + "' or empty one when finished):");
        StringBuffer buffer = new StringBuffer();
        while (true) {
            String string = readln();
            if (string.equals(ANSWER) || string.isEmpty()) {
                break;
            } else {
                buffer.append(string);
            }
        }
        return buffer.toString();
    }

    public boolean askUserDefaultAnswer(String question, String defaultAnswer) {
        System.out.println(question+defaultAnswer);
        String answer = scanner.nextLine();
        return  answer.equals(defaultAnswer)||
                answer.isEmpty();
    }

    public String askUser(String s) {
        println(s);
        return readln();
    }


    public void println(String s) {
        System.out.println(s);
    }

    private String readln() {
        return scanner.nextLine();
    }
}
