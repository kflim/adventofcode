import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Util {

    private static final String[] X_DIR = {"x", "-x"};
    private static final String[] Y_DIR = {"y", "-y"};
    private static final String[] Z_DIR = {"z", "-z"};

    public static List<String> orientations = getAllOrientations();
    public static int MIN_BEACONS_NEEDED = 12;

    public static List<Scanner> readScanners(String filepath) {
        List<Scanner> scanners = new ArrayList<>();

        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(filepath));
            Pattern coordinatesPattern = Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+)");
            String currLine;
            List<Coordinate> coordinateList = new ArrayList<>();

            while ((currLine = lineReader.readLine()) != null) {
                Matcher matcher = coordinatesPattern.matcher(currLine);
                if (currLine.matches("-{3} scanner \\d+ -{3}")) {
                    coordinateList = new ArrayList<>();
                } else if (matcher.matches()) {
                    int x = Integer.parseInt(matcher.group(1));
                    int y = Integer.parseInt(matcher.group(2));
                    int z = Integer.parseInt(matcher.group(3));
                    coordinateList.add(new Coordinate(x, y, z));
                } else {
                    scanners.add(new Scanner(coordinateList));
                }

            }

            scanners.add(new Scanner(coordinateList));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scanners;
    }

    private static List<String> getAllOrientations() {
        List<String> res = new ArrayList<>();

        setFixedXOrientation(res);
        setFixedYOrientation(res);
        setFixedZOrientation(res);

        return res.stream().filter(Util::hasDeterminant1).collect(Collectors.toList());
    }

    private static void setFixedXOrientation(List<String> orientations) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    String orientation = X_DIR[i] + "," + Y_DIR[j] + "," + Z_DIR[k];
                    orientations.add(orientation);
                }
            }

            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    String orientation = X_DIR[i] + "," + Z_DIR[j] + "," + Y_DIR[k];
                    orientations.add(orientation);
                }
            }
        }
    }

    private static void setFixedYOrientation(List<String> orientations) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    String orientation = Y_DIR[i] + "," + X_DIR[j] + "," + Z_DIR[k];
                    orientations.add(orientation);
                }
            }

            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    String orientation = Y_DIR[i] + "," + Z_DIR[j] + "," + X_DIR[k];
                    orientations.add(orientation);
                }
            }
        }
    }

    private static void setFixedZOrientation(List<String> orientations) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    String orientation = Z_DIR[i] + "," + X_DIR[j] + "," + Y_DIR[k];
                    orientations.add(orientation);
                }
            }

            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    String orientation = Z_DIR[i] + "," + Y_DIR[j] + "," + X_DIR[k];
                    orientations.add(orientation);
                }
            }
        }
    }

    private static boolean hasDeterminant1(String orientation) {
        String[] xyz = orientation.split(",");
        int[][] matrix = new int[3][];

        for (int i = 0; i < 3; i++) {
            if (xyz[i].matches("-?x")) {
                if (xyz[i].charAt(0) == '-') {
                    matrix[i] = new int[]{-1, 0, 0};
                } else {
                    matrix[i] = new int[]{1, 0, 0};
                }
            } else if (xyz[i].matches("-?y")) {
                if (xyz[i].charAt(0) == '-') {
                    matrix[i] = new int[]{0, -1, 0};
                } else {
                    matrix[i] = new int[]{0, 1, 0};
                }
            } else {
                if (xyz[i].charAt(0) == '-') {
                    matrix[i] = new int[]{0, 0, -1};
                } else {
                    matrix[i] = new int[]{0, 0, 1};
                }
            }
        }

        int determinant = matrix[0][0] * (matrix[1][1] * matrix[2][2] - matrix[1][2] * matrix[2][1])
            - matrix[0][1] * (matrix[1][0] * matrix[2][2] - matrix[1][2] * matrix[2][0])
            + matrix[0][2] * (matrix[1][0] * matrix[2][1] - matrix[1][1] * matrix[2][0]);

        return determinant == 1;
    }

    public static int abs(int x) {
        return Math.abs(x);
    }
}
