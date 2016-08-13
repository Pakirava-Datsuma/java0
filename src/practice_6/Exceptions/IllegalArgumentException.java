package practice_6.Exceptions;

/**
 * Created by swanta on 06.07.16.
 */
public class IllegalArgumentException extends Exception{
    public IllegalArgumentException(Person person) {
        super(person.toString());
    }
}
