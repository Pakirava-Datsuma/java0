package practice_5.List;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by swanta on 22.06.16.
 */
public class JewelryIterator {
    public static void main(String[] args) {
        List<Jewelry> jewelryList = new LinkedList<Jewelry>() {
            public void printAll() {
                System.out.println("Содержимое коллекции");
                for (Jewelry jewelry :
                        this) {
                    System.out.println(String.format("%s costs %f in cell# %d"
                            ,jewelry.getName(), jewelry.getValue(), jewelry.getCellNumber()));
                }
            }
        };
        jewelryList.add(new Jewelry("Gold Ring", 4362.43, 1));
        jewelryList.add(new Jewelry("Silver Bracelet", 33242.63, 1));
        jewelryList.add(new Jewelry("Platinum Watch", 59999.99, 2));

        //jewelryList.printAll();
        printJewelry(jewelryList, 1);
        printJewelry2(jewelryList, 2);
    }

    public static void printJewelry (List<Jewelry> collection, int cellNumber) {
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i).getCellNumber() == cellNumber) {
                System.out.println("Под номером "+cellNumber
                        + " лежит " + collection.get(i).getName());
            }
        }
    }

    public static void printJewelry2 (List<Jewelry> collection, int cellNumber) {
        Iterator<Jewelry> iterator = collection.iterator();
        for (;iterator.hasNext();) {
            Jewelry jewelry = iterator.next();
            if (jewelry.getCellNumber() == cellNumber) {
                System.out.println("Под номером "+cellNumber
                        + " лежит " + jewelry.getName());
            }
        }
    }
}
