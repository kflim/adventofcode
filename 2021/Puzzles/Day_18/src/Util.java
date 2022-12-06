import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
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

    public static BinaryTreeNode createTree(String snailFishNumber) {
        if (snailFishNumber == null || snailFishNumber.isEmpty()) {
            return new BinaryTreeNode();
        }

        Deque<BinaryTreeNode> stack = new ArrayDeque<>();
        BinaryTreeNode currNode = null;

        for(int i = 0; i < snailFishNumber.length(); i++) {
            if (snailFishNumber.charAt(i) == '[') {
                BinaryTreeNode newNode = new BinaryTreeNode();
                newNode.setParent(currNode);
                currNode = newNode;
                stack.push(currNode);
            } else if (snailFishNumber.charAt(i) == ']') {
                BinaryTreeNode childNode = stack.pop();

                if(stack.isEmpty()) {
                    currNode = childNode;
                    break;
                }

                currNode = stack.peek();

                if (currNode.getLeftValue() == null && currNode.getLeftChild() == null) {
                    currNode.setLeftChild(childNode);
                } else if (currNode.getRightValue() == null && currNode.getRightChild() == null){
                    currNode.setRightChild(childNode);
                }
            } else if (snailFishNumber.substring(i,i+2).matches("\\d+,")) {
                int num = Integer.parseInt(snailFishNumber.substring(i,i+1));
                currNode.setLeftValue(num, false);
            } else if (snailFishNumber.substring(i,i+2).matches(",\\d+")) {
                int num = Integer.parseInt(snailFishNumber.substring(i+1,i+2));
                currNode.setRightValue(num, false);
            }
        }

        return currNode;
    }

    public static BinaryTreeNode combineTrees(BinaryTreeNode leftTree, BinaryTreeNode rightTree) {
        if (leftTree == null) {
            return rightTree;
        }
        if (rightTree == null) {
            return leftTree;
        }

        BinaryTreeNode newRoot = new BinaryTreeNode();

        newRoot.setLeftChild(leftTree);
        newRoot.setRightChild(rightTree);

        leftTree.setParent(newRoot);
        rightTree.setParent(newRoot);

        return newRoot;
    }

    private static BinaryTreeNode findPredecessor(BinaryTreeNode node) {
        if (node == null) {
            return null;
        }

        BinaryTreeNode currNode = node;
        BinaryTreeNode currPredecessor = currNode.getParent();

        // Get closest common parent node to predecessor
        while (currPredecessor != null && currPredecessor.getLeftChild() == currNode) {
            currNode = currPredecessor;
            currPredecessor = currPredecessor.getParent();
        }

        if (currPredecessor == null) {
            return null;
        }

        if (currPredecessor.getLeftValue() != null) {
            return currPredecessor;
        }

        currPredecessor = currPredecessor.getLeftChild();

        while (currPredecessor.getRightChild() != null) {
            currPredecessor = currPredecessor.getRightChild();
        }

        return currPredecessor;
    }

    private static BinaryTreeNode findSuccessor(BinaryTreeNode node) {
        if (node == null) {
            return null;
        }

        BinaryTreeNode currNode = node;
        BinaryTreeNode currSuccessor = currNode.getParent();

        // Get closest common parent node to successor
        while (currSuccessor != null && currSuccessor.getRightChild() == currNode) {
            currNode = currSuccessor;
            currSuccessor = currSuccessor.getParent();
        }

        if (currSuccessor == null) {
            return null;
        }

        if (currSuccessor.getRightValue() != null) {
            return currSuccessor;
        }

        currSuccessor = currSuccessor.getRightChild();

        while (currSuccessor.getLeftChild() != null) {
            currSuccessor = currSuccessor.getLeftChild();
        }

        return currSuccessor;
    }

    private static void checkAndSetPredecessor(BinaryTreeNode predecessor, BinaryTreeNode node) {
        if (predecessor != null) {
            if (predecessor.getRightValue() != null) {
                predecessor.setRightValue(node.getLeftValue(), true);
            } else {
                predecessor.setLeftValue(node.getLeftValue(), true);
            }
        }
    }

    private static void checkAndSetSuccessor(BinaryTreeNode successor, BinaryTreeNode node) {
        if (successor != null) {
            if (successor.getLeftValue() != null) {
                successor.setLeftValue(node.getRightValue(), true);
            } else {
                successor.setRightValue(node.getRightValue(), true);
            }
        }
    }

    private static void checkAndSetParent(BinaryTreeNode parent, BinaryTreeNode node) {
        if (parent.getLeftChild() == node) {
            parent.setLeftChild(null);
            parent.setLeftValue(0, false);
        } else {
            parent.setRightChild(null);
            parent.setRightValue(0, false);
        }
    }

    private static boolean explodeNode(BinaryTreeNode node, int depth) {
        boolean hasExploded = false;

        if (node.getLeftChild() != null) {
            hasExploded = explodeNode(node.getLeftChild(), depth + 1);
        }

        if (node.isLeaf() && depth >= 4) {
            BinaryTreeNode predecessor = findPredecessor(node);
            checkAndSetPredecessor(predecessor, node);

            BinaryTreeNode successor = findSuccessor(node);
            checkAndSetSuccessor(successor, node);

            BinaryTreeNode parent = node.getParent();
            checkAndSetParent(parent, node);

            hasExploded = true;
        }

        if (node.getRightChild() != null) {
            hasExploded = explodeNode(node.getRightChild(), depth + 1) || hasExploded;
        }

        return hasExploded;
    }

    private static void createAndSetChild(BinaryTreeNode node, int value, boolean isLeftChild) {
        BinaryTreeNode newNode = new BinaryTreeNode();
        newNode.setLeftValue(value / 2, false);
        newNode.setRightValue((int) Math.ceil((double) value / 2), false);
        newNode.setParent(node);

        if (isLeftChild) {
            node.setLeftChild(newNode);
            node.setLeftValue(null, false);
        } else {
            node.setRightChild(newNode);
            node.setRightValue(null, false);
        }
    }

    // Split only once per check
    private static boolean splitNode(BinaryTreeNode node) {
        boolean hasSplit = false;

        if (node.getLeftChild() != null) {
            hasSplit = splitNode(node.getLeftChild());
        }

        if (hasSplit) {
            return true;
        }

        if (node.getLeftChild() == null && node.getLeftValue() >= 10) {
            createAndSetChild(node, node.getLeftValue(), true);
            return true;
        }

        if (node.getRightChild() == null && node.getRightValue() >= 10) {
            createAndSetChild(node, node.getRightValue(), false);
            return true;
        }

        if (node.getRightChild() != null) {
            hasSplit = splitNode(node.getRightChild());
        }

        return hasSplit;
    }

    public static void reduceTree(BinaryTreeNode root) {
        while (explodeNode(root, 0) || splitNode(root)) {
            while (explodeNode(root, 0));
            splitNode(root);
        }
    }
}
