import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {
    private static List<List<Boolean>> grid;
    private static List<String> foldInstructions;

    public static void solve() {
        setupData();
        findDotsAfterFolds(foldInstructions.size());
        printEachRow(grid);
    }

    private static void setupData() {
        foldInstructions = new ArrayList<>();
        List<List<Integer>> coordinatesList = new ArrayList<>();
        int height = 0;
        int width = 0;
        String currInstruction;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currInstruction = br.readLine()) != null) {
                if (currInstruction.length() > 0) {
                    if (currInstruction.contains("x")) {
                        int equalsIndex = currInstruction.indexOf('=');
                        String axis = currInstruction.substring(equalsIndex + 1);
                        foldInstructions.add("x:" + axis);
                    } else if (currInstruction.contains("y")) {
                        int equalsIndex = currInstruction.indexOf('=');
                        String axis = currInstruction.substring(equalsIndex + 1);
                        foldInstructions.add("y:" + axis);
                    } else {
                        String[] currCoordinates = currInstruction.split(",");
                        List<Integer> newCoordinates = Arrays.stream(currCoordinates)
                                                             .map(Integer::parseInt)
                                                             .collect(Collectors.toList());
                        coordinatesList.add(newCoordinates);
                        height = Math.max(height, newCoordinates.get(0));
                        width = Math.max(width, newCoordinates.get(1));
                    }
                }
            }
            setupGrid(coordinatesList, height, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setupGrid(List<List<Integer>> coordinatesList, int height, int width) {
        grid = new ArrayList<>();
        for (int i = 0; i <= height; i++) {
            List<Boolean> row = new ArrayList<>();
            for (int j = 0; j <= width; j++) {
                row.add(false);
            }
            grid.add(row);
        }
        for (List<Integer> coordinates : coordinatesList) {
            int x = coordinates.get(0);
            int y = coordinates.get(1);
            grid.get(x).set(y, true);
        }
    }

    private static void findDotsAfterFolds(int foldNum) {
        for (int i = 0; i < foldNum; i++) {
            String foldInstruction = foldInstructions.get(i);
            if (foldInstruction.contains("x")) {
                int colonIndex = foldInstruction.indexOf(':');
                String axisString = foldInstruction.substring(colonIndex + 1);
                int axis = Integer.parseInt(axisString);
                foldGridByXAxis(axis);
            } else if (foldInstruction.contains("y")) {
                int colonIndex = foldInstruction.indexOf(':');
                String axisString = foldInstruction.substring(colonIndex + 1);
                int axis = Integer.parseInt(axisString);
                foldGridByYAxis(axis);
            }
        }

        int dotCount = 0;
        for (List<Boolean> row : grid) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                if (row.get(j)) {
                    dotCount++;
                }
            }
        }
        System.out.println("Total dots: " + dotCount);
    }

    private static void printEachRow(List<List<Boolean>> grid) {
        for (List<Boolean> row : grid) {
            List<Character> printRow = new ArrayList<>();
            for (Boolean b : row) {
                if (b) {
                    printRow.add('#');
                } else {
                    printRow.add('.');
                }
            }
            System.out.println(printRow);
        }
    }

    private static void foldGridByXAxis(int axis) {
        int height = grid.size();
        int width = grid.get(0).size();
        for (int i = 0; i < axis; i++) {
            for (int j = 0; j < width; j++) {
                boolean currDotStatus = grid.get(i).get(j);
                boolean otherDotStatus = grid.get(height - i - 1).get(j);
                boolean overlappedDotStatus = currDotStatus || otherDotStatus;
                grid.get(i).set(j, overlappedDotStatus);
            }
        }
        grid = grid.subList(0, axis);
    }

    private static void foldGridByYAxis(int axis) {
        int height = grid.size();
        int width = grid.get(0).size();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < axis; j++) {
                boolean currDotStatus = grid.get(i).get(j);
                boolean otherDotStatus = grid.get(i).get(width - j - 1);
                boolean overlappedDotStatus = currDotStatus || otherDotStatus;
                grid.get(i).set(j, overlappedDotStatus);
            }
            List<Boolean> currRow = grid.get(i);
            grid.set(i, currRow.subList(0, axis));
        }
    }

    public static void main(String[] args) {
        solve();
    }
}
