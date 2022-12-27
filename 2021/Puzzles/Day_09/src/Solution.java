import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static List<List<Integer>> heightMap;
    private static List<Integer> basinSizes;
    private static boolean[][] visited;
    private static int[][] DELTAS = new int[][]{
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    public static void investigateCaves() {
        setupData();
        findRiskLevelSumOfLowPoints();
    }

    private static void setupData() {
        heightMap = new ArrayList<>();
        String currHeights;
        List<Integer> newHeights;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currHeights = br.readLine()) != null) {
                newHeights = new ArrayList<>();
                while (currHeights.length() > 0) {
                    char currHeightChar = currHeights.charAt(0);
                    int currHeight = Character.getNumericValue(currHeightChar);
                    newHeights.add(currHeight);
                    currHeights = currHeights.substring(1);
                }
                heightMap.add(newHeights);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findRiskLevelSumOfLowPoints() {
        int width = heightMap.size();
        int length = heightMap.get(0).size();
        int sum = 0;

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < length; col++) {
                int currHeight = heightMap.get(row).get(col);
                if (row == 0) {
                    if (checkTopRow(col)) {
                        sum += currHeight + 1;
                    }
                } else if (row == width - 1) {
                    if (checkBottomRow(col)) {
                        sum += currHeight + 1;
                    }
                } else {
                    if (checkMiddleRows(row, col)) {
                        sum += currHeight + 1;
                    }
                }
            }
        }

        System.out.println("Sum of risk levels: " + sum);
    }

    private static boolean checkTopRow(int col) {
        int length = heightMap.get(0).size();
        int currHeight = heightMap.get(0).get(col);
        if (col == 0) {
            int rightHeight = heightMap.get(0).get(col + 1);
            int bottomHeight = heightMap.get(1).get(col);
            return currHeight < rightHeight && currHeight < bottomHeight;
        } else if (col == length - 1) {
            int leftHeight = heightMap.get(0).get(col - 1);
            int bottomHeight = heightMap.get(1).get(col);
            return currHeight < leftHeight && currHeight < bottomHeight;
        } else {
            int leftHeight = heightMap.get(0).get(col - 1);
            int rightHeight = heightMap.get(0).get(col + 1);
            int bottomHeight = heightMap.get(1).get(col);
            return currHeight < leftHeight && currHeight < rightHeight
                    && currHeight < bottomHeight;
        }
    }

    private static boolean checkBottomRow(int col) {
        int width = heightMap.size();
        int length = heightMap.get(0).size();
        int currHeight = heightMap.get(width - 1).get(col);
        if (col == 0) {
            int rightHeight = heightMap.get(width - 1).get(col + 1);
            int topHeight = heightMap.get(width - 2).get(col);
            return currHeight < rightHeight && currHeight < topHeight;
        } else if (col == length - 1) {
            int leftHeight = heightMap.get(width - 1).get(col - 1);
            int topHeight = heightMap.get(width - 2).get(col);
            return currHeight < leftHeight && currHeight < topHeight;
        } else {
            int leftHeight = heightMap.get(width - 1).get(col - 1);
            int rightHeight = heightMap.get(width - 1).get(col + 1);
            int topHeight = heightMap.get(width - 2).get(col);
            return currHeight < leftHeight && currHeight < rightHeight
                    && currHeight < topHeight;
        }
    }

    private static boolean checkMiddleRows(int row, int col) {
        int length = heightMap.get(0).size();
        int currHeight = heightMap.get(row).get(col);
        if (col == 0) {
            int rightHeight = heightMap.get(row).get(col + 1);
            int topHeight = heightMap.get(row - 1).get(col);
            int bottomHeight = heightMap.get(row + 1).get(col);
            return currHeight < rightHeight && currHeight < topHeight
                    && currHeight < bottomHeight;
        } else if (col == length - 1) {
            int leftHeight = heightMap.get(row).get(col - 1);
            int topHeight = heightMap.get(row - 1).get(col);
            int bottomHeight = heightMap.get(row + 1).get(col);
            return currHeight < leftHeight && currHeight < topHeight
                    && currHeight < bottomHeight;
        } else {
            int leftHeight = heightMap.get(row).get(col - 1);
            int rightHeight = heightMap.get(row).get(col + 1);
            int topHeight = heightMap.get(row - 1).get(col);
            int bottomHeight = heightMap.get(row + 1).get(col);
            return currHeight < leftHeight && currHeight < rightHeight
                    && currHeight < topHeight && currHeight < bottomHeight;
        }
    }

    private static void investigateBasins() {
        heightMap = new ArrayList<>();
        basinSizes = new ArrayList<>();
        setupData();
        visited = new boolean[heightMap.size()][heightMap.get(0).size()];
        findBasinSizes();
    }

    private static void findBasinSizes() {
        int width = heightMap.size();
        int length = heightMap.get(0).size();

        for (int row = 0; row < width; row++) {
            for (int col = 0; col < length; col++) {
                if (row == 0) {
                    if (checkTopRow(col)) {
                        basinSizes.add(0);
                        DFS(0, col);
                    }
                } else if (row == width - 1) {
                    if (checkBottomRow(col)) {
                        basinSizes.add(0);
                        DFS(width - 1, col);
                    }
                } else {
                    if (checkMiddleRows(row, col)) {
                        basinSizes.add(0);
                        DFS(row, col);
                    }
                }
            }
        }

        basinSizes.sort((o1, o2) -> o2 - o1);

        List<Integer> topBasinSizes = basinSizes.subList(0, 3);
        int productOfBiggestBasins = 1;
        for (int basinSize : topBasinSizes) {
            productOfBiggestBasins *= basinSize;
        }

        System.out.println("Product of top three basin sizes: " + productOfBiggestBasins);
    }

    private static void DFS(int row, int col) {
        if (row < 0 || row >= heightMap.size() || col < 0 || col >= heightMap.get(0).size()
            || heightMap.get(row).get(col) == 9 || visited[row][col]) {
            return;
        }

        int basinsLength = basinSizes.size();
        basinSizes.set(basinsLength - 1, basinSizes.get(basinsLength - 1) + 1);
        visited[row][col] = true;

        DFS(row - 1, col);
        DFS(row + 1, col);
        DFS(row, col - 1);
        DFS(row, col + 1);
    }

    public static void main(String[] args) {
        investigateBasins();
    }
}
