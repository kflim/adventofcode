import java.util.List;

public class Solution {

    public static void solvePartOne() throws Exception {
        List<String> strategyGuide = Util.readStrategyGuide("input.txt");
        int totalScore = Util.getTotalScoreFirstTime(strategyGuide);
        System.out.println("The total score according to the guide is " + totalScore);
    }

    public static void solvePartTwo() throws Exception {
        List<String> strategyGuide = Util.readStrategyGuide("input.txt");
        int totalScore = Util.getTotalScoreSecondTime(strategyGuide);
        System.out.println("The total score according to the guide is " + totalScore);
    }

    public static void main(String[] args) throws Exception {
        // solvePartOne();
        solvePartTwo();
    }
}
