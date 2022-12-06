import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Solution {
    private static long[] fishTimerCount;

    public static void simulateLanternFishGrowth() {
        setupData();

        long[] newTimers;
        long newbornCount;
        for (int day = 0; day < 256; day++) {
            newTimers = new long[9];
            newbornCount = fishTimerCount[0];
            System.arraycopy(fishTimerCount, 1, newTimers, 0, 8);
            newTimers[6] += newbornCount;
            newTimers[8] = newbornCount;
            fishTimerCount = newTimers;
        }

        long totalFish = 0;
        for (long count : fishTimerCount) {
            totalFish += count;
        }

        System.out.println("Total fish after 256 days: " + totalFish);
    }

    private static void setupData() {
        fishTimerCount = new long[9];

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String lineOfTimers = br.readLine();
            int[] timers = Arrays.stream(lineOfTimers.split(","))
                                 .mapToInt(Integer::parseInt)
                                 .toArray();
            for (int timer : timers) {
                fishTimerCount[timer]++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        simulateLanternFishGrowth();
    }
}
