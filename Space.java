package FocusUI;

import java.awt.Color;
import javax.swing.JLabel;

/**
 * Custom JLabel class for the board spaces
 * @author Nathan French
 * @version 2021-11-03
 *
 */

public class Space extends JLabel{

	public Space() {
		super();
	}
	
	public void setSpaceColor(Color color) {
		setBorder(new RoundedBorder(50,color));
	}
	
	public void setBorderColor(Color color) {
		setBackground(color);
	}
}
