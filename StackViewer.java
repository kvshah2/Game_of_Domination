package FocusUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

import FocusGame.Pawn;

/**
 * Displays pawns in a stack according to array of pawns given
 * @author Nathan French
 * @version 2021-11-21
 *
 */

public class StackViewer extends JPanel{

	public StackViewer() {
		setLayout(new GridLayout(5,1));
		setPreferredSize(new Dimension(100,250));
		setBackground(new Color(180,180,180));
	}
	
	private void addPawns(Color[] colors, Pawn[] pawns) {
		for (int i = 4; i >= 0; i--) {
			JLabel newPawn = new JLabel();
			newPawn.setMaximumSize(new Dimension(100,50));
			if ((pawns.length-1) >= i) {
				Color color = colors[pawns[i].getPlayer()];
				newPawn.setBackground(color);
				newPawn.setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, color.darker()));
				newPawn.setOpaque(true);
			}
			add(newPawn);
		}
		revalidate();
		repaint();
	}
	
	public void viewPiece(Color[] colors, Pawn[] pawns) {
		if (pawns != null) {
			addPawns(colors,pawns);
		} else {
			removeAll();
			revalidate();
			repaint();
		}
	}
}
