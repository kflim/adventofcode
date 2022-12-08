import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Util {

    public static int[][] DELTAS = new int[][]{
        {0, 1}, {1, 0}, {0, -1}, {-1, 0}
    };

    public static List<List<Integer>> readGrid(String filepath) {
        try {
            List<String> gridRows = Files.readAllLines(Paths.get(filepath));
            return gridRows.stream()
                           .map(row -> Arrays.asList(row.split("")))
                           .map(strings -> strings.stream()
                                                  .map(Integer::parseInt)
                                                  .collect(Collectors.toList()))
                           .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static int[][] deepCopyIntArr(int[][] arr) {
        if (arr == null) {
            return null;
        }

        int[][] result = new int[arr.length][];
        for (int i = 0; i < arr.length; i++) {
            result[i] = Arrays.copyOf(arr[i], arr[i].length);
        }

        return result;
    }

    public static boolean[][] deepCopyBoolArr(boolean[][] original) {
        if (original == null) {
            return null;
        }

        boolean[][] result = new boolean[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }
}
