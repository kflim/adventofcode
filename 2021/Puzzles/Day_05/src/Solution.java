import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
    private static int[][] ventCounts;

    public static void findDangerousAreas() {
        setupData();

        int areaCount = 0;
        for (int x = 0; x < ventCounts.length; x++) {
            for (int y = 0; y < ventCounts[0].length; y++) {
                if (ventCounts[x][y] > 1) {
                    areaCount++;
                }
            }
        }

        System.out.println("Count of Dangerous Areas: " + areaCount);
    }

    private static void setupData() {
        ventCounts = new int[1000][1000];
        String currLine = "";
        String[] positions;
        String[] startCoordinates;
        String[] endCoordinates;

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currLine = br.readLine()) != null) {
                positions = currLine.split("->");
                startCoordinates = positions[0].trim().split(",");
                endCoordinates = positions[1].trim().split(",");
                int realStartX = Integer.parseInt(startCoordinates[0]);
                int realStartY = Integer.parseInt(startCoordinates[1]);
                int realEndX = Integer.parseInt(endCoordinates[0]);
                int realEndY = Integer.parseInt(endCoordinates[1]);
                updateVentCounts(realStartX, realStartY, realEndX, realEndY);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void updateVentCounts(int realStartX, int realStartY, int realEndX, int realEndY) {
        int smallerX = Math.min(realStartX, realEndX);
        int smallerY = Math.min(realStartY, realEndY);
        int largerX = Math.max(realStartX, realEndX);
        int largerY = Math.max(realStartY, realEndY);
        if (smallerX == largerX || smallerY == largerY) {
            for (int x = smallerX; x <= largerX; x++) {
                for (int y = smallerY; y <= largerY; y++) {
                    ventCounts[x][y]++;
                }
            }
        } else if (Math.abs(realStartX - realEndX) == Math.abs(realStartY - realEndY)) {
            if (realStartX > realEndX) {
                if (realStartY > realEndY) {
                    updateBottomLeftTopRight(realEndX, realEndY, realStartX);
                } else {
                    updateTopLeftBottomRight(realEndX, realEndY, realStartX);
                }
            } else {
                if (realStartY > realEndY) {
                    updateTopLeftBottomRight(realStartX, realStartY, realEndX);
                } else {
                    updateBottomLeftTopRight(realStartX, realStartY, realEndX);
                }
            }
        }
    }

    private static void updateBottomLeftTopRight(int leftX, int leftY, int rightX) {
        while (leftX <= rightX) {
            ventCounts[leftX++][leftY++]++;
        }
    }

    private static void updateTopLeftBottomRight(int leftX, int leftY, int rightX) {
        while (leftX <= rightX) {
            ventCounts[leftX++][leftY--]++;
        }
    }

    public static void main(String[] args) {
        findDangerousAreas();
    }
}
