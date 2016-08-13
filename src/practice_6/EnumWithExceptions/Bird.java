package practice_6.EnumWithExceptions;

/**
 * Created by swanta on 03.07.16.
 */
public enum Bird {
    Ostrich("I like to run and to dig in sand.") {
        @Override
        public void fly() throws BirdsFlyException{
            cantFly();
        }
    },
    Swalow("I like to fly very low just before it rains."),
    Pinguin("I like to be a Linux logo.") {
        @Override
        public void fly() throws BirdsFlyException{
            cantFly();
        }
    };
    private String activityMessage;

    Bird(String activityMessage){
        this.activityMessage = activityMessage;
    }

    public void fly() throws BirdsFlyException{
        System.out.println("I'm " + name() + ". " + activityMessage);
    }

    protected void cantFly()throws BirdsFlyException{
        throw new BirdsFlyException(this);
    }

    public String getActivity() {
        return activityMessage;
    }
}
