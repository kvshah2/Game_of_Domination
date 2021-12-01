package FocusUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

/**
 * This class was copied from https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java, with
 * the added addition of a color variable
 * 
 * Used to give the illusion of a rounded border
 * 
 * @author VonC, Peter Mortensen, Nathan French
 *
 */

public class RoundedBorder implements Border {

    private int radius;
    private Color color;

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    	g.setColor(color);
        g.fillRoundRect(x, y, width-1, height-1, radius, radius);
    }
}
