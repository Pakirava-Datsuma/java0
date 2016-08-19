package home_1;

import org.omg.CORBA.StringHolder;

/**
 * Created by swanta on 21.05.16.
 */
public class FinderOfLongestChainOfZeroes {
    private static final char SEEKING_CHAR = '0';
    private static final char NONSEEKING_CHAR = '1';

    public static void main(String[] args) {
        String binaryChain = args[0];
        System.out.println(binaryChain);

        findLongestChainViaSeekingMethod(binaryChain);
        findLongestChainViaReverseSeeking(binaryChain);
        findLongestChainViaStringSplit(binaryChain);
    }

    private static void printSubchainFound (int start, int end) {
        final String PRINT_FORMAT_OK =
                "Самая длинная подстрока символов %c начинается с позиции %d " +
                        "и заканчивается позицией %d\n";
        final String PRINT_FORMAT_FAIL = "Символов %c в строке не обнаружено\n";
        if (start >= 0 && end >= 0) {
            System.out.println(String.format(PRINT_FORMAT_OK, SEEKING_CHAR, start, end));
        } else {
            System.out.println(String.format(PRINT_FORMAT_FAIL, SEEKING_CHAR));
        }
    }

    private static void findLongestChainViaSeekingMethod(String binaryChain) {
        System.out.println("Метод прямого перебора символов:");
        int chainLength = binaryChain.length();
        int currentCharIndex = 0;
        char currentChar;
        int subchainLength = 0;
        int subchainStartIndex = 0;
        int subchainEndIndex = 0;
        int maxSubchainLength = 0;
        int maxSubchainStartIndex = -1; // значение на случай, если не найдем символов вообще
        int maxSubchainEndIndex = -1;
        for (currentCharIndex = 0; currentCharIndex < chainLength; currentCharIndex++) {
            currentChar = binaryChain.charAt(currentCharIndex);
            if (currentChar == SEEKING_CHAR) { //  символ обнаружен
            //  сдвинем конец последовательности
                subchainEndIndex = currentCharIndex;
            //  и проверим, не стала ли она самой длинной
                subchainLength = subchainEndIndex - subchainStartIndex + 1;
                if (subchainLength > maxSubchainLength) {
                //  если найденная последовательность самая длинная, запомним её
                    maxSubchainLength = subchainLength;
                    maxSubchainStartIndex = subchainStartIndex;
                    maxSubchainEndIndex = subchainEndIndex;
                }
            } else { //  символ не обнаружен
            //  сдвинем начало следующей последовательности
                subchainStartIndex = currentCharIndex + 1;
            }
        }
        printSubchainFound(maxSubchainStartIndex, maxSubchainEndIndex);
    }

    private static void findLongestChainViaStringSplit(String binaryChain) {
        System.out.println("Метод разбиения целевой строки на подстроки:");
        final String splitRegexp = NONSEEKING_CHAR + "+";
        String maxSubchain = "";
        int maxSubchainLength = 0;

        String[] subchains = binaryChain.split(splitRegexp);

        for (String subchain: subchains
             ) {
            if (subchain.length() > maxSubchainLength) {
                maxSubchainLength = subchain.length();
                maxSubchain = subchain;
            }
        }
        int maxSubchainStart = binaryChain.indexOf(maxSubchain);
        int maxSubchainEnd = maxSubchainStart + maxSubchainLength - 1;
        printSubchainFound(maxSubchainStart, maxSubchainEnd);
    }

    private static void findLongestChainViaReverseSeeking(String binaryChain) {
        System.out.println("Метод подбора искомой строки:");
        int subchainStart = -1;
        int subchainLength = binaryChain.length() + 1;
        do {
            subchainLength --;
            StringBuilder subchain = new StringBuilder();
            for (int j = 0; j < subchainLength; j++) {
                subchain.append(SEEKING_CHAR);
            }
            subchainStart = binaryChain.indexOf(subchain.toString());
        } while (subchainLength > 1 && subchainStart < 0);
        int subchainEnd = subchainStart + subchainLength - 1;
        printSubchainFound(subchainStart, subchainEnd);
    }

}
