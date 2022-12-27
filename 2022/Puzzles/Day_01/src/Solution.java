import java.util.List;

public class Solution {

    public static void solvePartOne() {
        List<String> caloriesList = Util.readAllNumbers("input.txt");
        List<Integer> listOfCaloriesPerElf = Util.getCaloriesPerElf(caloriesList);
        int highestTotalCalories = Util.getHighestTotalCalories(listOfCaloriesPerElf);
        System.out.println("The highest total calories is : " + highestTotalCalories);
    }

    public static void solvePartTwo() {
        List<String> caloriesList = Util.readAllNumbers("input.txt");
        List<Integer> listOfCaloriesPerElf = Util.getCaloriesPerElf(caloriesList);
        List<Integer> topThreeTotalCalories = Util.getTopThreeHighestCalories(listOfCaloriesPerElf);
        int sumOfCalories = topThreeTotalCalories.stream().mapToInt(Integer::intValue).sum();
        System.out.println("The sum of the three highest total calories is : " + sumOfCalories);
    }

    public static void main(String[] args) {
        // solvePartOne();
        solvePartTwo();
    }
}
