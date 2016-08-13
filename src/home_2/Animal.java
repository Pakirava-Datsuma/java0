package home_2;

/**
 * Created by swanta on 04.06.16.
 */
public abstract class Animal {
    public String name;
    public String category = "animal";
    public String environment;
    public String favoriteActivity;
    public String currentActivity;

    public Animal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "I am " + category + " " + name +
                ". I like " + favoriteActivity +
                " and I'm " + currentActivity + " now.";
    }

    public void Introduce() {
        System.out.println(toString());
    }
}
