package practice_6.EnumWithExceptions;

/**
 * Created by swanta on 03.07.16.
 */
public class BirdsFlyException extends Exception{
    public BirdsFlyException(Bird bird) {
        super("Hey dude! I'm "+bird.name() + "! I can't fly. " + bird.getActivity());
    }
}
