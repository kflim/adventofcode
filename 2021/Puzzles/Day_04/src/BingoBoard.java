public class BingoBoard {
    private final int[][] board;
    private final boolean[][] marked;

    public BingoBoard(int[][] board) {
        this.board = board;
        this.marked = new boolean[board.length][board[0].length];
    }

    public void update(int num) {
        outerLoop:
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == num) {
                    marked[row][col] = true;
                    break outerLoop;
                }
            }
        }
    }

    public boolean isSolved() {
        return hasCompletedRow() || hasCompletedColumn();
    }
    
    private boolean hasCompletedRow() {
        for (int row = 0; row < board.length; row++) {
            boolean completed = true;
            for (int col = 0; col < board[0].length; col++) {
                if (!marked[row][col]) {
                    completed = false;
                    break;
                }
            }
            if (completed) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasCompletedColumn() {
        for (int col = 0; col < board[0].length; col++) {
            boolean completed = true;
            for (int row = 0; row < board.length; row++) {
                if (!marked[row][col]) {
                    completed = false;
                    break;
                }
            }
            if (completed) {
                return true;
            }
        }
        return false;
    }

    public boolean[][] showMarkedBoard() {
        return marked;
    }

    public int[][] showBoard() {
        return board;
    }

    public int getUnmarkedSum() {
        int unmarkedSum = 0;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (!marked[row][col]) {
                    unmarkedSum += board[row][col];
                }
            }
        }
        return unmarkedSum;
    }
}
