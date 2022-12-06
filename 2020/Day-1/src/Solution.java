import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Solution {

    public static void solvePartOne() {
        List<String> numberStrings = Util.readAllNumbers("input.txt");
        List<Integer> numbers = numberStrings.stream().map(Integer::parseInt).collect(Collectors.toList());
        Map<Integer, Integer> map = new HashMap<>();
        int[] pair = new int[2];
        int target = 2020;

        for (int i = 0; i < numbers.size(); i++) {
            int currNum = numbers.get(i);
            if (map.containsKey(target - currNum)) {
                pair[0] = map.get(target - currNum);
                pair[1] = i;
                break;
            }
            map.put(currNum, i);
        }

        int result = numbers.get(pair[0]) * numbers.get(pair[1]);
        System.out.println("Answer is : " + result);
    }

    public static void solvePartTwo() {
        List<String> numberStrings = Util.readAllNumbers("input.txt");
        List<Integer> numbers = numberStrings.stream()
                                             .map(Integer::parseInt)
                                             .sorted()
                                             .collect(Collectors.toList());
        List<Integer> ans = new ArrayList<>();
        int target = 2020;

        outerLoop:
        for (int i = 0; i < numbers.size(); i++) {
            if (i == 0 || !Objects.equals(numbers.get(i), numbers.get(i - 1))) {
                int low = i + 1, high = numbers.size() - 1, sum = target - numbers.get(i);
                while (low < high) {
                    if (numbers.get(low) + numbers.get(high) == sum) {
                        ans = Arrays.asList(numbers.get(i), numbers.get(low), numbers.get(high));
                        break outerLoop;
                    } else if (numbers.get(low) + numbers.get(high) < sum) {
                        low++;
                    } else {
                        high--;
                    }
                }
            }
        }

        if (ans.size() < 3) {
            System.out.println("There is no answer");
        }

        int result = ans.get(0) * ans.get(1) * ans.get(2);
        System.out.printf("Answer is : %d\n", result);
    }

    public static void main(String[] args) {
        solvePartTwo();
    }
}
