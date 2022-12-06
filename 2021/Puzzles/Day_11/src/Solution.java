import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    private static List<List<Integer>> octopusGrid;
    private static boolean[][] flashed;
    private static final int[][] DELTAS = new int[][]{
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1}, {0, 1},
        {1, -1}, {1, 0}, {1, 1}
    };
    private static int currFlashSum;

    public static void analyzeOctopusFlashes() {
        setupData();
        findTotalFlashes(100);
        printEachRow(octopusGrid);
    }

    private static void setupData() {
        octopusGrid = new ArrayList<>();
        String currRow;
        List<Integer> nextRow;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currRow = br.readLine()) != null) {
                nextRow = new ArrayList<>();
                while (currRow.length() > 0) {
                    char currHeightChar = currRow.charAt(0);
                    int currHeight = Character.getNumericValue(currHeightChar);
                    nextRow.add(currHeight);
                    currRow = currRow.substring(1);
                }
                octopusGrid.add(nextRow);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void findTotalFlashes(int steps) {
        currFlashSum = 0;
        for (int i = 0; i < steps; i++) {
            incrementGrid();
            updateGrid();
        }
        System.out.println("Total flashes: " + currFlashSum);
    }

    private static void incrementGrid() {
        int cols = octopusGrid.get(0).size();
        for (List<Integer> integers : octopusGrid) {
            for (int j = 0; j < cols; j++) {
                integers.set(j, (integers.get(j) + 1) % 10);
            }
        }
    }

    private static void updateGrid() {
        int rows = octopusGrid.size();
        int cols = octopusGrid.get(0).size();
        flashed = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (octopusGrid.get(i).get(j) == 0) {
                    DFS(i, j);
                }
            }
        }
    }

    private static void DFS(int row, int col) {
        if (row < 0 || row >= octopusGrid.size() || col < 0 || col >= octopusGrid.get(0).size()
            || octopusGrid.get(row).get(col) != 0 || flashed[row][col]) {
            return;
        }
        flashed[row][col] = true;
        currFlashSum++;
        for (int[] delta : DELTAS) {
            int nextRow = row + delta[0];
            int nextCol = col + delta[1];
            if (0 <= nextRow && nextRow < octopusGrid.size()
                && 0 <= nextCol && nextCol < octopusGrid.get(0).size()
                && octopusGrid.get(nextRow).get(nextCol) != 0) {
                List<Integer> currRow = octopusGrid.get(nextRow);
                currRow.set(nextCol, (currRow.get(nextCol) + 1) % 10);
                DFS(nextRow, nextCol);
            }
        }
    }

    private static void printEachRow(List<List<Integer>> grid) {
        for (List<Integer> row : grid) {
            System.out.println(row);
        }
    }

    public static void findFirstCompleteFlash() {
        setupData();
        simulateInfiniteStepsTillSuccess();
    }

    private static void simulateInfiniteStepsTillSuccess() {
        currFlashSum = 0;
        int prevFlashSum = 0;
        int rows = octopusGrid.size();
        int cols = octopusGrid.get(0).size();
        int steps = 1;
        while (true) {
            incrementGrid();
            updateGrid();
            if (currFlashSum == prevFlashSum + rows * cols) {
                break;
            }
            prevFlashSum = currFlashSum;
            steps++;
        }
        System.out.println("Number of steps: " + steps);
    }

    public static void main(String[] args) {
        findFirstCompleteFlash();
    }
}
