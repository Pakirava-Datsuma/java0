package practice_6.Exceptions;

/**
 * Created by swanta on 06.07.16.
 */
public class PersonaCreatorApp {
    public static void main(String[] args) {
        try {
            System.out.println(Person.createPerson("Julian", "Caesar", -100).toString());
            System.out.println(Person.createPerson("Terminator", "Infiltrator", 2029).toString());
        }catch (IllegalArgumentException e) {
            System.out.println("Bad data in human: " + e.getMessage());
        }
    }
}
