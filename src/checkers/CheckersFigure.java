package checkers;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class CheckersFigure {

    static final int EMPTY = 0, RED = 1, RED_KING = 2, BLUE = 3, BLUE_KING = 4;
    int[][] figure;

    public CheckersFigure() {
        figure = new int[8][8];
        setUpGame();
    }

    void setUpGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (row % 2 == col % 2) {
                    if (col < 3) {
                        figure[row][col] = BLUE;
                    } else if (col > 4) {
                        figure[row][col] = RED;
                    } else {
                        figure[row][col] = EMPTY;
                    }
                } else {
                    figure[row][col] = EMPTY;
                }
            }
        }

    }

    int pieceAt(int row, int col) {
        return figure[row][col];

    }

    void moveFigure(int row1, int col1, int row2, int col2) {
        figure[row2][col2] = figure[row1][col1];
        figure[row1][col1] = EMPTY;
        if (row1 - row2 == 2 || row1 - row2 == -2) {

            int jumpRow = (row1 + row2) / 2;
            int jumpCol = (col1 + col2) / 2;
            figure[jumpRow][jumpCol] = EMPTY;
        }
        if (col2 == 0 && figure[row2][col2] == RED) {
            figure[row2][col2] = RED_KING;
        }
        if (col2 == 7 && figure[row2][col2] == BLUE) {
            figure[row2][col2] = BLUE_KING;
        }
    }

    void moveFigure(CheckersMove move) {
        moveFigure(move.row1, move.col1, move.row2, move.col2);
    }

    private boolean canJump(int player, int row1, int col1, int row2, int col2, int row3, int col3) {

        if (row3 < 0 || row3 >= 8 || col3 < 0 || col3 >= 8) {
            return false;
        }

        if (figure[row3][col3] != EMPTY) {
            return false;
        }

        if (player == RED) {
            if (figure[row1][col1] == RED && col3 > col1) {
                return false;
            }
            if (figure[row2][col2] != BLUE && figure[row2][col2] != BLUE_KING) {
                return false;
            }
            return true;
        } else {
            if (figure[row1][col1] == BLUE && col3 < col1) {
                return false;
            }
            if (figure[row2][col2] != RED && figure[row2][col2] != RED_KING) {
                return false;
            }
            return true;
        }

    }

    private boolean canMove(int player, int row1, int col1, int row2, int col2) {

        if (row2 < 0 || row2 >= 8 || col2 < 0 || col2 >= 8) {
            return false;
        }

        if (figure[row2][col2] != EMPTY) {
            return false;
        }

        if (player == RED) {
            if (figure[row1][col1] == RED && col2 > col1) {
                return false;
            }
            return true;
        } else {
            if (figure[row1][col1] == BLUE && col2 < col1) {
                return false;
            }
            return true;
        }

    }

    CheckersMove[] getMoves(int player) {

        if (player != RED && player != BLUE) {
            return null;
        }

        ArrayList<CheckersMove> moves = new ArrayList<CheckersMove>();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (figure[row][col] == player) {
                    if (canMove(player, row, col, row + 1, col + 1)) {
                        moves.add(new CheckersMove(row, col, row + 1, col + 1));
                    }
                    if (canMove(player, row, col, row - 1, col + 1)) {
                        moves.add(new CheckersMove(row, col, row - 1, col + 1));
                    }
                    if (canMove(player, row, col, row + 1, col - 1)) {
                        moves.add(new CheckersMove(row, col, row + 1, col - 1));
                    }
                    if (canMove(player, row, col, row - 1, col - 1)) {
                        moves.add(new CheckersMove(row, col, row - 1, col - 1));
                    }
                }
            }
        }
        CheckersMove[] moveArray = new CheckersMove[moves.size()];
        for (int i = 0; i < moves.size(); i++) {
            moveArray[i] = moves.get(i);
        }
        return moveArray;

    }
}
