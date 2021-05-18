package mastermind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author Alejandro
 */
// custom button to display clues for Mastermind
public class ChinchetaPista extends JComponent {
    // data member
    private int  currColor;

    // constructor
    public ChinchetaPista() {
        currColor = 0;
    }

    //draw component
    public void paint(Graphics g) {
        if (currColor == 1) {
            g.setColor(Color.WHITE);
            g.fillOval(0,0,10,10);
        } else if (currColor == 2) {
            g.setColor(Color.BLACK);
            g.fillOval(0,0,10,10);
        } else {
            g.setColor(Color.BLACK);
            g.drawOval(0,0,10,10);
        }
    }

    // get color
    public int getCurrColor() {
        return currColor;
    }

    // set color and redraw
    public void setCurrColor(int newColor) {
        currColor = newColor;
        repaint();
    }

    //set default size
    public Dimension getMinimumSize() {
        return new Dimension(10,10);
    }
    public Dimension getPreferredSize() {
        return new Dimension(10,10);
    }
}