package practice_7.zip;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.lang.Integer.min;

/**
 * Created by swanta on 25.08.16.
 */
public class Archivator {
    private static final Scanner scanner = new Scanner(System.in);
    private static File file = setFile();

    public static void main(String[] args) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(file.getName().concat(".zip"));
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            zos.putNextEntry(new ZipEntry(file.getName()));
            System.out.println("zipped successfully");
        }


    }
    private static File setFile() {
        File dir = new File(".");
        final int PRINT_FILES_LIMIT = 5;
        File result;

        String[] files = dir.list();
        int printFilesLimit = min(PRINT_FILES_LIMIT, files.length);
        if (files.length > 0) {
            System.out.println("Some files here:");
            for (int i = 0; i < printFilesLimit; i++) {
                System.out.println(i + ". "+files[i]);
            }
        }
        else {
            throw new IOError(new Throwable("Current directory is empty"));
        }
        do {
            System.out.println("Enter filename or it's number:");
            String filename = scanner.next();
            if (filename.matches(String.format("[0-%d]", printFilesLimit))) {
                filename = files[Integer.valueOf(filename)];
                System.out.println("chosen: " + filename);
            }
            result = new File(filename);
        } while (!result.isFile());
        return result;
    }
}
