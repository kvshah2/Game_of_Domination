package FocusGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Deals with system files directly by reading and writing text files
 * @author Nathan French
 * @version 2021-11-15
 *
 */

public class SaveStorage {
	
	private String gamePath = System.getProperty("user.dir");
	private String path;
	
	public SaveStorage() {
		path = gamePath + "\\Saves";
	}
	
	public void saveGameData(String fileName, String[] saveData) {
		try {
			@SuppressWarnings("unused")
			File saveFile = new File(path + "\\" + fileName);
			FileWriter writer = new FileWriter(path + "\\" + fileName);
			for (String line : saveData) {
				writer.write(line == null ? "\n" : line+"\n");
			}
			writer.close();
		} catch (IOException e) {
			System.out.println("Error saving file");
			e.printStackTrace();	
		}
	}
	
	public ArrayList<String> loadGameData(String fileName) {
		
		ArrayList<String> gameData = new ArrayList<>();
		try {
			File saveFile = new File(path + "\\" + fileName);
			Scanner reader = new Scanner(saveFile);
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				gameData.add(data);
			}	
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		return(gameData);
		
	}
	
	public String[] getSaves() {
		File saveFiles = new File(path);
		return(saveFiles.list());	
	}
	
}
