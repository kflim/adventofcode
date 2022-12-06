import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {
    private static List<String> lines;
    private static List<Long> completionScores;

    public static void analyzeCorruptedLines() {
        setupData();
        getTotalErrorScore();
    }

    private static void setupData() {
        lines = new ArrayList<>();
        completionScores = new ArrayList<>();
        String currLine;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currLine = br.readLine()) != null) {
                lines.add(currLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getTotalErrorScore() {
        Stack<Character> stack;
        int totalErrorScore = 0;

        for (String line : lines) {
            stack = new Stack<>();
            int length = line.length();
            for (int i = 0; i < length; i++) {
                char currChar = line.charAt(i);
                if (currChar == '(' || currChar == '[' || currChar == '{' || currChar == '<') {
                    stack.push(currChar);
                } else {
                    if (!stack.isEmpty()) {
                        char recentChar = stack.peek();
                        if (!matches(recentChar, currChar)) {
                            totalErrorScore += getPoints(currChar);
                            break;
                        }
                        stack.pop();
                    }
                }
            }
        }

        System.out.println("Total error score: " + totalErrorScore);
    }

    private static int getPoints(char illegalChar) {
        switch (illegalChar) {
        case ')':
            return 3;
        case ']':
            return 57;
        case '}':
            return 1197;
        case '>':
            return 25137;
        default:
            return 0;
        }
    }

    private static boolean matches(char openingChar, char closingChar) {
        switch (openingChar) {
        case '(':
            return closingChar == ')';
        case '[':
            return closingChar == ']';
        case '{':
            return closingChar == '}';
        case '<':
            return closingChar == '>';
        default:
            return false;
        }
    }

    public static void analyzeIncompleteLines() {
        setupData();
        getMedianCompletionScore();
    }

    private static void getMedianCompletionScore() {
        Stack<Character> stack;

        for (String line : lines) {
            stack = new Stack<>();
            int length = line.length();
            boolean isCorrupted = false;
            for (int i = 0; i < length; i++) {
                char currChar = line.charAt(i);
                if (currChar == '(' || currChar == '[' || currChar == '{' || currChar == '<') {
                    stack.push(currChar);
                } else {
                    if (!stack.isEmpty()) {
                        char recentChar = stack.peek();
                        if (!matches(recentChar, currChar)) {
                            isCorrupted = true;
                            break;
                        }
                        stack.pop();
                    }
                }
            }
            if (!isCorrupted) {
                addToCompletionScores(stack);
            }
        }

        completionScores.sort((Long::compare));
        int scoresLength = completionScores.size();
        System.out.println("Middle score: " + completionScores.get(scoresLength / 2));
    }

    private static void addToCompletionScores(Stack<Character> stack) {
        long completionScore = 0;
        while (!stack.isEmpty()) {
            completionScore *= 5;
            char currOpeningChar = stack.pop();
            switch (currOpeningChar) {
            case '(':
                completionScore += 1;
                break;
            case '[':
                completionScore += 2;
                break;
            case '{':
                completionScore += 3;
                break;
            case '<':
                completionScore += 4;
                break;
            }
        }
        completionScores.add(completionScore);
    }

    public static void main(String[] args) {
        analyzeIncompleteLines();
    }
}
