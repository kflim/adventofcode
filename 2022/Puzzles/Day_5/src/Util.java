import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static List<String> readAllInstructions(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
    
    public static List<List<String>> getCratesAndProcedures(List<String> instructions) {
        int inputSize = instructions.size();
        int splitIndex = 0;

        for (int i = 0; i < inputSize; i++) {
            if (instructions.get(i).length() == 0) {
                splitIndex = i;
                break;
            }
        }
        List<String> crates = instructions.subList(0, splitIndex);
        List<String> procedures = instructions.subList(splitIndex + 1, inputSize);

        return Arrays.asList(crates, procedures);
    }

    public static List<String> findTopCratesAfterProcedures(List<List<String>> cratesAndProcedures) {
        List<String> crates = cratesAndProcedures.get(0);
        List<String> procedures = cratesAndProcedures.get(1);

        List<Stack<String>> crateStacks = parseCratePositions(crates);
        moveSingleCrates(crateStacks, procedures);

        return getTopCrates(crateStacks);
    }

    private static List<Stack<String>> parseCratePositions(List<String> crates) {
        List<Stack<String>> parsedCrateStacks = new ArrayList<>();

        int stackCount = (crates.get(0).length() + 1) / 4;
        for (int i = 0; i < stackCount; i++) {
            parsedCrateStacks.add(new Stack<>());
        }

        int cratesHeight = crates.size();
        Pattern pattern = Pattern.compile("[A-Z]");
        for (int i = cratesHeight - 2; i >=0; i--) {
            String currCrates = crates.get(i);
            Matcher matcher = pattern.matcher(currCrates);
            while (matcher.find()) {
                int stackIndex = (matcher.start() - 1) / 4;
                parsedCrateStacks.get(stackIndex).push(matcher.group());
            }
        }

        return parsedCrateStacks;
    }

    private static void moveSingleCrates(List<Stack<String>> crateStacks, List<String> procedures) {
        for (String procedure : procedures) {
            List<Integer> parsedProcedure = parseProcedure(procedure);
            int crateCount = parsedProcedure.get(0);
            int startPos = parsedProcedure.get(1) - 1;
            int endPos = parsedProcedure.get(2) - 1;

            Stack<String> startStack = crateStacks.get(startPos);
            Stack<String> endStack = crateStacks.get(endPos);

            for (int i = 0; i < crateCount; i++) {
                String currCrate = startStack.pop();
                endStack.push(currCrate);
            }

            crateStacks.set(startPos, startStack);
            crateStacks.set(endPos, endStack);
        }
    }

    private static List<Integer> parseProcedure(String procedure) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(procedure);
        List<Integer> result = new ArrayList<>();

        for (int i = 0; matcher.find(); i++) {
            result.add(Integer.parseInt(matcher.group()));
        }

        return result;
    }

    private static List<String> getTopCrates(List<Stack<String>> crateStacks) {
        List<String> topCrates = new ArrayList<>();

        for (Stack<String> crateStack : crateStacks) {
            topCrates.add(crateStack.peek());
        }

        return topCrates;
    }

    public static List<String> findTopCratesAfterNewProcedures(List<List<String>> cratesAndProcedures) {
        List<String> crates = cratesAndProcedures.get(0);
        List<String> procedures = cratesAndProcedures.get(1);

        List<Stack<String>> crateStacks = parseCratePositions(crates);
        moveMultipleCrates(crateStacks, procedures);

        return getTopCrates(crateStacks);
    }

    private static void moveMultipleCrates(List<Stack<String>> crateStacks, List<String> procedures) {
        for (String procedure : procedures) {
            List<Integer> parsedProcedure = parseProcedure(procedure);
            int crateCount = parsedProcedure.get(0);
            int startPos = parsedProcedure.get(1) - 1;
            int endPos = parsedProcedure.get(2) - 1;

            Stack<String> startStack = crateStacks.get(startPos);
            Stack<String> tempStack = new Stack<>();
            Stack<String> endStack = crateStacks.get(endPos);

            for (int i = 0; i < crateCount; i++) {
                String currCrate = startStack.pop();
                tempStack.push(currCrate);
            }

            for (int i = 0; i < crateCount; i++) {
                String currCrate = tempStack.pop();
                endStack.push(currCrate);
            }

            crateStacks.set(startPos, startStack);
            crateStacks.set(endPos, endStack);
        }
    }
}
