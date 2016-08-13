package home_3_expressions;

/**
 * Created by swanta on 11.06.16.
 */
public class FractionOperating {
    public static void main(String[] args) {
        class Fraction {
            private int numerator;
            private int denominator;


            public Fraction properPart() {
                return substract(intPart());
            }

            public int intPart() {
                return numerator / denominator;
            }

            public Fraction add(Fraction fraction) {
                return new Fraction(numerator * fraction.denominator +
                        fraction.numerator * denominator
                        , denominator * fraction.denominator);
            }

            public Fraction add(int integer) {
                return add(new Fraction(integer, 1));
            }

            public Fraction substract(Fraction fraction) {
                return this.add(
                        new Fraction(-fraction.numerator, fraction.denominator));
            }
            public Fraction substract(int integer) {
                return substract(new Fraction(integer, 1));
            }

            public Fraction divide(Fraction fraction) {
                return new Fraction(fraction.denominator, fraction.numerator).multiply(this);
            }
            public Fraction divide(int integer) {
                return divide(new Fraction(integer, 1));
            }

            public Fraction multiply(Fraction fraction) {
                return new Fraction(numerator * fraction.numerator,
                        denominator * fraction.denominator);
            }
            public Fraction multiply(int integer) {
                return multiply(new Fraction(integer, 1));
            }

            public float toFloat() {
                return numerator / denominator;
            }

            public Fraction(int numerator, int denominator) {
                int gcdValue = gcd(numerator, denominator);
                gcdValue = ((gcdValue == 0) ? 1 : gcdValue);
                this.numerator = numerator / gcdValue;
                this.denominator = denominator / gcdValue;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                Fraction fraction = (Fraction) o;

                return toFloat() == fraction.toFloat();

            }

            @Override
            public String toString() {
                return numerator + "/" + denominator;
            }

            private int gcd(int a, int b) {
                if (b > a) {
                    int tmp = b;
                    b = a;
                    a = tmp;
                }
                // https://ru.wikibooks.org/wiki/%D0%A0%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D0%B8_%D0%B0%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC%D0%BE%D0%B2/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%95%D0%B2%D0%BA%D0%BB%D0%B8%D0%B4%D0%B0#Java
                while (b != 0) {
                    int tmp = a % b;
                    a = b;
                    b = tmp;
                }
                return a;
            }
            private int gcd() {
                return gcd(numerator, denominator);
            }


        }

        Fraction a = new Fraction(4, 8);
        Fraction b = new Fraction(6, 4);
        int i = 2;
        System.out.println(a + " * " + b + " = " + a.multiply(b));
        System.out.println(a + " / " + b + " = " + a.divide(b));
        System.out.println(a + " - " + b + " = " + a.substract(b));
        System.out.println(a + " + " + b + " = " + a.add(b));
        System.out.println(a + " * " + i + " = " + a.multiply(i));
        System.out.println(a + " / " + i + " = " + a.divide(i));
        System.out.println(a + " - " + i + " = " + a.substract(i));
        System.out.println(a + " + " + i + " = " + a.add(i));
        System.out.println(b + " = " + b.intPart() + "+" + b.properPart());
        System.out.println(a + " == " + b + " = " + a.equals(b));
        System.out.println(a + " == " + a + " = " + a.equals(a));

    }
}
