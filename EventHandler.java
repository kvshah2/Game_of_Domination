package FocusUI;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Any events fired creates a new EventHandler object, which then performs the requested event
 * @author Nathan French
 * @version 2021-11-22
 *
 */

public class EventHandler implements ActionListener, MouseListener, ChangeListener {

	private GUI userInterface;
	private String command;
	
	public EventHandler(GUI UI,String command) {
		userInterface = UI;
		this.command = command;
	}
	
	public void mouseClicked(MouseEvent evt) {
		int row = Integer.parseInt(command.substring(0,1));
		int col = Integer.parseInt(command.substring(1));
		userInterface.selectSpace(row, col);
	}
	
	public void mouseEntered(MouseEvent evt) {
		int row = Integer.parseInt(command.substring(0,1));
		int col = Integer.parseInt(command.substring(1));
		userInterface.hoverSpace(row, col);
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		
	}
	
	public void stateChanged(ChangeEvent evt) {
		Object target = evt.getSource();
		userInterface.setMoveAmount((int) ((JSpinner)target).getValue());
		
	}
	
	public void actionPerformed(ActionEvent evt) {
		Object target = evt.getSource();
		
		if (target instanceof JButton) {

			if (command.equals("ShowStartPanel")) {
				userInterface.showGamePanel("2");
			} else if (command.equals("TitleScreen")) {
				userInterface.showGamePanel("1");
			} else if (command.equals("ShowLoadPanel")) {
				userInterface.showGamePanel("3");
			} else if (command.equals("ShowSettingsPanel")) {
				userInterface.showGamePanel("4");
			} else if (command.equals("Quit")) {
				System.exit(0);
			} else if (command.equals("StartGame")) {
				userInterface.setupGame();
			} else if (command.equals("LoadGame")) {
				userInterface.loadGame();
			} else if (command.equals("SaveGame")) {
				userInterface.saveGame();
			} else if (command.equals("QuitGame")) {
				userInterface.quitGame();
			} else if (command.equals("AddReserve")) {
				userInterface.addReserve();
			}
			
		} else if (target instanceof JComboBox) {
			
			if (command.equals("ChangeBoardColor")) {
				userInterface.changeBoardColor((String) ((JComboBox<?>) target).getSelectedItem());
			} else if (command.equals("ChangeFontSize")) {
				userInterface.changeFont((String) ((JComboBox<?>) target).getSelectedItem());
			} else if (command.equals("ChangePieceColor")) {
				userInterface.changePieceColor((String) ((JComboBox<?>) target).getSelectedItem());
			}
		} else if (target instanceof JTextField) {
			
		} else if (target instanceof JCheckBox) {
			if (command.substring(0,5).equals("SetDV")) {
				userInterface.setDifficultyVisual(command.substring(6),((JCheckBox) target).isSelected());
			}	
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
