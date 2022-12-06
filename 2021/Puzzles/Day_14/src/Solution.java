import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Solution {
    private static Map<String, String[]> pairInsertionRules;
    private static Map<String, Long> pairCounts;
    private static Map<Character, Long> elementCounts;

    public static void solve() {
        setupData();
        repeatPairInsertion(40);
        generateUniqueElementCounts();
        analyzeElementFrequencies();
    }

    private static void setupData() {
        pairInsertionRules = new HashMap<>();
        pairCounts = new HashMap<>();
        elementCounts = new HashMap<>();
        String currLine;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currLine = br.readLine()) != null) {
                if (currLine.length() > 0) {
                    if (!currLine.contains("->")) {
                        for (int i = 0; i < currLine.length() - 1; i++) {
                            String currPair = currLine.substring(i, i + 2);
                            if (!pairCounts.containsKey(currPair)) {
                                pairCounts.put(currPair, 1L);
                            } else {
                                pairCounts.put(currPair, pairCounts.get(currPair) + 1);
                            }
                        }
                    } else {
                        String[] pairInsertionRule = currLine.split("->");
                        String pair = pairInsertionRule[0].trim();
                        String newElement = pairInsertionRule[1].trim();
                        String[] newPairs = new String[2];
                        newPairs[0] = pair.charAt(0) + newElement;
                        newPairs[1] = newElement + pair.charAt(1);
                        pairInsertionRules.put(pair, newPairs);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void repeatPairInsertion(int steps) {
        Map<String, Long> newPairCounts;
        for (int i = 0; i < steps; i++) {
            newPairCounts = new HashMap<>();
            for (Map.Entry<String, Long> currEntry : pairCounts.entrySet()) {
                long currPairCount = currEntry.getValue();
                String[] newPairs = pairInsertionRules.get(currEntry.getKey());
                String firstNewPair = newPairs[0];
                String secondNewPair = newPairs[1];
                if (!newPairCounts.containsKey(firstNewPair)) {
                    newPairCounts.put(firstNewPair, currPairCount);
                } else {
                    newPairCounts.put(firstNewPair, newPairCounts.get(firstNewPair) + currPairCount);
                }
                if (!newPairCounts.containsKey(secondNewPair)) {
                    newPairCounts.put(secondNewPair, currPairCount);
                } else {
                    newPairCounts.put(secondNewPair, newPairCounts.get(secondNewPair) + currPairCount);
                }
            }
            pairCounts = newPairCounts;
        }
    }

    private static void generateUniqueElementCounts() {
        for (Map.Entry<String, Long> pairCount : pairCounts.entrySet()) {
            String currPair = pairCount.getKey();
            char firstChar = currPair.charAt(0);
            char secondChar = currPair.charAt(1);
            long count = pairCount.getValue();
            if (!elementCounts.containsKey(firstChar)) {
                elementCounts.put(firstChar, count);
            } else {
                elementCounts.put(firstChar, elementCounts.get(firstChar) + count);
            }
            if (!elementCounts.containsKey(secondChar)) {
                elementCounts.put(secondChar, count);
            } else {
                elementCounts.put(secondChar, elementCounts.get(secondChar) + count);
            }
        }

        elementCounts.replaceAll((k, v) -> (long) Math.ceil((double) v / 2));
    }

    private static void analyzeElementFrequencies() {
        Map.Entry<Character, Long> highestEntry = null;
        Map.Entry<Character, Long> lowestEntry = null;

        for (Map.Entry<Character, Long> elementCount : elementCounts.entrySet()) {
            if (highestEntry == null || elementCount.getValue() > highestEntry.getValue()) {
                highestEntry = elementCount;
            }
            if (lowestEntry == null || elementCount.getValue() < lowestEntry.getValue()) {
                lowestEntry = elementCount;
            }
        }

        System.out.println("Highest frequency: " + highestEntry.getValue());
        System.out.println("Lowest frequency: " + lowestEntry.getValue());
    }

    public static void main(String[] args) {
        solve();
    }
}
