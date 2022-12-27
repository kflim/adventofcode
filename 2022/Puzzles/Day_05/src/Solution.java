import java.util.List;

public class Solution {

    public static void solveParrOne() {
        List<String> instructions = Util.readAllInstructions("input.txt");
        List<List<String>> cratesAndProcedures = Util.getCratesAndProcedures(instructions);
        List<String> topCrates = Util.findTopCratesAfterProcedures(cratesAndProcedures);
        System.out.println("The list of crates on top of each stack is: " + topCrates);
    }

    public static void solvePartTwo() {
        List<String> instructions = Util.readAllInstructions("input.txt");
        List<List<String>> cratesAndProcedures = Util.getCratesAndProcedures(instructions);
        List<String> topCrates = Util.findTopCratesAfterNewProcedures(cratesAndProcedures);
        System.out.println("The list of crates on top of each stack is: " + topCrates);
    }

    public static void main(String[] args) {
        // solveParrOne();
        solvePartTwo();
    }
}
