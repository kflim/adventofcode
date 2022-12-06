import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Solution {
    private static List<List<Integer>> riskGrid;
    private static int[][] riskLevels;
    private static boolean[][] visited;
    private static PriorityQueue<Node> priorityQueue;
    private static final int[][] DELTAS = new int[][]{
        {-1, 0}, {0, 1}, {1, 0}, {0, -1}
    };

    public static void solve() {
        setupData();
        findLowestRiskPath();
    }

    private static void setupData() {
        riskGrid = new ArrayList<>();
        priorityQueue = new PriorityQueue<>();
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
                riskGrid.add(nextRow);
            }

            stretchRiskGrid(4, 4);
            int height = riskGrid.size();
            int width = riskGrid.get(0).size();
            setupRiskLevelsGrid(height, width);
            visited = new boolean[height][width];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setupRiskLevelsGrid(int height, int width) {
        riskLevels = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                riskLevels[i][j] = Integer.MAX_VALUE;
            }
        }
        riskLevels[0][0] = 0;
    }

    private static void findLowestRiskPath() {
        priorityQueue.add(new Node(0, 0, riskLevels[0][0]));

        while (!priorityQueue.isEmpty()) {
            Node currNode = priorityQueue.poll();
            int currX = currNode.getX();
            int currY = currNode.getY();

            if (visited[currX][currY]) {
                continue;
            }

            visited[currX][currY] = true;
            for (int[] delta : DELTAS) {
                int nextX = currX + delta[0];
                int nextY = currY + delta[1];
                if (0 <= nextX && nextX < riskGrid.size() && 0 <= nextY && nextY < riskGrid.get(0).size()
                    && !visited[nextX][nextY]) {
                    int currRisk = currNode.getRisk();
                    int nextRisk = riskGrid.get(nextX).get(nextY);
                    int newRisk = currRisk + nextRisk;
                    if (newRisk < riskLevels[nextX][nextY]) {
                        riskLevels[nextX][nextY] = newRisk;
                    }
                    priorityQueue.offer(new Node(nextX, nextY, riskLevels[nextX][nextY]));
                }
            }
        }
        System.out.println("Lowest risk: " + riskLevels[riskGrid.size() - 1][riskGrid.get(0).size() - 1]);
    }

    private static void stretchRiskGrid(int xTimes, int yTimes) {
        List<List<Integer>> newGrid = getDeepCopy(riskGrid);

        for (int i = 0; i < xTimes; i++) {
            for (int j = 0; j < newGrid.size(); j++) {
                List<Integer> currRow = newGrid.get(j);
                for (int k = 0; k < currRow.size(); k++) {
                    int newRisk = Math.max(1, (currRow.get(k) + 1) % 10);
                    currRow.set(k, newRisk);
                }
                riskGrid.get(j).addAll(currRow);
            }
        }

        newGrid = getDeepCopy(riskGrid);
        for (int i = 0; i < yTimes; i++) {
            for (List<Integer> row : newGrid) {
                for (int j = 0; j < row.size(); j++) {
                    int newRisk = Math.max(1, (row.get(j) + 1) % 10);
                    row.set(j, newRisk);
                }
            }

            riskGrid.addAll(getDeepCopy(newGrid));
        }
    }

    private static void printEachRow(int[][] grid) {
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static void printEachRow(List<List<Integer>> grid) {
        for (List<Integer> integers : grid) {
            System.out.println(integers);
        }
    }

    private static List<List<Integer>> getDeepCopy(List<List<Integer>> riskGrid) {
        List<List<Integer>> newRiskGrid = new ArrayList<>();
        for (List<Integer> row : riskGrid) {
            newRiskGrid.add(new ArrayList<>(row));
        }
        return newRiskGrid;
    }

    public static void main(String[] args) {
        solve();
    }
}
