package FocusUI;

import javax.swing.*;
import javax.swing.event.ChangeListener;

import FocusGame.Game;
import FocusGame.Pawn;

import java.awt.*;

import static javax.swing.JOptionPane.showMessageDialog;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Main UI class of the project, intereacts directly with user
 * @author Nathan French
 * @version 2021-11-22
 *
 */

public class GUI extends JFrame{
	
	
	// Variables
	private JPanel menuPanel;
	private JPanel titlePanel;
	private JPanel startPanel;
	private JPanel loadPanel;
	private JPanel settingsPanel;
	private JPanel sessionPanel;
	private Board boardPanel;
	public static int fontSize;
	
	private static final String[] botNames = {"Nathan","Kenil","Waleed","Sakif","Ddzviti","Toby","Maria","Leon","Axelle","Teija",
			"Neelima","Seleucus","Spiridon","Herve","Pia","Javohir","Ami","Klavdiya","Sevket","Gura","Dorte","Silvija","Edelmiro",
			"Hartmut","Leucippus","Cahaya","Desiderio","Rani","Mollie","Daisy","Zayden","Dusko","Adi","Anneta","Haik","Rut"};
	
	CardLayout cl = new CardLayout();
	
	private Game game;
	
	/**
	 * Constructor for main user interface, all ui is constructed here
	 */
	public GUI() {
		setTitle("Focus");
		fontSize = 18;
		
		boardPanel = new Board(this);
		
		menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(300,700));
		menuPanel.setLayout(cl);
		titlePanel = createTitlePanel();
		startPanel = createStartPanel();
		loadPanel = createLoadPanel();
		settingsPanel = createSettingsPanel();
		sessionPanel = createSessionPanel();
		menuPanel.add(titlePanel,"1");
		menuPanel.add(startPanel,"2");
		menuPanel.add(loadPanel,"3");
		menuPanel.add(settingsPanel,"4");
		menuPanel.add(sessionPanel,"5");
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(boardPanel,BorderLayout.CENTER);
		getContentPane().add(menuPanel,BorderLayout.WEST);
		pack();

		setSize(1100,800);
		setDefaultCloseOperation(3);
		setResizable(false);
		setVisible(true);
		
		game = new Game(this,boardPanel);
		updateSaveList();
	}
	
	/**
	 * Creates the title panel for the game
	 * @return GamePanel TitlePanel
	 */
	private GamePanel createTitlePanel() {
		GamePanel titlePanel = new GamePanel("Focus");
		
		JButton startButton = new JButton("Start Game");
		startButton.setMaximumSize(new Dimension(200,50));
		startButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		startButton.addActionListener(new EventHandler(this,"ShowStartPanel"));
		
		JButton loadButton = new JButton("Load Game");
		loadButton.setMaximumSize(new Dimension(200,50));
		loadButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		loadButton.addActionListener(new EventHandler(this,"ShowLoadPanel"));
		
		JButton displayButton = new JButton("Display Settings");
		displayButton.setMaximumSize(new Dimension(200,50));
		displayButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		displayButton.addActionListener(new EventHandler(this,"ShowSettingsPanel"));
		
		JButton quitButton  = new JButton("Quit");
		quitButton.setMaximumSize(new Dimension(200,50));
		quitButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		quitButton.addActionListener(new EventHandler(this,"Quit"));
		
		titlePanel.add(startButton);
		titlePanel.add(loadButton);
		titlePanel.add(displayButton);
		titlePanel.add(quitButton);
		
		return(titlePanel);
	}
	
	/**
	 * Creates the start game panel for the game
	 * @return GamePanel StartPanel
	 */
	private GamePanel createStartPanel() {
		GamePanel startPanel = new GamePanel("Start");
		
		// Repeats 4 times to create each player panel
		for (int i = 1; i < 5; i ++) {
			JPanel playerPanel = new JPanel();
			playerPanel.setLayout(new FlowLayout());
			playerPanel.setMaximumSize(new Dimension(250,80));
			playerPanel.setOpaque(false);
			
			JLabel playerNumber = new JLabel("Player " + i);
			playerNumber.setFont(new Font("lol",Font.PLAIN,fontSize));
			
			JTextField playerName = new JTextField();
			playerName.setPreferredSize(new Dimension(70,30));
			
			JCheckBox cpuCheck = new JCheckBox("CPU");
			cpuCheck.setFont(new Font("lol",Font.PLAIN,fontSize));
			cpuCheck.addActionListener(new EventHandler(this,"SetDV:" + i));
			
			String[] difficulties = { "Easy", "Hard" };
			JComboBox<String> cpuDifficulty = new JComboBox<String>(difficulties);
			cpuDifficulty.setFont(new Font("lol",Font.PLAIN,fontSize));
			cpuDifficulty.setPreferredSize(new Dimension(100,30));
			cpuDifficulty.setVisible(false);
			
			playerPanel.add(playerNumber);
			playerPanel.add(playerName);
			playerPanel.add(cpuCheck);
			playerPanel.add(cpuDifficulty);
			
			startPanel.add(playerPanel);
		}
		
		JButton startButton = new JButton("Start Game");
		startButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		startButton.setMaximumSize(new Dimension(200,50));
		startButton.addActionListener(new EventHandler(this,"StartGame"));
		
		JButton backButton = new JButton("Back to Menu");
		backButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		backButton.setMaximumSize(new Dimension(200,50));
		backButton.addActionListener(new EventHandler(this,"TitleScreen"));

		startPanel.add(startButton);
		startPanel.add(backButton);

		return(startPanel);
	}
	
	/**
	 * Creates the load game panel for the game
	 * @return GamePanel LoadPanel
	 */
	private GamePanel createLoadPanel() {
		GamePanel loadPanel = new GamePanel("Load");
		
		JLabel saveLabel = new JLabel("Save List");
		saveLabel.setFont(new Font("lol",Font.PLAIN,fontSize));
		saveLabel.setMaximumSize(new Dimension(200,50));
		
		JComboBox<String> gameSaves = new JComboBox<String>();
		gameSaves.setFont(new Font("lol",Font.PLAIN,fontSize));
		gameSaves.setMaximumSize(new Dimension(300,50));
		
		JButton loadButton = new JButton("Load Game");
		loadButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		loadButton.setMaximumSize(new Dimension(200,50));
		loadButton.addActionListener(new EventHandler(this,"LoadGame"));
		
		JButton backButton = new JButton("Back to Menu");
		backButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		backButton.setMaximumSize(new Dimension(200,50));
		backButton.addActionListener(new EventHandler(this,"TitleScreen"));
		
		JLabel saveInfoLabel = new JLabel("<html>" + "Remember to have 'Saves' folder in the same dictionary as the application!" + "</html>");
		saveInfoLabel.setFont(new Font("lol",Font.PLAIN,fontSize));
		saveInfoLabel.setMaximumSize(new Dimension(200,100));

		loadPanel.add(saveLabel);
		loadPanel.add(gameSaves);
		loadPanel.add(loadButton);
		loadPanel.add(backButton);
		loadPanel.add(saveInfoLabel);

		return(loadPanel);
	}
	
	/**
	 * Creates the display settings panel for the game
	 * @return GamePanel SettingsPanel
	 */
	private GamePanel createSettingsPanel() {
		GamePanel settingsPanel = new GamePanel("Settings");
		
		JLabel pieceColorText = new JLabel("Piece Colour");
		pieceColorText.setFont(new Font("lol",Font.PLAIN,fontSize));
		pieceColorText.setMaximumSize(new Dimension(200,50));
		
		String[] pieceColors = {"Blue, yellow, green, red", "Orange, pink, cyan, yellow"};
		JComboBox<String> pieceColorSetting = new JComboBox<String>(pieceColors);
		pieceColorSetting.setFont(new Font("lol",Font.PLAIN,fontSize));
		pieceColorSetting.setMaximumSize(new Dimension(300,50));
		pieceColorSetting.addActionListener(new EventHandler(this,"ChangePieceColor"));
		
		JLabel boardColorText = new JLabel("Board Colour");
		boardColorText.setFont(new Font("lol",Font.PLAIN,fontSize));
		boardColorText.setMaximumSize(new Dimension(200,50));
		
		String[] colors = { "Gray", "White", "Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink" };
		JComboBox<String> boardColorSetting = new JComboBox<String>(colors);
		boardColorSetting.setFont(new Font("lol",Font.PLAIN,fontSize));
		boardColorSetting.setMaximumSize(new Dimension(300,50));
		boardColorSetting.addActionListener(new EventHandler(this,"ChangeBoardColor"));
		
		JLabel fontSizeText = new JLabel("Font Size");
		fontSizeText.setFont(new Font("lol",Font.PLAIN,fontSize));
		fontSizeText.setMaximumSize(new Dimension(200,50));
		
		String[] sizes = { "Small", "Medium", "Large" };
		JComboBox<String> fontSizeSetting = new JComboBox<String>(sizes);
		fontSizeSetting.setSelectedIndex(1);
		fontSizeSetting.setFont(new Font("lol",Font.PLAIN,fontSize));
		fontSizeSetting.setMaximumSize(new Dimension(300,50));
		fontSizeSetting.addActionListener(new EventHandler(this,"ChangeFontSize"));
		
		JButton backButton = new JButton("Back to Menu");
		backButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		backButton.setMaximumSize(new Dimension(200,50));
		backButton.addActionListener(new EventHandler(this,"TitleScreen"));
		
		settingsPanel.add(pieceColorText);
		settingsPanel.add(pieceColorSetting);
		settingsPanel.add(boardColorText);
		settingsPanel.add(boardColorSetting);
		settingsPanel.add(fontSizeText);
		settingsPanel.add(fontSizeSetting);
		settingsPanel.add(backButton);

		return(settingsPanel);
	}
	
	private GamePanel createSessionPanel() {
		GamePanel sessionPanel = new GamePanel();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		for (int i = 1; i < 5; i ++) {
			JPanel playerPanel = new JPanel();
			playerPanel.setLayout(new FlowLayout());
			playerPanel.setMaximumSize(new Dimension(250,40));
			playerPanel.setBackground(new Color(214,214,214));
			
			JLabel playerNumberAndName = new JLabel();
			playerNumberAndName.setFont(new Font("lol",Font.PLAIN,fontSize));
			
			JLabel cpuDifficulty = new JLabel();
			cpuDifficulty.setFont(new Font("lol",Font.PLAIN,fontSize));
			cpuDifficulty.setPreferredSize(new Dimension(100,30));
			
			playerPanel.add(playerNumberAndName);
			playerPanel.add(cpuDifficulty);
			c.gridy = i-1;
			sessionPanel.add(playerPanel,c);
		}
		
		JLabel statusMessage = new JLabel("Player's 0 turn!");
		statusMessage.setFont(new Font("lol",Font.PLAIN,fontSize));
		statusMessage.setMaximumSize(new Dimension(200,50));
		
		JLabel reserveCount = new JLabel("0, 0, 0, 0");
		reserveCount.setFont(new Font("lol",Font.PLAIN,fontSize));
		reserveCount.setMaximumSize(new Dimension(200,40));

		JLabel captureCount = new JLabel("0, 0, 0, 0");
		captureCount.setFont(new Font("lol",Font.PLAIN,fontSize));
		captureCount.setMaximumSize(new Dimension(200,40));
		
		JPanel spacePanel = new JPanel();
		spacePanel.setLayout(new FlowLayout());
		spacePanel.setMaximumSize(new Dimension(250,350));
		spacePanel.setBackground(new Color(214,214,214));
		
		StackViewer stackViewer1 = new StackViewer();
		StackViewer stackViewer2 = new StackViewer();
		
		JLabel moveLabel = new JLabel("Move: ");
		moveLabel.setFont(new Font("lol",Font.PLAIN,fontSize));
		moveLabel.setMaximumSize(new Dimension(100,50));
		
		JSpinner moveAmount = new JSpinner(new SpinnerNumberModel(1,1,5,1));
		moveAmount.setFont(new Font("lol",Font.PLAIN,fontSize));
		moveAmount.setPreferredSize(new Dimension(50,30));
		moveAmount.addChangeListener((ChangeListener) new EventHandler(this,"SetMoveAmount"));
		
		JButton reserveButton = new JButton("Place reserve");
		reserveButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		reserveButton.setMaximumSize(new Dimension(120,30));
		reserveButton.addActionListener(new EventHandler(this,"AddReserve"));
		
		spacePanel.add(stackViewer1);
		spacePanel.add(stackViewer2);
		spacePanel.add(moveLabel);
		spacePanel.add(moveAmount);
		spacePanel.add(reserveButton);
		
		JTextField saveName = new JTextField("SaveGame");
		saveName.setMaximumSize(new Dimension(180,30));
		
		JButton saveButton = new JButton("Save Game");
		saveButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		saveButton.setMaximumSize(new Dimension(200,50));
		saveButton.addActionListener(new EventHandler(this,"SaveGame"));

		JButton quitButton = new JButton("Quit");
		quitButton.setFont(new Font("lol",Font.PLAIN,fontSize));
		quitButton.setMaximumSize(new Dimension(200,50));
		quitButton.addActionListener(new EventHandler(this,"QuitGame"));
		
		c.gridy = 4;
		sessionPanel.add(statusMessage,c);
		c.gridy = 5;
		sessionPanel.add(reserveCount,c);
		c.gridy = 6;
		sessionPanel.add(captureCount,c);
		c.gridy = 7;
		c.ipady = 300;
		sessionPanel.add(spacePanel,c);
		c.gridy = 8;
		c.ipady = 25;
		sessionPanel.add(saveName,c);
		c.gridy = 9;
		sessionPanel.add(saveButton,c);
		c.gridy = 10;
		sessionPanel.add(quitButton,c);
		
		return(sessionPanel);
	}
	
	/**
	 * Switchs to a different game panel depending on param
	 * @param panel
	 */
	public void showGamePanel(String panel) {
		if (panel == "3") updateSaveList();
		cl.show(menuPanel,panel);
	}
	
	/**
	 * Calls the board to change its color
	 * @param color
	 */
	public void changeBoardColor(String color) {
		boardPanel.changeColor(color);
	}
	
	public void changePieceColor(String pieceColors) {
		game.changePieceColors(pieceColors);
	}
	
	private void iteratePanels(Component childPanel) {
		for (Component v : ((Container) childPanel).getComponents()) {
			if (v instanceof JPanel || v instanceof GamePanel) {
				iteratePanels(v);
			} else {
				if (v.getParent().equals(sessionPanel) || !(v.getParent().getComponent(0).equals(v)))
				v.setFont(new Font("lol",Font.PLAIN,fontSize));
			}
		}
	}

	/**
	 * Changes the global font variables, followed by changing the font size of all elements
	 * @param size
	 */
	public void changeFont(String size) {
		if (size.equals("Small")) {
			fontSize = 14;
		} else if (size.equals("Medium")) {
			fontSize = 18;
		} else if (size.equals("Large")) {
			fontSize = 23;
		}
		iteratePanels(menuPanel);
	}
	
	/**
	 * Shows and hides difficulty select option depending if player is cpu or not
	 * @param player
	 * @param visible
	 */
	public void setDifficultyVisual(String player, Boolean visible) {
		JPanel playerPanel = (JPanel) startPanel.getComponent(Integer.parseInt(player));
		((JComponent) playerPanel.getComponent(3)).setVisible(visible);
	}
	
	@SuppressWarnings("unchecked")
	public void updateSaveList() {
		if (game != null) {
			String[] saves = game.getSaves();
			JComboBox<String> saveBox = ((JComboBox<String>) loadPanel.getComponent(2));
			saveBox.removeAllItems();
			if (saves != null) {
				for (String save: saves) {
					saveBox.addItem(save.substring(0,save.length()-4));	
				}
			}
		}
	}
	
	public void setPlayerInfo(Object[][] playerData) {
		String[] difficulties = {"","[Easy]","[Hard]"};
		Color[] colors = game.getColors();
		for (int i = 0; i < 4; i++) {
			String name = (String) playerData[i][0];
			int difficulty = (int) playerData[i][1];
			JPanel playerPanel = (JPanel) sessionPanel.getComponent(i);
			((JLabel) playerPanel.getComponent(0)).setText((i+1) + ": " + name);
			((JLabel) playerPanel.getComponent(0)).setForeground(colors[i].darker());
			((JLabel) playerPanel.getComponent(1)).setText(difficulties[difficulty]);
		}
	}
	
	/**
	 * Checks if players are valid before sending player data to the game class to start a new game
	 */
	public void setupGame() {
		Object[][] playerData = new Object[4][2];
		int incompleteData = 0;
		int botCount = 0;
		for (int i = 0; i < 4; i++) {
			
			JPanel playerPanel = (JPanel) startPanel.getComponent(i+1);
			String playerName = ((JTextField) playerPanel.getComponent(1)).getText();
			Boolean cpuCheck = ((JCheckBox) playerPanel.getComponent(2)).isSelected();
			String difficulty = (String) ((JComboBox<?>) playerPanel.getComponent(3)).getSelectedItem();
			
			if (playerName.length() < 19) {
				
				if (cpuCheck) {
					botCount ++;
					if (difficulty == "Easy") {
						playerData[i][1] = 1;
					} else {
						playerData[i][1] = 2;
					}
					if (playerName.isEmpty()) {
						playerData[i][0] = botNames[ThreadLocalRandom.current().nextInt(0, 36)];
					} else {
						playerData[i][0] = playerName;	
					}
						
				} else {
					if (playerName.isEmpty()) {
						playerData[i][0] = "Player " + (i+1);
					} else {
						playerData[i][0] = playerName;	
					}
					playerData[i][1] = 0;	
				}
				
			} else {
				incompleteData = i+1;
				break;
			}
			
		}
		if (incompleteData == 0 && botCount < 4) {
			setPlayerInfo(playerData);
			game.setupGame(playerData);
			cl.show(menuPanel,"5");
		} else if (botCount < 4){
			showMessageDialog(null, "Invalid name for player " + incompleteData);
		}else {
			showMessageDialog(null, "No human players");	
		}
		
	}
	
	public void loadGame() {
		String fileName = (String) ((JComboBox<?>) loadPanel.getComponent(2)).getSelectedItem();
		setPlayerInfo(game.loadGame(fileName+".txt"));
		((JTextField) sessionPanel.getComponent(8)).setText(fileName);
		cl.show(menuPanel,"5");
	}
	
	public void saveGame() {
		String fileName = (String) ((JTextField) sessionPanel.getComponent(8)).getText();
		fileName = fileName + ".txt";
		String[] saves = game.getSaves();
		Boolean continueSave = true;
		for (String save: saves) {
			if (save.equals(fileName)) {
				int result = JOptionPane.showConfirmDialog(this,"Name currently used by another save. Overwrite previous save?", "Save Game",
			               JOptionPane.YES_NO_OPTION,
			               JOptionPane.QUESTION_MESSAGE);
				continueSave = (result == JOptionPane.YES_OPTION);
			}
		}
		if (continueSave) game.saveGame(fileName);
		
	}
	
	public void quitGame() {
		int result = 0;
		if (!game.checkIfSaved()) {
			result = JOptionPane.showConfirmDialog(this,"Game state has not been saved. Quit without saving?", "Save Game",
		               JOptionPane.YES_NO_OPTION,
		               JOptionPane.QUESTION_MESSAGE);
		}
		if (result == JOptionPane.YES_OPTION) {
			cl.show(menuPanel,"1");
			game.clearData();
			viewStack(null, null,false);
		}
	}
	
	public void selectSpace(int row, int col) {
		game.selectSpace(row,col);
	}
	
	public void hoverSpace(int row, int col) {
		game.hoverSpace(row,col);
	}
	
	public void viewStack(Color[] color, Pawn[] pawns, Boolean hover) {
		JPanel spacePanel = (JPanel) sessionPanel.getComponent(7);
		
		StackViewer stackPanel = null;
		if (hover) {
			stackPanel = (StackViewer) spacePanel.getComponent(1);	
			stackPanel.viewPiece(color, null);
		} else {
			stackPanel = (StackViewer) spacePanel.getComponent(0);	
		}
		stackPanel.viewPiece(color, pawns);
	}
	
	public void updateRCCount(int[] reserve, int[] capture) {
		JLabel reserveCount = (JLabel) sessionPanel.getComponent(5);
		JLabel captureCount = (JLabel) sessionPanel.getComponent(6);
		String newRCount = "Reserve: ";
		String newCCount = "Capture: ";
		for (int i=0; i < reserve.length; i++) {
			newRCount += reserve[i] + ", ";
			newCCount += capture[i] + ", ";
		}
		newRCount = newRCount.substring(0,19);
		newCCount = newCCount.substring(0,19);
		reserveCount.setText(newRCount);
		captureCount.setText(newCCount);
	}
	
	public void updateStatus(int turn) {
		JLabel statusMessage = (JLabel) sessionPanel.getComponent(4);
		if (turn > 0) {
			statusMessage.setText("Player's "+turn+" turn!");
		} else if (turn < 0) {
			statusMessage.setText("Player "+turn*-1+" has won!");
		}
	}
	
	public void updateMoveAmount(int maxAmount) {
		JPanel spacePanel = (JPanel) sessionPanel.getComponent(7);
		JSpinner MoveAmount = (JSpinner) spacePanel.getComponent(3);
		MoveAmount.setModel(new SpinnerNumberModel(maxAmount,1,maxAmount,1));
	}
	
	public void setMoveAmount(int amount) {
		game.setSplitAmount(amount);
	}
	
	public void addReserve() {
		game.addReserve();
	}
	
}
