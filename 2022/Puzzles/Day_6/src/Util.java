import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Util {

    public static String readLine(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath)).get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int findFirstPacketMarker(String signal) {
        Map<Character, Integer> characterIndexes = new HashMap<>();
        int signalLength = signal.length();
        int packetLength = 4;

        for (int i = 0, start = 0; i < signalLength; i++) {
            char c = signal.charAt(i);
            if (characterIndexes.containsKey(c)) {
                if (characterIndexes.get(c) >= start) {
                    start = characterIndexes.get(c) + 1;
                }
            }
            if (i - start + 1 == packetLength) {
                return i + 1;
            }
            characterIndexes.put(c, i);
        }

        return signalLength;
    }

    public static int findFirstMessageMarker(String signal) {
        Map<Character, Integer> characterIndexes = new HashMap<>();
        int signalLength = signal.length();
        int packetLength = 14;

        for (int i = 0, start = 0; i < signalLength; i++) {
            char c = signal.charAt(i);
            if (characterIndexes.containsKey(c)) {
                if (characterIndexes.get(c) >= start) {
                    start = characterIndexes.get(c) + 1;
                }
            }
            if (i - start + 1 == packetLength) {
                return i + 1;
            }
            characterIndexes.put(c, i);
        }

        return signalLength;
    }
}
