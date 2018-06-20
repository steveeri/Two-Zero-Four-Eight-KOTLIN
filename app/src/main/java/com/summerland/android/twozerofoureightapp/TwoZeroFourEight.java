package com.summerland.android.twozerofoureightapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by steve on 17/10/16.
 * Grid is from bottom left to right, bottom to top.
 */
public class TwoZeroFourEight implements Serializable {

    public enum actions {ADD_NEW, BLANK, SLIDE, COMPACT, REFRESH}

    static final int TARGET = 2048;
    private static final int GRID_CNT = 16, ROW_CNT = 4, COL_CNT = 4, BLANK = 0;
    private int score = 0, numEmpty = 16, maxTile = 0;

    private ArrayList<Transition> transitions = null;
    private int[] tiles = new int[16];
    private Random rand = new Random();

    TwoZeroFourEight() {
        this.createNewTransitions();
        this.addNewTile();
        this.addNewTile();
    }

    void createNewTransitions() {
        transitions = new ArrayList<>();
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void rePlot() {
        this.createNewTransitions();
        for (int i = 0; i < GRID_CNT; i++) {
            this.transitions.add(new Transition(actions.REFRESH, tiles[i], i));
        }
    }

    void addNewTile() {
        if (numEmpty == 0) return;

        int val = (rand.nextInt(2) + 1) * 2;
        int pos = rand.nextInt(numEmpty);
        int blanks = 0;

        for (int i = 0; i < GRID_CNT; i++) {
            if (tiles[i] == BLANK) {
                if (blanks == pos) {
                    tiles[i] = val;
                    if (val > maxTile) maxTile = val;
                    numEmpty--;
                    if (transitions != null)
                        transitions.add(new Transition(actions.ADD_NEW, val, i));
                    return;
                }
                blanks++;
            }
        }
    }

    public int getMaxTile() {
        return maxTile;
    }

    public int getValue(int i) {
        return (tiles[i]);
    }

    public int getScore() {
        return score;
    }

    public boolean hasMovesRemaining() {

        if (numEmpty > 0) return true;

        // check left-right for compact moves remaining.
        int arrLimit = GRID_CNT - COL_CNT;
        for (int i = 0; i < arrLimit; i++) {
            if (tiles[i] == tiles[i + COL_CNT]) return true;
        }

        // check up-down for compact moves remaining.
        arrLimit = GRID_CNT - 1;
        for (int i = 0; i < arrLimit; i++) {
            if ((i + 1) % ROW_CNT > 0) {
                if (tiles[i] == tiles[i + 1]) return true;
            }
        }
        return false;
    }

    /*public boolean canCompactLeftRight(){
        // check left-right for compact moves remaining.
        for (int i = 0; i < ROW_CNT; i++) {
            int val1 = tiles[i]; // seed value for the row.
            for (int j = 1; j < COL_CNT; j++) {
                if (val1 != 0 && val2 != 0) {
                    if (val1 == val2) {
                        return true;
                    } else {
                        val1 = val2; // we found differing non zero values.
                    }
                } else if (val1 == 0 && val2 != 0) {
                    val1 = val2;
                }
            }
        }
        return false;
    }*/

/*
    public boolean canCompactUpDown(){

        // check up-down for compact moves remaining.
        for (int i = 0; i < COL_CNT; i++) {
            int val1 = tiles[i*COL_CNT]; // seed value for the column.
            for (int j = 1; j < ROW_CNT; j++) {
                int val2 = tiles[(i*COL_CNT)+j];
                if (val1 != 0 && val2 != 0) {
                    if (val1 == val2) {
                        return true;
                    } else {
                        val1 = val2; // we found differing non zero values.
                    }
                } else if (val1 == 0 && val2 != 0) {
                    val1 = val2;
                }
            }
        }
        return false;
    }
*/

    private boolean slideTileRowOrColumn(int index1, int index2, int index3, int index4) {

        boolean moved = false;
        int val1 = tiles[index1];
        int tmpI = index1;
        for (int j = 1; j < COL_CNT; j++) {
            int val2 = 0, tmpJ = 0;
            switch (j) {
                case 1:
                    val2 = tiles[index2];
                    tmpJ = index2;
                    break;
                case 2:
                    val2 = tiles[index3];
                    tmpJ = index3;
                    break;
                case 3:
                    val2 = tiles[index4];
                    tmpJ = index4;
                    break;
                default:
                    break;
            }

            if (val1 == 0 && val2 != 0) {
                tiles[tmpI] = val2;
                tiles[tmpJ] = 0;
                if (transitions != null) {
                    transitions.add(new Transition(actions.SLIDE, val2, tmpJ, tmpI));
                    transitions.add(new Transition(actions.BLANK, BLANK, tmpJ));
                }
                val1 = 0;
                tmpI = tmpJ;
                moved = true;
            } else if (val1 != 0 && val2 == 0) {
                val1 = 0;
                tmpI = tmpJ;
            }
        }
        return moved;
    }

    private boolean slideLeft() {
        boolean a, b, c, d;
        a = slideTileRowOrColumn(0, 4, 8, 12);
        b = slideTileRowOrColumn(1, 5, 9, 13);
        c = slideTileRowOrColumn(2, 6, 10, 14);
        d = slideTileRowOrColumn(3, 7, 11, 15);
        return (a || b || c || d);
    }

    private boolean slideRight() {
        boolean a, b, c, d;
        a = slideTileRowOrColumn(12, 8, 4, 0);
        b = slideTileRowOrColumn(13, 9, 5, 1);
        c = slideTileRowOrColumn(14, 10, 6, 2);
        d = slideTileRowOrColumn(15, 11, 7, 3);
        return (a || b || c || d);
    }

    private boolean slideUp() {
        boolean a, b, c, d;
        a = slideTileRowOrColumn(0, 1, 2, 3);
        b = slideTileRowOrColumn(4, 5, 6, 7);
        c = slideTileRowOrColumn(8, 9, 10, 11);
        d = slideTileRowOrColumn(12, 13, 14, 15);
        return (a || b || c || d);
    }

    private boolean slideDown() {
        boolean a, b, c, d;
        a = slideTileRowOrColumn(3, 2, 1, 0);
        b = slideTileRowOrColumn(7, 6, 5, 4);
        c = slideTileRowOrColumn(11, 10, 9, 8);
        d = slideTileRowOrColumn(15, 14, 13, 12);
        return (a || b || c || d);
    }

    private boolean compactTileRowOrColumn(int index1, int index2, int index3, int index4) {

        boolean compacted = false;

        for (int j = 1; j < COL_CNT; j++) {
            int val1 = 0, val2 = 0, tmpI = 0, tmpJ = 0;
            switch (j) {
                case 1:
                    val1 = tiles[index1];
                    val2 = tiles[index2];
                    tmpI = index1;
                    tmpJ = index2;
                    break;
                case 2:
                    val1 = tiles[index2];
                    val2 = tiles[index3];
                    tmpI = index2;
                    tmpJ = index3;
                    break;
                case 3:
                    val1 = tiles[index3];
                    val2 = tiles[index4];
                    tmpI = index3;
                    tmpJ = index4;
                    break;
                default:
                    break;
            }

            if (val1 != 0 && val1 == val2) {
                tiles[tmpI] = val1 * 2;
                score += tiles[tmpI];
                if (tiles[tmpI] > maxTile) maxTile = tiles[tmpI];
                numEmpty++;
                tiles[tmpJ] = 0;
                compacted = true;
                if (transitions != null) {
                    transitions.add(new Transition(actions.COMPACT, tiles[tmpI], tmpJ, tmpI));
                    transitions.add(new Transition(actions.BLANK, BLANK, tmpJ));
                }
            }
        }
        return compacted;
    }

    private boolean compactLeft() {
        boolean a, b, c, d;
        a = compactTileRowOrColumn(0, 4, 8, 12);
        b = compactTileRowOrColumn(1, 5, 9, 13);
        c = compactTileRowOrColumn(2, 6, 10, 14);
        d = compactTileRowOrColumn(3, 7, 11, 15);
        return (a || b || c || d);
    }

    private boolean compactRight() {
        boolean a, b, c, d;
        a = compactTileRowOrColumn(12, 8, 4, 0);
        b = compactTileRowOrColumn(13, 9, 5, 1);
        c = compactTileRowOrColumn(14, 10, 6, 2);
        d = compactTileRowOrColumn(15, 11, 7, 3);
        return (a || b || c || d);
    }

    private boolean compactUp() {
        boolean a, b, c, d;
        a = compactTileRowOrColumn(0, 1, 2, 3);
        b = compactTileRowOrColumn(4, 5, 6, 7);
        c = compactTileRowOrColumn(8, 9, 10, 11);
        d = compactTileRowOrColumn(12, 13, 14, 15);
        return (a || b || c || d);
    }

    private boolean compactDown() {
        boolean a, b, c, d;
        a = compactTileRowOrColumn(3, 2, 1, 0);
        b = compactTileRowOrColumn(7, 6, 5, 4);
        c = compactTileRowOrColumn(11, 10, 9, 8);
        d = compactTileRowOrColumn(15, 14, 13, 12);
        return (a || b || c || d);
    }

    public boolean actionMoveLeft() {
        boolean a, b, c;
        a = slideLeft();
        b = compactLeft();
        c = slideLeft();
        return (a || b || c);
    }

    public boolean actionMoveRight() {
        boolean a, b, c;
        a = slideRight();
        b = compactRight();
        c = slideRight();
        return (a || b || c);
    }

    public boolean actionMoveUp() {
        boolean a, b, c;
        a = slideUp();
        b = compactUp();
        c = slideUp();
        return (a || b || c);
    }

    public boolean actionMoveDown() {
        boolean a, b, c;
        a = slideDown();
        b = compactDown();
        c = slideDown();
        return (a || b || c);
    }

    @Override
    public String toString() {
        return "TwoZeroFourEight:class\n" +
                "--------------------\n" +
                "|" + tiles[0] + "|" + tiles[4] + "|" + tiles[8] + "|" + tiles[12] + "|\n" +
                "|" + tiles[1] + "|" + tiles[5] + "|" + tiles[9] + "|" + tiles[13] + "|\n" +
                "|" + tiles[2] + "|" + tiles[6] + "|" + tiles[10] + "|" + tiles[14] + "|\n" +
                "|" + tiles[3] + "|" + tiles[7] + "|" + tiles[11] + "|" + tiles[15] + "|\n" +
                "--------------------\n";
    }

    class Transition implements Serializable {

        actions type;
        int val = 0, posStart = -1, posFinal = -1;

        Transition(actions action, int val, int posStart, int posFinal) {
            type = action;
            this.val = val;
            this.posStart = posStart;
            this.posFinal = posFinal;
        }

        Transition(actions action, int val, int posFinal) {
            type = action;
            this.val = val;
            this.posFinal = posFinal;
        }
    }
}
