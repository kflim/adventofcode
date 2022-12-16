import java.util.List;

// TODO: Find another way to do part one and two

public class Solution {

    public static void solvePartOne() {
        List<List<Integer>> treeGrid = Util.readGrid("input.txt");
        int[][] maxHeights = new int[treeGrid.size()][];
        boolean[][] isVisible = new boolean[treeGrid.size()][];

        visitOuterMostTrees(treeGrid, maxHeights, isVisible);
        if (treeGrid.size() >= 3) {
            traverseGrid(treeGrid, maxHeights, isVisible);
        }

        int visibleTreesCount = countVisibleTrees(isVisible);
        System.out.println("Number of visible trees is: " + visibleTreesCount);
    }

    public static void solvePartTwo() {
        List<List<Integer>> treeGrid = Util.readGrid("input.txt");
        int highestScenicScore = findHighestScenicScore(treeGrid);
        System.out.println("Highest Scenic score possible is: " + highestScenicScore);
    }

    private static void visitOuterMostTrees(List<List<Integer>> treeGrid, int[][] maxHeights,
                                            boolean[][] isVisible) {
        int rows = treeGrid.size();
        int cols = treeGrid.get(0).size();

        for (int i = 0; i < rows; i++) {
            maxHeights[i] = new int[cols];
            maxHeights[i][0] = treeGrid.get(i).get(0);
            maxHeights[i][cols - 1] = treeGrid.get(i).get(cols - 1);
            isVisible[i] = new boolean[cols];
            isVisible[i][0] = true;
            isVisible[i][cols - 1] = true;
        }

        for (int j = 1; j < cols - 1; j++) {
            maxHeights[0][j] = treeGrid.get(0).get(j);
            maxHeights[rows - 1][j] = treeGrid.get(rows - 1).get(j);
            isVisible[0][j] = true;
            isVisible[rows - 1][j] = true;
        }
    }

    private static void traverseGrid(List<List<Integer>> treeGrid, int[][] maxHeights,
                                     boolean[][] isVisible) {
        int[][] heightsCopy = Util.deepCopyIntArr(maxHeights);
        getMaximumsDownGrid(treeGrid, heightsCopy, isVisible);
        getMaximumsUpGrid(treeGrid, heightsCopy, isVisible);
        getMaximumsLeftGrid(treeGrid, heightsCopy, isVisible);
        getMaximumsRightGrid(treeGrid, heightsCopy, isVisible);
    }

    private static void getMaximumsDownGrid(List<List<Integer>> treeGrid, int[][] maxHeights,
                                            boolean[][] isVisible) {
        int rows = treeGrid.size();
        int cols = treeGrid.get(0).size();

        for (int j = 1; j < cols - 1; j++) {
            for (int i = 1; i < rows - 1; i++) {
                int currHeight = treeGrid.get(i).get(j);
                int prevMaxHeight = maxHeights[i - 1][j];
                int largerHeight = Math.max(prevMaxHeight, currHeight);
                maxHeights[i][j] = largerHeight;
                if (currHeight > prevMaxHeight) {
                    isVisible[i][j] = true;
                }
            }
        }
    }

    private static void getMaximumsUpGrid(List<List<Integer>> treeGrid, int[][] maxHeights,
                                          boolean[][] isVisible) {
        int rows = treeGrid.size();
        int cols = treeGrid.get(0).size();

        for (int j = 1; j < cols - 1; j++) {
            for (int i = rows - 2; i >= 1; i--) {
                int currHeight = treeGrid.get(i).get(j);
                int prevMaxHeight = maxHeights[i + 1][j];
                int largerHeight = Math.max(prevMaxHeight, currHeight);
                maxHeights[i][j] = largerHeight;
                if (currHeight > prevMaxHeight) {
                    isVisible[i][j] = true;
                }
            }
        }
    }

    private static void getMaximumsLeftGrid(List<List<Integer>> treeGrid, int[][] maxHeights,
                                            boolean[][] isVisible) {
        int rows = treeGrid.size();
        int cols = treeGrid.get(0).size();

        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                int currHeight = treeGrid.get(i).get(j);
                int prevMaxHeight = maxHeights[i][j - 1];
                int largerHeight = Math.max(prevMaxHeight, currHeight);
                maxHeights[i][j] = largerHeight;
                if (currHeight > prevMaxHeight) {
                    isVisible[i][j] = true;
                }
            }
        }
    }

    private static void getMaximumsRightGrid(List<List<Integer>> treeGrid, int[][] maxHeights,
                                             boolean[][] isVisible) {
        int rows = treeGrid.size();
        int cols = treeGrid.get(0).size();

        for (int i = 1; i < rows - 1; i++) {
            for (int j = cols - 2; j >= 1; j--) {
                int currHeight = treeGrid.get(i).get(j);
                int prevMaxHeight = maxHeights[i][j + 1];
                int largerHeight = Math.max(prevMaxHeight, currHeight);
                maxHeights[i][j] = largerHeight;
                if (currHeight > prevMaxHeight) {
                    isVisible[i][j] = true;
                }
            }
        }
    }

    private static int countVisibleTrees(boolean[][] isVisible) {
        int rows = isVisible.length;
        int cols = isVisible[0].length;
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (isVisible[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }

    public static int findHighestScenicScore(List<List<Integer>> treeGrid) {
        int rows = treeGrid.size();
        int cols = treeGrid.get(0).size();
        int currHighestScore = Integer.MIN_VALUE;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (checkPeakPos(treeGrid, i, j)) {
                    int currScenicScore = getScenicScore(treeGrid, i, j);
                    currHighestScore = Math.max(currHighestScore, currScenicScore);
                }
            }
        }

        return currHighestScore;
    }

    private static boolean checkPeakPos(List<List<Integer>> treeGrid, int row, int col) {
        int currHeight = treeGrid.get(row).get(col);

        for (int[] delta : Util.DELTAS) {
            int[] neighbourPos = new int[]{row + delta[0], col + delta[1]};
            if (!isValidPos(treeGrid, neighbourPos[0], neighbourPos[1])) {
                continue;
            }

            if (treeGrid.get(neighbourPos[0]).get(neighbourPos[1]) > currHeight) {
                return false;
            }
        }

        return true;
    }

    private static boolean isValidPos(List<List<Integer>> treeGrid, int col, int row) {
        int rows = treeGrid.size();
        int cols = treeGrid.get(0).size();
        return 0 <= row && row < rows && 0 <= col && col < cols;
    }

    private static int getScenicScore(List<List<Integer>> treeGrid, int row, int col) {
        return getScenicDistOnLeft(treeGrid, row, col) * getScenicDistOnRight(treeGrid, row, col)
            * getScenicDistOnTop(treeGrid, row, col) * getScenicDistOnBottom(treeGrid, row, col);
    }

    private static int getScenicDistOnLeft(List<List<Integer>> treeGrid, int row, int col) {
        if (col == 0) {
            return 0;
        }

        int toStartCol = col - 1;
        int currHeight = treeGrid.get(row).get(col);

        while (toStartCol > 0) {
            if (treeGrid.get(row).get(toStartCol) < currHeight) {
                toStartCol--;
            } else {
                break;
            }
        }

        return col - toStartCol;
    }

    private static int getScenicDistOnRight(List<List<Integer>> treeGrid, int row, int col) {
        int cols = treeGrid.get(0).size();
        if (col == cols - 1) {
            return 0;
        }

        int toEndCol = col + 1;
        int currHeight = treeGrid.get(row).get(col);

        while (toEndCol < cols - 1) {
            if (treeGrid.get(row).get(toEndCol) < currHeight) {
                toEndCol++;
            } else {
                break;
            }
        }

        return toEndCol - col;
    }

    private static int getScenicDistOnTop(List<List<Integer>> treeGrid, int row, int col) {
        if (row == 0) {
            return 0;
        }

        int toStartRow = row - 1;
        int currHeight = treeGrid.get(row).get(col);

        while (toStartRow > 0) {
            if (treeGrid.get(toStartRow).get(col) < currHeight) {
                toStartRow--;
            } else {
                break;
            }
        }

        return row - toStartRow;
    }

    private static int getScenicDistOnBottom(List<List<Integer>> treeGrid, int row, int col) {
        int rows = treeGrid.size();
        if (row == rows - 1) {
            return 0;
        }

        int toEndRow = row + 1;
        int currHeight = treeGrid.get(row).get(col);

        while (toEndRow < rows - 1) {
            if (treeGrid.get(toEndRow).get(col) < currHeight) {
                toEndRow++;
            } else {
                break;
            }
        }

        return toEndRow - row;
    }


    public static void main(String[] args) {
        // solvePartOne();
        solvePartTwo();
    }
}
