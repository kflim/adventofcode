import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solution {
    public static void parseCommands() {
        int breadth = 0, depth = 0, steps;
        String currCommand = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currCommand = br.readLine()) != null) {
                String[] input = currCommand.split(" ");
                steps = Integer.parseInt(input[1]);
                if (currCommand.startsWith("forward")) {
                    breadth += steps;
                } else if (currCommand.startsWith("down")) {
                    depth += steps;
                } else if (currCommand.startsWith("up")) {
                    depth -= steps;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Breadth: " + breadth + ", Depth: " + depth);
    }

    public static void parseCommandsRight() {
        int breadth = 0, depth = 0, aim = 0, steps;
        String currCommand = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currCommand = br.readLine()) != null) {
                String[] input = currCommand.split(" ");
                steps = Integer.parseInt(input[1]);
                if (currCommand.startsWith("forward")) {
                    breadth += steps;
                    depth += aim * steps;
                } else if (currCommand.startsWith("down")) {
                    aim += steps;
                } else if (currCommand.startsWith("up")) {
                    aim -= steps;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Breadth: " + breadth + ", Depth: " + depth);
    }

    public static void main(String[] args) {
        parseCommands();
        parseCommandsRight();
    }
}
