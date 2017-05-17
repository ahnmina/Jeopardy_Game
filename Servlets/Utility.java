package cs4640work;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Utility {

	//method to get all the games by all users
	public static Games getAllGames() {
		
		List<String> usernames = getAllUsers();
		
		// read all txt files in data folder and look for files 
		// starting with username
		File file = new File("WebContent/WEB-INF/data");
		File[] gameFiles = file.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				String[] fileName = name.split(Pattern.quote("$"));
				String username = fileName[0];
				if (usernames.contains(username) && name.endsWith(".txt")) {
					return true;
				} else {
					return false;
				}
			}
		});

		Games games = new Games();
		List<GameBoard> gameBoards = new ArrayList<GameBoard>();
		for (File gameFile : gameFiles) {
			GameBoard gameBoard = new GameBoard();
			String fName = gameFile.getName().replaceAll(".txt", "");
			String userName = fName.split(Pattern.quote("$"))[0];
			String gameName = fName.split(Pattern.quote("$"))[1];
			gameBoard.setUsername(userName);
			gameBoard.setGameName(gameName);
			gameBoards.add(gameBoard);
		}
		games.setGames(gameBoards);
		return games;
	}
	
	//method that gets all registered users
	public static List<String> getAllUsers() {
		List<String> users = new ArrayList<String>();
		
		// load user-info to read all usernames
		File file = new File("WebContent/WEB-INF/data/user-info.txt");

		// reads the file created by selecting questions for jeopardy board
		Scanner inputFile = null;
		try {
			inputFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (inputFile.hasNext()){
			String input = inputFile.nextLine();
			String[] parts = input.split(",");
			users.add(parts[0]);
		}
		
		inputFile.close();
		
		return users;
	}

}
