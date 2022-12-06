import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Solution {
    private static Map<Integer, Integer> positionCounts;
    private static List<String> binaryStrings;

    public static void producePowerConsumption() {
        positionCounts = new HashMap<>();
        getMostFrequentPosBits();

        int gammaRate = 0;
        for (int i = 0; i < positionCounts.size(); i++) {
            gammaRate += (positionCounts.get(i) > 0 ? 1 : 0) * Math.pow(2, i);
        }

        int epsilonRate = 0;
        for (int i = 0; i < positionCounts.size(); i++) {
            epsilonRate += (positionCounts.get(i) > 0 ? 0 : 1) * Math.pow(2, i);
        }

        System.out.println("Gamma rate: " + gammaRate);
        System.out.println("Epsilon rate: " + epsilonRate);
        System.out.println("Power Consumption: " + gammaRate * epsilonRate);
    }

    private static void getMostFrequentPosBits() {
        String currBinString = "";
        int currPos = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currBinString = br.readLine()) != null) {
                while (!currBinString.equals("")) {
                    if (!positionCounts.containsKey(currPos)) {
                        positionCounts.put(currPos, 0);
                    }
                    if (currBinString.endsWith("1")) {
                        positionCounts.put(currPos, positionCounts.get(currPos) + 1);
                    } else {
                        positionCounts.put(currPos, positionCounts.get(currPos) - 1);
                    }
                    currBinString = currBinString.substring(0, currBinString.length() - 1);
                    currPos += 1;
                }
                currPos = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void produceLifeSupportRating() {
        binaryStrings = new ArrayList<>();
        getBinaryStrings();

        List<String> tempOne = new ArrayList<>(binaryStrings);
        List<String> tempTwo = new ArrayList<>(binaryStrings);

        int stringSize = tempOne.get(0).length();
        String binOxygenRating = filterList(tempOne, 0, stringSize);
        String binCO2Rating = filterList(tempTwo, 1, stringSize);

        int oxygenRating = 0;
        for (int i = 0; i < stringSize; i++) {
            oxygenRating += (binOxygenRating.charAt(stringSize - i - 1) - '0' == 1 ? 1 : 0) * Math.pow(2, i);
        }

        int co2Rating = 0;
        for (int i = 0; i < stringSize; i++) {
            co2Rating += (binCO2Rating.charAt(stringSize - i - 1) - '0' == 1 ? 1 : 0) * Math.pow(2, i);
        }

        System.out.println("Oxygen Rating: " + oxygenRating);
        System.out.println("CO2 Rating: " + co2Rating);
        System.out.println("Life Support Rating: " + oxygenRating * co2Rating);
    }

    private static void getBinaryStrings() {
        String currBinString = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currBinString = br.readLine()) != null) {
                binaryStrings.add(currBinString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String filterList(List<String> binaryStrings, int bitCriteria, int stringSize) {
        if (bitCriteria == 0) {
            return filterListForOxygen(binaryStrings, stringSize - 1, stringSize);
        } else if (bitCriteria == 1) {
            return filterListForCO2(binaryStrings, stringSize - 1, stringSize);
        }
        return "";
    }

    private static String filterListForOxygen(List<String> binaryStrings, int offset, int stringSize) {
        if (offset < 0 || binaryStrings.size() == 1) {
            return binaryStrings.get(0);
        }

        int balance = 0;
        for (String s : binaryStrings) {
            if (s.charAt(stringSize - offset - 1) == '1') {
                balance++;
            } else {
                balance--;
            }
        }
        int mostFrequentBitAtOffset = balance >= 0 ? 1 : 0;

        binaryStrings = binaryStrings
                .stream()
                .filter(s -> (s.charAt(stringSize - offset - 1) - '0') == mostFrequentBitAtOffset)
                .collect(Collectors.toList());

        return filterListForOxygen(binaryStrings, offset - 1, stringSize);
    }

    private static String filterListForCO2(List<String> binaryStrings, int offset, int stringSize) {
        if (offset < 0 || binaryStrings.size() == 1) {
            return binaryStrings.get(0);
        }

        int balance = 0;
        for (String s : binaryStrings) {
            if (s.charAt(stringSize - offset - 1) == '1') {
                balance++;
            } else {
                balance--;
            }
        }
        int leastFrequentBitAtOffset = balance >= 0 ? 0 : 1;

        binaryStrings = binaryStrings
                .stream()
                .filter(s -> (s.charAt(stringSize - offset - 1) - '0') == leastFrequentBitAtOffset)
                .collect(Collectors.toList());

        return filterListForCO2(binaryStrings, offset - 1, stringSize);
    }

    public static void main(String[] args) {
        producePowerConsumption();
        produceLifeSupportRating();
    }
}
