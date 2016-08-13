package home_3_expressions;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by swanta on 08.06.16.
 */


public class Arrays {
    static final int RANDOM_BOUND = 10;

    public static void main(String[] args) {
        class RandomArray {
            private int[] array;

            public double getArrayMedian() {
                int[] arrayMedianValueIndex = {0, 0};
                arrayMedianValueIndex[0] = array.length / 2;
                arrayMedianValueIndex[1] = array.length / 2 + array.length % 2;
                int[] arrayMedianValue = {0, 0};
                outer: for (int i = 0; i < array.length; i++) {
                    int distanceFromMedian = 0;
                    inner: for (int j = 0; j < array.length; j++) {
                        // equal numbers have to be compared by i.
                        if ((array[i] > array[j]) || ((array[i] == array[j]) && (i > j)))
                            distanceFromMedian ++;
                        else if ((array[i] < array[j]) || ((array[i] == array[j]) && (i < j)))
                            distanceFromMedian --;
                    }
                    System.out.print("Number: " + array[i]);
                    System.out.println("  has position from median: " + distanceFromMedian);

                    switch (distanceFromMedian) {
                        case -1:
                            //number is to the left from median value
                            arrayMedianValue[0] = array[i];
                            break;
                        case 0:
                            //this is median value
                            arrayMedianValue[0] = array[i];
                            arrayMedianValue[1] = array[i];
                            break outer;
                        case 1:
                            //number is to the right from median value
                            arrayMedianValue[1] = array[i];
                            break;
                        default:
                            //number is too far from median value
                            break;
                    }
                }
                return (arrayMedianValue[0]+arrayMedianValue[1])/2.0;
            }

            public RandomArray (int arrayLength) {
                array = new int[arrayLength];
                Random random;
                random = new Random();
                for (int i = 0; i < arrayLength; i++)
                    array[i] = random.nextInt(RANDOM_BOUND);
            }

            //for testing purpose
            public RandomArray (int[] array) {
                this.array = new int[array.length];
                this.array = array;
            }

            @Override
            public String toString() {
                StringBuilder string = new StringBuilder("{");
                for (int i = 0; i < array.length; i++)
                    string.append(" "+ array[i]);
                string.append(" }");
                return string.toString();
            }

            public String getUniqueNumbers() {
                StringBuilder string = new StringBuilder("{");
                outer: for (int i = 0; i < array.length; i++) {
                    inner:
                    for (int j = 0; j < array.length; j++) {
                        if (i == j)
                            continue inner;
                        else if (array[i] == array[j])
                            continue outer;
                    }
                    string.append(" " + array[i]);
                }
                string.append(" }");
                return string.toString();
            }

        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array length:");
        int arrayLength = scanner.nextInt();
        RandomArray array = new RandomArray(arrayLength);
        System.out.println("array: " + array);
        System.out.println("array median: " + array.getArrayMedian());
        System.out.println("array unique numbers: " + array.getUniqueNumbers());


    }



}
