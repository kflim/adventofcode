import java.util.List;

public class Solution {

    public static void solveParrOne() {
        List<String> sectionAssignments = Util.readAllSectionAssignments("input.txt");
        int fullyOverlappedAssignments = Util.countFullyOverlappedAssignments(sectionAssignments);
        System.out.println("The number of fully overlapped assignments is " + fullyOverlappedAssignments);
    }

    public static void solvePartTwo() {
        List<String> sectionAssignments = Util.readAllSectionAssignments("input.txt");
        int overlappedAssignments = Util.countOverlappedAssignments(sectionAssignments);
        System.out.println("The number of overlapped assignments is " + overlappedAssignments);
    }

    public static void main(String[] args) {
        // solveParrOne();
        solvePartTwo();
    }
}
