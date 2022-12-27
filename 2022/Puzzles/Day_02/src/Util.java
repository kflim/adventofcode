import static java.util.Map.entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Util {
    private static Map<String, Integer> RPSChoiceScoreMapping = Map.ofEntries(
        entry("Rock", 1),
        entry("Paper", 2),
        entry("Scissors", 3)
    );

    private static Map<String, String> columnOneMapping = Map.ofEntries(
        entry("A", "Rock"),
        entry("B", "Paper"),
        entry("C", "Scissors")
    );

    private static Map<String, String> columnTwoMappingFirstTime = Map.ofEntries(
        entry("X", "Rock"),
        entry("Y", "Paper"),
        entry("Z", "Scissors")
    );

    private static Map<String, Map<String, String>> columnTwoMappingSecondTime = Map.ofEntries(
        entry("X",
            Map.ofEntries(
                entry("Rock", "Scissors"),
                entry("Paper", "Rock"),
                entry("Scissors", "Paper"))
        ),
        entry("Y",
            Map.ofEntries(
                entry("Rock", "Rock"),
                entry("Paper", "Paper"),
                entry("Scissors", "Scissors"))
        ),
        entry("Z",
            Map.ofEntries(
                entry("Rock", "Paper"),
                entry("Paper", "Scissors"),
                entry("Scissors", "Rock"))
        )
    );

    public static List<String> readStrategyGuide(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static boolean hasWonRound(String opponentChoice, String myChoice) throws Exception {
        switch (myChoice) {
        case "Rock":
            return opponentChoice.equals("Scissors");
        case "Scissors":
            return opponentChoice.equals("Paper");
        case "Paper":
            return opponentChoice.equals("Rock");
        default:
            throw new Exception("One of the choices is not valid");
        }
    }

    public static int getTotalScoreFirstTime(List<String> strategyGuide) throws Exception {
        int totalScore = 0;

        for (String choicePair : strategyGuide) {
            String[] choices = choicePair.split(" ");
            String opponentChoice = columnOneMapping.get(choices[0]);
            String myChoice = columnTwoMappingFirstTime.get(choices[1]);
            int myChoiceScore = RPSChoiceScoreMapping.get(myChoice);
            if (opponentChoice.equals(myChoice)) {
                totalScore += myChoiceScore + 3;
            } else if (hasWonRound(opponentChoice, myChoice)) {
                totalScore += myChoiceScore + 6;
            } else {
                totalScore += myChoiceScore;
            }
        }

        return totalScore;
    }

    public static int getTotalScoreSecondTime(List<String> strategyGuide) throws Exception {
        int totalScore = 0;

        for (String strategyPair : strategyGuide) {
            String[] choices = strategyPair.split(" ");
            String opponentChoice = columnOneMapping.get(choices[0]);
            String strategyChoice = choices[1];
            String myChoice = columnTwoMappingSecondTime.get(strategyChoice).get(opponentChoice);
            int myChoiceScore = RPSChoiceScoreMapping.get(myChoice);
            if (strategyChoice.equals("X")) {
                totalScore += myChoiceScore;
            } else if (strategyChoice.equals("Y")) {
                totalScore += myChoiceScore + 3;
            } else {
                totalScore += myChoiceScore + 6;
            }
        }

        return totalScore;
    }

}
