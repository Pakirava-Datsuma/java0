package practice_7.Encryption;

/**
 * Created by swanta on 10.07.16.
 */
public class EncryptApp {

    public static void main(String[] args) {
        String text;
        View view = new View();
        Enigma enigma = new Enigma(view);
        Streamer streamer = new Streamer(view);

        boolean exit = false;
        while (!exit) {
            String string = view.askUser("1 - decode, 2 - encode, smthg else - exit");
            switch (string) {
                case "1":
                    byte[] bytesFromFile = streamer.readFile();
                    if (bytesFromFile != null) {
                        text = enigma.decryptBytes(bytesFromFile);
                        view.println(">>> BEGIN OF FILE " + streamer.getFilename() + ">>>");
                        view.println(text);
                        view.println("<<< END OF FILE " + streamer.getFilename() + "<<<");
                    }
                    break;
                case "2":
                    text = view.composeText();
                    streamer.writeFile(enigma.encryptText(text));
                    break;
                default:
                    exit = true;
                    break;
            }
            exit = !exit ? view.askUserDefaultAnswer("Еще работка? y/", View.ANSWER_NO) : exit;
        }
    }
}
