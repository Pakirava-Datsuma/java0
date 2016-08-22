package practice_7.Goal;

import java.io.*;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by swanta on 21.08.16.
 */
public class GoalKeeper {
    static final String GOAL_FILE_EXT = ".goal";
    static File dir = new File("goals"){{
        if ((!exists()) ) {
//            try {
            mkdir();
            System.out.println(dir.getAbsolutePath() + " created.");
//            } catch (IOException e) {
//                e.printStackTrace();
        }
        if (!isDirectory()) {
            System.out.println("Please, clear path for app data directory: " + getAbsolutePath());
            System.exit(1);
//            }
        }
    }};
    static Set<Goal> goals = new HashSet<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        loadGoalsFromFiles();
//        try {
//            goals.add(new Goal("помыть посуду", "24.08.2016"));
//            goals.add(new Goal("получить сертификат", "06.09.2016"));
//            goals.add(new Goal("Java 1", "25.08.2016"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        while (!exit) {
            System.out.println("\n1 - New Goal...");
            if (!goals.isEmpty()) {
                System.out.println("2 - List goals");
                System.out.println("3 - Write goals to file...");
            }
            System.out.println("0 - Exit");

            while (!scanner.hasNextInt());
            int userInputInteger = scanner.nextInt();
            switch (userInputInteger) {
                case 1:
                    addGoalFromUser();
                    break;
                case 2:
                    if (!goals.isEmpty())
                        for (Goal goal : goals)
                            System.out.println(
//                                    "---------\n" +
                                    goal.toString() +
                                    "\n---------");
                    break;
                case 3:
                    if (!goals.isEmpty()) writeGoalsToFile();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    break;
            }

        }
    }

    private static void addGoalFromUser() {
        System.out.println("введите новую цель:");
        String description = scanner.next();
        Goal newGoal = null;
        do {
            try {
                System.out.println("введите её дату в формате " + Goal.DATE_FORMAT);
                String date = scanner.next();
                newGoal = new Goal(description, date);
            } catch (ParseException e) {
            }
        } while (!goals.add(newGoal));
    }

    private static void loadGoalsFromFiles() {
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (name.endsWith(GOAL_FILE_EXT));
            }};
            try {
                for (File file : dir.listFiles(filter))
                    loadGoalsFromFile(file);
            } catch (NullPointerException e) {
                System.out.println("Saved data not found in folder " + dir.getAbsolutePath());
            }
    }

    private static void loadGoalsFromFile(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Set<Goal> newGoals = (Set<Goal>)ois.readObject();
            System.out.println("\n" + file.getName() + " loaded:");
            for (Goal newGoal : newGoals) {
                if (goals.add(newGoal)) {
                    newGoal.countDaysLeft();
                    System.out.println(newGoal.toString());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeGoalsToFile() {
        File newFile;
        boolean exit = false;
        do {
            System.out.println("Enter filename:");
            String userInputString = scanner.next();
            if (userInputString.isEmpty()) {
                System.out.println("пустая строка. отмена.");
                exit = true;
            } else {
                newFile = new File(dir.getAbsolutePath(), userInputString + GOAL_FILE_EXT);

                try (FileOutputStream fos = new FileOutputStream(newFile);
                     ObjectOutputStream ous = new ObjectOutputStream(fos)) {

                    ous.writeObject(goals);
                    System.out.println(newFile.getAbsolutePath() + " created");
                    exit = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } while (!exit);
    }
}
