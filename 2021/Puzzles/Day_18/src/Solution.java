import java.util.List;

public class Solution {

    public static void solvePartOne() {
        List<String> snailFishNumbers = Util.readAllNumbers("input.txt");
        BinaryTreeNode root = null;

        for (String snailFishNumber : snailFishNumbers) {
            BinaryTreeNode nextTree = Util.createTree(snailFishNumber);
            root = Util.combineTrees(root, nextTree);
            Util.reduceTree(root);
        }

        System.out.println(root);
        System.out.println("Total magnitude: " + root.getMagnitude());
    }

    public static void solvePartTwo() {
        List<String> snailFishNumbers = Util.readAllNumbers("input.txt");
        int largestMagnitude = Integer.MIN_VALUE;
        int numbersSize = snailFishNumbers.size();

        for (int i = 0; i < numbersSize; i++) {
            for (int j = i + 1; j < numbersSize; j++) {
                BinaryTreeNode nodeOne = Util.createTree(snailFishNumbers.get(i));
                BinaryTreeNode nodeTwo = Util.createTree(snailFishNumbers.get(j));
                BinaryTreeNode combinedNode = Util.combineTrees(nodeOne, nodeTwo);
                Util.reduceTree(combinedNode);
                int currMagnitude = combinedNode.getMagnitude();
                largestMagnitude = Math.max(largestMagnitude, currMagnitude);
            }
        }

        System.out.println("Largest magnitude possible: " + largestMagnitude);
    }

    public static void main(String[] args) {
        solvePartTwo();
    }
}
