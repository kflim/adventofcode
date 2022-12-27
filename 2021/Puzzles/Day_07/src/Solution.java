import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Solution {
    private static int[] horizontalPositions;

    public static void findMinimumFuel() {
        setupData();

        Arrays.sort(horizontalPositions);
        int length = horizontalPositions.length;
        int middlePosition = horizontalPositions[length / 2];
        int minimumFuel = 0;

        for (int pos : horizontalPositions) {
            minimumFuel += Math.abs(pos - middlePosition);
        }

        if (length % 2 == 0) {
            int otherMinimumFuel = 0;
            middlePosition = horizontalPositions[length / 2 - 1];
            for (int pos : horizontalPositions) {
                otherMinimumFuel += Math.abs(pos - middlePosition);
            }
            minimumFuel = Math.min(minimumFuel, otherMinimumFuel);
        }

        System.out.println("Minimum Fuel: " + minimumFuel);
    }

    private static void setupData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String lineOfPositions = br.readLine();
            horizontalPositions = Arrays.stream(lineOfPositions.split(","))
                                        .mapToInt(Integer::parseInt)
                                        .toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void findModifiedMinimumFuel() {
        setupData();

        int length = horizontalPositions.length;
        int average = (Arrays.stream(horizontalPositions).reduce(0, Integer::sum)) / length;

        int minimumFuel = 0;
        for (int pos : horizontalPositions) {
            int steps = Math.abs(pos - average);
            minimumFuel += (((steps * steps) + steps) / 2);
        }

        System.out.println("Minimum Fuel: " + minimumFuel);
    }

    public static void main(String[] args) {
        findModifiedMinimumFuel();
    }
}
