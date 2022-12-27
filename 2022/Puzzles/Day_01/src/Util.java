import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Util {

    public static List<String> readAllNumbers(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<Integer> getCaloriesPerElf(List<String> caloriesList) {
        List<Integer> result = new ArrayList<>();
        int currentTotalCalories = 0;

        for (String calories : caloriesList) {
            if (calories.length() == 0) {
                result.add(currentTotalCalories);
                currentTotalCalories = 0;
            } else {
                currentTotalCalories += Integer.parseInt(calories);
            }
        }

        result.add(currentTotalCalories);
        return result;
    }

    public static int getHighestTotalCalories(List<Integer> listOfCaloriesPerElf) {
        int highestTotalCalories = Integer.MIN_VALUE;

        for (int totalCalories : listOfCaloriesPerElf) {
            highestTotalCalories = Math.max(highestTotalCalories, totalCalories);
        }

        return highestTotalCalories;
    }

    public static List<Integer> getTopThreeHighestCalories(List<Integer> listOfCaloriesPerElf) {
        listOfCaloriesPerElf.sort(Collections.reverseOrder());
        return listOfCaloriesPerElf.subList(0, 3);
    }

}
