import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {

    public static void solvePartOne() {
        List<String> instructions = Util.readLines("test-input.txt");
        Set<Position> visitedPos = new HashSet<>();
        moveRope(instructions, visitedPos, 2);
        System.out.println("Number of unique positions by the tail are: " + visitedPos.size());
    }

    public static void solvePartTwo() {
        List<String> instructions = Util.readLines("input.txt");
        Set<Position> visitedPos = new HashSet<>();
        moveRope(instructions, visitedPos, 10);

        System.out.println("Number of unique positions by the tail are: " + visitedPos.size());
    }

    private static void moveRope(List<String> instructions, Set<Position> visitedPos,
                                 int knotCount) {
        List<Position> knots = new ArrayList<>();
        for (int i = 0; i < knotCount; i++) {
            knots.add(new Position(0, 0));
        }
        visitedPos.add(new Position(0, 0));
        Map<String, int[]> deltaMap = Util.deltaMap;

        for (String instruction : instructions) {
            String[] instructionDetails = instruction.split(" ");
            String direction = instructionDetails[0];
            int[] delta = deltaMap.get(direction);
            int steps = Integer.parseInt(instructionDetails[1]);

            movePositions(knots, delta, steps, visitedPos);
        }
    }

    private static void movePositions(List<Position> knots, int[] delta, int steps,
                                      Set<Position> visitedPos) {
        int knotCount = knots.size();
        Position head = knots.get(0);
        Position tail = knots.get(knotCount - 1);

        for (int i = 0; i < steps; i++) {
            movePos(head, delta);

            for (int j = 1; j < knotCount; j++) {
                Position prevKnot = knots.get(j - 1);
                Position nextKnot = knots.get(j);
                if (nextKnot.tooFarFrom(prevKnot)) {
                    moveToLastDelta(prevKnot, nextKnot);
                }
            }

            visitedPos.add(new Position(tail));
        }
    }

    private static void movePos(Position pos, int[] delta) {
        pos.changePos(delta[0], delta[1]);
    }

    private static void moveToLastDelta(Position head, Position tail) {
        int[] diff = head.getDiff(tail);
        int absXDelta = Util.absInt(diff[0]);
        int absYDelta = Util.absInt(diff[1]);
        if (absXDelta > absYDelta) {
            setTailX(head, tail, diff);
        } else if (absXDelta < absYDelta){
            setTailY(head, tail, diff);
        } else {
            setTailXY(head, tail, diff);
        }
    }

    private static void setTailX(Position head, Position tail, int[] diff) {
        if (diff[0] > 0) {
            tail.setPos(head.getX() - 1, head.getY());
        } else {
            tail.setPos(head.getX() + 1, head.getY());
        }
    }

    private static void setTailY(Position head, Position tail, int[] diff) {
        if (diff[1] > 0) {
            tail.setPos(head.getX(), head.getY() - 1);
        } else {
            tail.setPos(head.getX(), head.getY() + 1);
        }
    }

    private static void setTailXY(Position head, Position tail, int[] diff) {
        if (diff[0] > 0) {
            if (diff[1] > 0) {
                tail.setPos(head.getX() - 1, head.getY() - 1);
            } else {
                tail.setPos(head.getX() - 1, head.getY() + 1);
            }
        } else {
            if (diff[1] > 0) {
                tail.setPos(head.getX() + 1, head.getY() - 1);
            } else {
                tail.setPos(head.getX() + 1, head.getY() + 1);
            }
        }
    }

    public static void main(String[] args) {
        // solvePartOne();
        solvePartTwo();
    }
}
