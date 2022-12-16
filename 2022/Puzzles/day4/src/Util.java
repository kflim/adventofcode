import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class Util {

    public static List<String> readAllSectionAssignments(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static int countFullyOverlappedAssignments(List<String> sectionAssignments) {
        int count = 0;

        for (String sectionAssignmentsPair : sectionAssignments) {
            String[] assignments = sectionAssignmentsPair.split(",");
            String firstAssignments = assignments[0];
            String secondAssignments = assignments[1];
            if (assignmentsFullyOverlap(firstAssignments, secondAssignments)) {
                count++;
            }
        }

        return count;
    }

    private static boolean assignmentsFullyOverlap(String assignmentsOne, String assignmentsTwo) {
        String[] firstLastAssignmentsOne = assignmentsOne.split("-");
        String[] firstLastAssignmentsTwo = assignmentsTwo.split("-");
        int firstAssignmentOne = Integer.parseInt(firstLastAssignmentsOne[0]);
        int lastAssignmentOne = Integer.parseInt(firstLastAssignmentsOne[1]);
        int firstAssignmentTwo = Integer.parseInt(firstLastAssignmentsTwo[0]);
        int lastAssignmentTwo = Integer.parseInt(firstLastAssignmentsTwo[1]);

        return firstAssignmentOne >= firstAssignmentTwo && lastAssignmentOne <= lastAssignmentTwo
            || firstAssignmentOne <= firstAssignmentTwo && lastAssignmentOne >= lastAssignmentTwo;
    }

    public static int countOverlappedAssignments(List<String> sectionAssignments) {
        int count = 0;

        for (String sectionAssignmentsPair : sectionAssignments) {
            String[] assignments = sectionAssignmentsPair.split(",");
            String firstAssignments = assignments[0];
            String secondAssignments = assignments[1];
            if (assignmentsOverlap(firstAssignments, secondAssignments)) {
                count++;
            }
        }

        return count;
    }

    private static boolean assignmentsOverlap(String assignmentsOne, String assignmentsTwo) {
        String[] firstLastAssignmentsOne = assignmentsOne.split("-");
        String[] firstLastAssignmentsTwo = assignmentsTwo.split("-");
        int firstAssignmentOne = Integer.parseInt(firstLastAssignmentsOne[0]);
        int lastAssignmentOne = Integer.parseInt(firstLastAssignmentsOne[1]);
        int firstAssignmentTwo = Integer.parseInt(firstLastAssignmentsTwo[0]);
        int lastAssignmentTwo = Integer.parseInt(firstLastAssignmentsTwo[1]);

        return lastAssignmentOne >= firstAssignmentTwo && lastAssignmentTwo >= firstAssignmentOne;
    }
}
