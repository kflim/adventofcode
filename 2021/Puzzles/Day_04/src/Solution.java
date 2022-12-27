import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    private static String[] drawnNumbers;
    private static List<BingoBoard> bingoBoards;

    public static void findWinningBingoBoard() {
        setupData();
        findWinner();
    }

    private static void setupData() {
        bingoBoards = new ArrayList<>();
        int[][] currBoard = new int[5][5];
        int row = 0;
        String currLine = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            while ((currLine = br.readLine()) != null) {
                currLine = currLine.trim();
                if (currLine.length() > 15) {
                    drawnNumbers = currLine.split(",");
                } else if (currLine.length() > 0){
                    currBoard[row++] = Arrays.stream(currLine.split("\\s+"))
                                             .mapToInt(Integer::parseInt)
                                             .toArray();
                    if (row == currBoard.length) {
                        BingoBoard bingoBoard = new BingoBoard(currBoard);
                        bingoBoards.add(bingoBoard);
                        row = 0;
                        currBoard = new int[5][5];
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void findWinner() {
        outerLoop:
        for (String numString : drawnNumbers) {
            int currDrawnNum = Integer.parseInt(numString);
            for (BingoBoard bingoBoard : bingoBoards) {
                bingoBoard.update(currDrawnNum);
                if (bingoBoard.isSolved()) {
                    System.out.println("Winning board is: " + bingoBoards.indexOf(bingoBoard));
                    int unmarkedSum = bingoBoard.getUnmarkedSum();
                    int finalScore = unmarkedSum * currDrawnNum;
                    System.out.println("Final Score: " + finalScore);
                    break outerLoop;
                }
            }
        }
    }

    public static void findLosingBingoBoard() {
        setupData();
        findLoser();
    }

    private static void findLoser() {
        int completedBoards = 0;
        outerLoop:
        for (String numString : drawnNumbers) {
            int currDrawnNum = Integer.parseInt(numString);
            for (BingoBoard bingoBoard : bingoBoards) {
                if (!bingoBoard.isSolved()) {
                    bingoBoard.update(currDrawnNum);
                    if (bingoBoard.isSolved()) {
                        completedBoards++;
                    }
                }
                if (completedBoards == bingoBoards.size()) {
                    System.out.println("Losing board is: " + bingoBoards.indexOf(bingoBoard));
                    int unmarkedSum = bingoBoard.getUnmarkedSum();
                    int finalScore = unmarkedSum * currDrawnNum;
                    System.out.println("Final Score: " + finalScore);
                    break outerLoop;
                }
            }
        }
    }

    public static void main(String[] args) {
        findWinningBingoBoard();
        findLosingBingoBoard();
    }
}
