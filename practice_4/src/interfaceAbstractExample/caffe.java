package interfaceAbstractExample;

/**
 * Created by swanta on 12.06.16.
 */
public class caffe {
    public static void main(String[] args) {
        abstract class Drink implements Alcoholic {
            @Override
            public double getAlcoholPercentage(){
                return ALCOHOL_PERCENTAGE;
            }

            abstract void printPercentage();
        }
        Drink myDrink = new Drink() {
            @Override
            void printPercentage() {
                System.out.println(getAlcoholPercentage());
            }
        };
        myDrink.printPercentage();
    }

    interface Beverage {
        double ALCOHOL_PERCENTAGE = 5.2;
    }

    interface Alcoholic extends Beverage{
        double getAlcoholPercentage();
        }
}
