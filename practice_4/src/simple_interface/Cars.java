package simple_interface;

/**
 * Created by swanta on 12.06.16.
 */
public class Cars {
    public static void main(String[] args) {
        FastCar ferrari = new FastCar(500);
        ThoughCar hotRoad18Wheels = new ThoughCar(600, 5000);
        printWeight(ferrari);
        printWeight(hotRoad18Wheels);

    }

    public static void printWeight (Lifter car) {
        System.out.println(car.getWeight());
    }

    public interface Lifter {
        int getWeight();
    }

    public static class FastCar implements Lifter {
        private final int WEIGHT;

        public FastCar(int weight) {
            this.WEIGHT = weight;
        }

        public int getWeight() {
            return WEIGHT;
        }

    }

    public static class ThoughCar implements Lifter {
        private final int TRACK_WEIGHT;
        private final int TRAILER_WEIGHT;

        public ThoughCar(int trackWeight, int trailerWeight) {
            this.TRACK_WEIGHT = trackWeight;
            this.TRAILER_WEIGHT = trailerWeight;
        }

        public int getWeight() {
            return TRACK_WEIGHT + TRAILER_WEIGHT;
        }

    }
}
