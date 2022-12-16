import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class Solution {

    public static void solvePartOne() {
        List<String> terminalOutput = Util.readLines("input.txt");
        FileNode root = new FileNode("/");
        formFileSystem(terminalOutput, root);
        findSizeOfAllNodes(root);
        int totalSize = getTotalSizeOfBigDirectories(root);
        System.out.println("Total size of directories with size at most 100000 is " + totalSize);
    }

    public static void solvePartTwo() {
        List<String> terminalOutput = Util.readLines("input.txt");
        FileNode root = new FileNode("/");
        formFileSystem(terminalOutput, root);
        findSizeOfAllNodes(root);
        int totalFileSystemSize = root.getSize();
        int freeSpace = Util.TOTAL_DISK_SPACE - totalFileSystemSize;
        int minSizeToBeDeleted =
            findSizeToBeDeleted(root, Util.MIN_UNUSED_SPACE_NEEDED - freeSpace);
        System.out.println("Minimum space to be deleted is : " + minSizeToBeDeleted);
    }

    private static void formFileSystem(List<String> terminalOutput, FileNode root) {
        FileNode currNode = root;

        for (String output : terminalOutput) {
            if (output.matches(String.valueOf(Util.LS_PATTERN))) {
                continue;
            }
            if (output.matches(String.valueOf(Util.CHANGE_ROOT_PATTERN))) {
                currNode = root;
                continue;
            }

            Matcher changeDirMatcher = Util.CD_PATTERN.matcher(output);
            if (changeDirMatcher.matches()) {
                currNode = handleCD(output, currNode);
                continue;
            }

            if (output.matches(String.valueOf(Util.DIR_PATTERN))) {
                String nextNode = findNextNode(output, Util.DIR_REGEX + " ");
                FileNode nextChild = new FileNode(nextNode, currNode);
                currNode.addChildNode(nextChild);
            } else {
                String nextNode = findNextNode(output, Util.ANY_NUM_REGEX + " ");
                int fileSize = findNodeSize(output, " " + Util.ANY_WORD_REGEX);
                FileNode nextChild = new FileNode(nextNode, fileSize, currNode);
                currNode.addChildNode(nextChild);
            }
        }
    }

    private static FileNode handleCD(String output, FileNode currNode) {
        if (output.matches(String.valueOf(Util.MOVE_OUT_DIR_PATTERN))) {
            FileNode parent = currNode.getParent();
            if (parent != null) {
                currNode = parent;
            }
        } else {
            String nextNode = findNextNode(output, Util.CD_REGEX + " ");
            currNode = currNode.findChildWithName(nextNode);
        }

        return currNode;
    }

    private static String findNextNode(String output, String regex) {
        return output.split(regex)[1];
    }

    private static int findNodeSize(String output, String regex) {
        return Integer.parseInt(output.split(regex)[0]);
    }

    private static int findSizeOfAllNodes(FileNode root) {
        if (root == null) {
            return 0;
        }

        if (!root.isDir()) {
            return root.getSize();
        }

        int totalSize = 0;
        Set<FileNode> children = root.getChildren();
        for (FileNode child : children) {
            totalSize += findSizeOfAllNodes(child);
        }
        root.setSize(totalSize);

        return totalSize;
    }

    private static int getTotalSizeOfBigDirectories(FileNode root) {
        if (root == null) {
            return 0;
        }

        int totalSize = root.getSize() <= 100000 ? root.getSize() : 0;
        Set<FileNode> children = root.getChildren();
        for (FileNode child : children) {
            if (!child.isDir()) {
                continue;
            }
            int totalSizeUnderChild = getTotalSizeOfBigDirectories(child);
            totalSize += totalSizeUnderChild;
        }

        return totalSize;
    }

    private static int findSizeToBeDeleted(FileNode root, int minimumSpaceNeeded) {
        if (root == null) {
            return 0;
        }

        int currMinimum = root.getSize();
        Set<FileNode> children = root.getChildren();
        for (FileNode child : children) {
            if (!child.isDir()) {
                continue;
            }
            int minSpaceUnderChild = findSizeToBeDeleted(child, minimumSpaceNeeded);
            if (minSpaceUnderChild > minimumSpaceNeeded) {
                currMinimum = Math.min(currMinimum, minSpaceUnderChild);
            }
        }

        return currMinimum;
    }

    public static void main(String[] args) {
        // solvePartOne();
        solvePartTwo();
    }
}
