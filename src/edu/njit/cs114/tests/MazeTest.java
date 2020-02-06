package edu.njit.cs114.tests;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;

/**
 * A class to test Maze.java.
 * @author Koffman and Wolfgang
 */
public class MazeTest extends JFrame {

    // data field
    private TwoDimGrid theGrid; // a 2-D grid of buttons

    public static void main(String[] args) {
        String reply = JOptionPane.showInputDialog("Enter number of rows");
        int nRows = Integer.parseInt(reply);
        reply = JOptionPane.showInputDialog("Enter number of columns");
        int nCols = Integer.parseInt(reply);
        TwoDimGrid aGrid = new TwoDimGrid(nRows, nCols);
        new MazeTest(aGrid);
    }

    // Builds the GUI
    private MazeTest(TwoDimGrid aGrid) {
        theGrid = aGrid;
        getContentPane().add(aGrid, BorderLayout.CENTER);
        JTextArea instruct = new JTextArea(2, 20);
        instruct.setText("Toggle a button to change its color"
                + "\nPress SOLVE when ready");
        getContentPane().add(instruct, BorderLayout.NORTH);
        JButton solveButton = new JButton("SOLVE");
        solveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                solve();
            }
        });
        JButton resetButton = new JButton("RESET");
        resetButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                (new Maze(theGrid)).restore();
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(solveButton);
        bottomPanel.add(resetButton);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void solve() {
        Maze m = new Maze(theGrid);
        boolean found = m.findMazePath();
        if (found) {
            JOptionPane.showMessageDialog(null, "Success - reset maze and try again");
        } else {
            JOptionPane.showMessageDialog(null, "No path - reset maze and try again");
        }
    }
}

