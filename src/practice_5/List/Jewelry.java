package practice_5.List;

/**
 * Created by swanta on 22.06.16.
 */
public class Jewelry {
    private final String name;
    private double value;
    private int cellNumber;

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public int getCellNumber() {
        return cellNumber;
    }

    public Jewelry(String name, double value, int cellNumber) {
        this.name = name;
        this.value = value;
        this.cellNumber = cellNumber;
    }

}
