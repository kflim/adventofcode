import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Solution {
    private static List<String> entries;
    private static String[] connections;

    public static void diagnoseSegmentDisplays() {
        setupData();

        int uniqueDigitCount = 0;
        for (String entry : entries) {
            String[] signalOutputPartitions = entry.split("\\|");
            String[] digitPatterns = signalOutputPartitions[1].trim().split(" ");
            for (String digitPattern : digitPatterns) {
                int length = digitPattern.length();
                if (length == 2 || length == 4 || length == 3 || length == 7) {
                    uniqueDigitCount++;
                }
            }
        }

        System.out.println("Number of unique digits: " + uniqueDigitCount);
    }

    private static void setupData() {
        entries = new ArrayList<>();
        String currEntry = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currEntry = br.readLine()) != null) {
                entries.add(currEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseEntries() {
        setupData();

        int total = 0;
        for (String entry : entries) {
            connections = new String[10];
            String[] signalOutputPartitions = entry.split("\\|");
            String[] signalPatterns = signalOutputPartitions[0].trim().split(" ");
            String[] outputPatterns = signalOutputPartitions[1].trim().split(" ");

            // Analyze the signal patterns, starting from the strictest number of segments
            findUniqueSignalPatterns(signalPatterns);
            findSignalPatternsWithSixSegments(signalPatterns);
            findSignalPatternsWithFiveSegments(signalPatterns);

            // Determine corresponding digit to output patterns and add to total
            StringBuilder result = new StringBuilder();
            for (String output : outputPatterns) {
                for (int i = 0; i < connections.length; i++) {
                    if (checkAnagram(output, connections[i])) {
                        result.append(i);
                        break;
                    }
                }
            }
            int actualOutput = Integer.parseInt(result.toString());
            total += actualOutput;
        }

        System.out.println("Total sum: " + total);
    }

    private static void findUniqueSignalPatterns(String[] signalPatterns) {
        for (String s : signalPatterns) {
            if (s.length() == 2) {
                connections[1] = s;
            } else if (s.length() == 4) {
                connections[4] = s;
            } else if (s.length() == 3) {
                connections[7] = s;
            } else if (s.length() == 7) {
                connections[8] = s;
            }
        }
    }

    private static void findSignalPatternsWithSixSegments(String[] signalPatterns) {
        Set<Character> signalFourChars = connections[4].chars()
                                                       .mapToObj(e -> (char)e)
                                                       .collect(Collectors.toSet());
        Set<Character> signalOneChars = connections[1].chars()
                                                       .mapToObj(e -> (char)e)
                                                       .collect(Collectors.toSet());
        for (String s : signalPatterns) {
            Set<Character> sCharacters = s.chars()
                                          .mapToObj(e -> (char)e)
                                          .collect(Collectors.toSet());
            if (s.length() == 6) {
                if (sCharacters.containsAll(signalFourChars)) {
                    connections[9] = s;
                } else if (sCharacters.containsAll(signalOneChars)) {
                    connections[0] = s;
                } else {
                    connections[6] = s;
                }
            }
        }
    }

    private static void findSignalPatternsWithFiveSegments(String[] signalPatterns) {
        Set<Character> signalSixChars = connections[6].chars()
                                                       .mapToObj(e -> (char)e)
                                                       .collect(Collectors.toSet());
        Set<Character> signalNineChars = connections[9].chars()
                                                      .mapToObj(e -> (char)e)
                                                      .collect(Collectors.toSet());
        for (String s : signalPatterns) {
            Set<Character> sCharacters = s.chars()
                                          .mapToObj(e -> (char)e)
                                          .collect(Collectors.toSet());
            if (s.length() == 5) {
                if (signalSixChars.containsAll(sCharacters)) {
                    connections[5] = s;
                } else if (signalNineChars.containsAll(sCharacters)) {
                    connections[3] = s;
                } else {
                    connections[2] = s;
                }
            }
        }
    }

    private static boolean checkAnagram(String str1, String str2) {

        if (str1.length() != str2.length())
            return false;

        char[] a = str1.toCharArray();
        char[] b = str2.toCharArray();

        Arrays.sort(a);
        Arrays.sort(b);

        return Arrays.equals(a, b);
    }

    public static void main(String[] args) {
        parseEntries();
    }
}
