import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Util {

    public static Map<String, int[]> deltaMap = Map.ofEntries(
        Map.entry("U", new int[]{0, 1}),
        Map.entry("R", new int[]{1, 0}),
        Map.entry("D", new int[]{0, -1}),
        Map.entry("L", new int[]{-1, 0})
    );

    public static List<String> readLines(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static int absInt(int num) {
        return Math.abs(num);
    }
}
