import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Util {

    public static String ANY_WORD_REGEX = "[^\\s]+";
    public static String ANY_NUM_REGEX = "\\d+";
    public static String DIR_REGEX = "dir";
    public static String CD_REGEX = "\\$ cd";
    public static String LS_REGEX = "\\$ ls";
    public static Pattern DIR_PATTERN = Pattern.compile(DIR_REGEX + " " + ANY_WORD_REGEX);
    public static Pattern CD_PATTERN = Pattern.compile(CD_REGEX + " " + ANY_WORD_REGEX);
    public static Pattern CHANGE_ROOT_PATTERN = Pattern.compile("\\$ cd /");
    public static Pattern MOVE_OUT_DIR_PATTERN = Pattern.compile(CD_REGEX + " ..");
    public static Pattern LS_PATTERN = Pattern.compile(LS_REGEX);
    public static int TOTAL_DISK_SPACE = 70000000;
    public static int MIN_UNUSED_SPACE_NEEDED = 30000000;

    public static List<String> readLines(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void printNodes(FileNode root) {
        if (root != null) {
            System.out.println(root);
            Set<FileNode> children = root.getChildren();
            for (FileNode node : children) {
                printNodes(node);
            }
        }
    }

}
