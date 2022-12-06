import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {

    private static int minEndX;
    private static int maxEndX;
    private static int minEndY;
    private static int maxEndY;

    public static void solve() {
        setupData();
        findTotalValidVelocities();
    }

    private static void setupData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String targetLine = br.readLine();

            int commaIndex = targetLine.indexOf(',');

            int xInfoIndex = targetLine.indexOf("x=");
            String xInfo = targetLine.substring(xInfoIndex + 2, commaIndex);
            String[] xInfoParts = xInfo.split("\\.\\.");
            minEndX = Integer.parseInt(xInfoParts[0]);
            maxEndX = Integer.parseInt(xInfoParts[1]);

            String yInfo = targetLine.substring(commaIndex + 4);
            String[] yInfoParts = yInfo.split("\\.\\.");
            minEndY = Integer.parseInt(yInfoParts[0]);
            maxEndY = Integer.parseInt(yInfoParts[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findHighestPosition() {
        int absoluteMinY = Math.abs(minEndY);
        int maximumHeight = (absoluteMinY * (absoluteMinY - 1)) / 2;

        System.out.println("Highest vertical position: " + maximumHeight);
    }

    private static void findTotalValidVelocities() {
        int velocityCount = 0;
        int highestVerticalVelocity = Math.abs(minEndY) - 1;

        for (int x = 1; x <= maxEndX; x++) {
            for (int y = highestVerticalVelocity; y >= minEndY; y--) {
                if (isValidVelocity(x, y)) {
                    velocityCount++;
                }
            }
        }

        System.out.println("Total number of valid velocities: " + velocityCount);
    }

    private static boolean isValidVelocity(int vx, int vy) {
        int startX = 0;
        int startY = 0;

        while (true) {
            startX += vx;
            startY += vy;

            if (minEndX <= startX && startX <= maxEndX && minEndY <= startY && startY <= maxEndY) {
                return true;
            } else if (startX > maxEndX || startY < minEndY) {
                return false;
            }

            vx = Math.max(0, vx - 1);
            vy--;
        }
    }

    public static void main(String[] args) {
        solve();
    }
}
