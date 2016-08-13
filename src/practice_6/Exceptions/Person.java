package practice_6.Exceptions;

/**
 * Created by swanta on 06.07.16.
 */
public class Person {
    String firstName;
    String lastName;
    int year;

    private Person(String firstName, String lastName, int year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
    }

    public static Person createPerson(String firstName, String lastName, int year)
    throws IllegalArgumentException
    {
        String patternMatching = "[-A-z]+";
        Person person = new Person(firstName, lastName, year);
        if (!(firstName.matches(patternMatching)
                && lastName.matches(patternMatching)
                && year < 2014)) {
            throw new IllegalArgumentException(person);
        }
        return person;
    }
    @Override
    public String toString() {
        return String.format("%s %s %d", firstName, lastName, year);
    }
}
