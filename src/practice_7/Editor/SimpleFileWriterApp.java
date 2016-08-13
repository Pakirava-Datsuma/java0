package practice_7.Editor;

import java.io.*;
import java.util.Scanner;

/**
 * Created by swanta on 10.07.16.
 */
public class SimpleFileWriterApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string = "";
        System.out.println("Enter filename:");
        string = scanner.nextLine();
        String filename = string;
        File file = new File(filename);
        try (PrintWriter pw = new PrintWriter(file)) {
            file.createNewFile();
            System.out.println("Enter text (or exit):");
            while (true) {
                string = scanner.nextLine();
                if (string.equals("exit")) {
                    pw.flush();
                    break;
                } else {
                    pw.println(string + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("ошибка доступа к " + filename);
            e.printStackTrace();
        }
        System.out.println("просмотреть? Y/n");
        string = scanner.nextLine();
        if (string.equals("Y")) {
            System.out.println(">>> BEGIN OF FILE " + filename + ">>>");
            try (BufferedReader br = new BufferedReader(new FileReader(file))){
                int item = br.read();
                while (item != -1) {
                    System.out.print((char)item);
                    item = br.read();
                }
            }catch(IOException e){
                System.out.println("ошибка доступа к " + filename);
                e.printStackTrace();
            }
            System.out.println("<<< END OF FILE " + filename + "<<<");
        }
    }
}
