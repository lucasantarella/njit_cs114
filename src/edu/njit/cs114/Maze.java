package edu.njit.cs114;


import java.awt.*;

/**
 * Class that solves maze problems with backtracking.
 *
 * @author Koffman and Wolfgang
 **/
public class Maze {

    public static Color PATH = Color.green;
    public static Color BACKGROUND = Color.white;
    public static Color NON_BACKGROUND = Color.red;
    public static Color TEMPORARY = Color.black;

    /**
     * The maze
     */
    private TwoDimGrid maze;

    public Maze(TwoDimGrid m) {
        maze = m;
    }

    /**
     * Wrapper method.
     */
    public boolean findMazePath() {
        return findMazePath(0, 0); // (0, 0) is the start point.
    }

    /**
     * PROBLEM 1
     * Attempts to find a path through point (x, y).
     *
     * @param x The x-coordinate of current point
     * @param y The y-coordinate of current point
     * @return If a path through (x, y) is found, true; otherwise, false
     * @pre Possible path cells are in NON_BACKGROUND color; barrier cells are in
     * BACKGROUND color.
     * @post If a path is found, all cells on it are set to the PATH color; all
     * cells that were visited but are not on the path are in the TEMPORARY
     * color.
     */
    public boolean findMazePath(int x, int y) {
        if (maze.getColor(x, y) == Maze.TEMPORARY)
            return false;

        if (maze.getColor(x, y) == Maze.NON_BACKGROUND || maze.getColor(x, y) == Maze.TEMPORARY) {
            if (x + 1 == maze.getNCols() && y + 1 == maze.getNRows()) {
                return true;
            } else {
                maze.recolor(x, y, Maze.TEMPORARY);
                if (x - 1 >= 0 && this.findMazePath(x - 1, y)) {
                    maze.recolor(x, y, Maze.PATH);
                    maze.recolor(x - 1, y, Maze.PATH);
                    return true;
                }

                if (x + 1 < maze.getNCols() && this.findMazePath(x + 1, y)) {
                    maze.recolor(x, y, Maze.PATH);
                    maze.recolor(x + 1, y, Maze.PATH);
                    return true;
                }

                if (y - 1 >= 0 && this.findMazePath(x, y - 1)) {
                    maze.recolor(x, y, Maze.PATH);
                    maze.recolor(x, y - 1, Maze.PATH);
                    return true;
                }

                if (y + 1 < maze.getNRows() && this.findMazePath(x, y + 1)) {
                    maze.recolor(x, y, Maze.PATH);
                    maze.recolor(x, y + 1, Maze.PATH);
                    return true;
                }
            }

        }

        return false;
    }

    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }

    public void restore() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
    }
}
