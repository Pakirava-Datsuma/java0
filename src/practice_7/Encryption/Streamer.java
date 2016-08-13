package practice_7.Encryption;

import java.io.*;

import static java.lang.Integer.min;

/**
 * Created by swanta on 10.07.16.
 */
public class Streamer {
    public static final String WORKING_DIR = ".";
    public static final int PRINT_FILES_LIMIT = 5;
    private String filename;
    private File file;
    private final View view;
    public Streamer(View view) {
        this.view = view;
    }
    protected byte[] readFile() {
        byte[] buffer = {};
        if (setFile() && file.length() > 0) {
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                buffer = new byte[(int)file.length()];
                bis.read(buffer);
            } catch (IOException e) {
                view.println("File "+file.getName()+" not available");
            }
            return buffer;
        }
        return null;
    }
    protected void writeFile(byte[] bytes) {
        while (!setFile()) {
            if (!view.askUserDefaultAnswer("You will LOSE ALL your text if continue." +
                    "Continue? y/", View.ANSWER_NO)) {
                return;
            }
        }
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(bytes);
        } catch (IOException e) {
            view.println("ошибка записи в " + filename);
            e.printStackTrace();
        }

    }
    private boolean setFile() {
//        try {
        File dir = new File(WORKING_DIR);
        String[] files = dir.list();
        int printFilesLimit = min(PRINT_FILES_LIMIT, files.length);
        if (files.length > 0) {
            System.out.println("Some files here:");
            for (int i = 0; i < printFilesLimit; i++) {
                view.println(i + ". "+files[i]);
            }
        }
//        } catch (IOException e) {
//            System.out.println("Internal error while printing files");
//        }
        filename = view.askUser("Enter filename or it's number:");
        if (filename.isEmpty()) {
            return false;
        }
        if (filename.matches(String.format("[0-%d]", printFilesLimit))) {
            filename = files[Integer.valueOf(filename)];
            System.out.println(filename);
        }
        file = new File(filename);
        return true;
    }

    public String getFilename() {
        return filename;
    }
}
