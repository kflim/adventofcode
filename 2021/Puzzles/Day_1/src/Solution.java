import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public static int countIncreases() {
        int count = 0, prev = Integer.MAX_VALUE, currNum;
        String currLine = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currLine = br.readLine()) != null) {
                currNum = Integer.parseInt(currLine);
                if (currNum > prev) {
                    count++;
                }
                prev = currNum;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int countWindowIncreases() {
        List<Integer> window = new ArrayList<>();
        int count = 0, prevSum = 0, currNum, currSum;
        String currLine = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currLine = br.readLine()) != null) {
                currNum = Integer.parseInt(currLine);
                if (window.size() < 3) {
                    window.add(currNum);
                    prevSum += currNum;
                } else {
                    window.remove(0);
                    window.add(currNum);
                    currSum = window.stream().reduce(0, Integer::sum);
                    if (currSum > prevSum) {
                        count++;
                    }
                    prevSum = currSum;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("Number of increases: " + countIncreases());
        System.out.println("Number of window increases: " + countWindowIncreases());
    }
}
