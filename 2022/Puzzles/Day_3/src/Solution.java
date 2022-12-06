import java.util.List;

public class Solution {

    public static void solvePartOne() {
        List<String> ruckSacks = Util.readAllRuckSacks("input.txt");
        List<Character> commonCompartmentItems = Util.findCommonCompartmentItems(ruckSacks);
        int sumOfPriorities = Util.getSumOfPriorities(commonCompartmentItems);
        System.out.println("The sum of the priorities of common compartment items is " + sumOfPriorities);
    }

    public static void solvePartTwo() {
        List<String> ruckSacks = Util.readAllRuckSacks("input.txt");
        List<Character> badges = Util.findBadgesPerGroup(ruckSacks);
        int sumOfPriorities = Util.getSumOfPriorities(badges);
        System.out.println("The sum of the priorities of group badges is " + sumOfPriorities);
    }

    public static void main(String[] args) {
        // solvePartOne();
        solvePartTwo();
    }
}
