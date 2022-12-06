import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Util {

    public static List<String> readAllRuckSacks(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<Character> findCommonCompartmentItems(List<String> ruckSacks) {
        List<Character> result = new ArrayList<>();

        for (String ruckSackItems : ruckSacks) {
            int ruckSackSize = ruckSackItems.length();
            String firstCompartmentItems = ruckSackItems.substring(0, ruckSackSize / 2);
            String secondCompartmentItems = ruckSackItems.substring(ruckSackSize / 2, ruckSackSize);
            Set<Character> commonItems = getCommonItems(firstCompartmentItems, secondCompartmentItems);
            result.addAll(commonItems);
        }

        return result;
    }

    private static Set<Character> getCommonItems(String items1, String items2) {
        Map<Character, Integer> itemCount = new HashMap<>();
        Set<Character> commonItems = new HashSet<>();

        for (char item : items1.toCharArray()) {
            itemCount.put(item, itemCount.getOrDefault(item, 0) + 1);
        }
        for (char item: items2.toCharArray()) {
            if (itemCount.containsKey(item)) {
                commonItems.add(item);
            }
        }

        return commonItems;
    }

    public static int getSumOfPriorities(List<Character> commonCompartmentItems) {
        int sum = 0;

        for (char c : commonCompartmentItems) {
            char lowercaseC = Character.toLowerCase(c);
            if (Character.isUpperCase(c)) {
                sum += 26;
            }
            sum += (lowercaseC - 'a') + 1;
        }

        return sum;
    }
    
    public static List<Character> findBadgesPerGroup(List<String> ruckSacks) {
        List<Character> result = new ArrayList<>();
        int listSize = ruckSacks.size();

        for (int i = 0; i < listSize; i +=3) {
            result.add(findGroupBadge(ruckSacks.subList(i, i + 3)));
        }

        return result;
    }

    private static char findGroupBadge(List<String> ruckSackGroup) {
        Set<Character> firstCommonItems = getCommonItems(ruckSackGroup.get(0), ruckSackGroup.get(1));
        Set<Character> secondCommonItems = getCommonItems(ruckSackGroup.get(0), ruckSackGroup.get(2));
        firstCommonItems.retainAll(secondCommonItems);
        return (char) firstCommonItems.toArray()[0];
    }
}
