package app.view;

import java.util.List;
import java.util.Scanner;

import model.Game;
import app.util.IO;
import app.util.InputValidator;

public class GameView {
	Scanner in = IO.getScanner();

	public Game addGameUI(Game game) {
		IO.clear();

		String title;
		Double averageGameplayTime;

		if (game == null) {
			game = new Game();

			title = InputValidator.promptAndParseString("Title of the game : ");
			averageGameplayTime = InputValidator.promptAndParseDouble("Enter the game's average match time : ", 10,
					200);
		} else {
			title = InputValidator.promptAndParseNullableString("Title of the game : ");
			averageGameplayTime = InputValidator.promptAndParseNullableDouble("Enter the game's average match time : ");
		}

		game.setTitle(title == null ? game.getTitle() : title);
		game.setAverageGameplayTime(averageGameplayTime == null ? game.getAverageGameplayTime() : averageGameplayTime);

		return game;
	}

	public Game listGames(List<Game> games) {
		Game selectedGame = null;

		if (games.size() == 0) {
			IO.clear();
			System.out.println(
					"+-------------------------------------------------------------------------------------------------------------+");
			System.out.println(
					"|                                                                                                             |");
			System.out.println(
					"|                                                No games found                                               |");
			System.out.println(
					"|                                                                                                             |");
			System.out.println(
					"+-------------------------------------------------------------------------------------------------------------+\n\n");

			IO.sysPause();
			return null;
		}

		do {
			IO.clear();
			System.out.println(
					"+--------------------------------------------------------------+");
			System.out.println(
					"|  Id  |           Name         |     Average gameplay time    |");
			System.out.println(
					"+--------------------------------------------------------------+");
			for (Game game : games) {
				System.out.printf("|  %-3d | %-22s | %-28.2f |\n",
						game.getId(),
						game.getTitle(),
						game.getAverageGameplayTime());
				System.out.println(
						"+--------------------------------------------------------------+");
			}

			System.out.print(
					"0 - Return to Game Menu \nPlease pick a game by entering their ID \nGame ID : ");
			try {
				int selectedId = IO.getScanner().nextInt();
				if (selectedId == 0)
					return selectedGame;
				selectedGame = games.stream()
						.filter(client -> client.getId() == selectedId)
						.findFirst()
						.orElse(null);

				if (selectedGame == null) {
					System.out.println("Invalid ID. Please try again.");
					IO.getScanner().next();
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter a valid number.");
				IO.getScanner().next();
				IO.getScanner().next();
			}

		} while (selectedGame == null);

		return selectedGame;
	}

	public int subGameMenu(Game game) {
		int input = -1;

		IO.clear();
		System.out.println(
				"+--------------------------------------------------------------+");
		System.out.println(
				"|  Id  |           Name         |     Average gameplay time    |");
		System.out.println(
				"+--------------------------------------------------------------+");
		System.out.printf("|  %-3d | %-22s | %-28.2f |\n",
				game.getId(),
				game.getTitle(),
				game.getAverageGameplayTime());
		System.out.println(
				"+--------------------------------------------------------------+");

		System.out.println("\t\t+---------------------------------------------+");
		System.out.println("\t\t|                                             |");
		System.out.println("\t\t|     1- Update game                          |");
		System.out.println("\t\t|     2- Delete game                          |");
		System.out.println("\t\t|     3- Back                                 |");
		System.out.println("\t\t|                                             |");
		System.out.println("\t\t+---------------------------------------------+");
		System.out.print("Pick your choice : ");

		try {
			input = in.nextInt();
			if (input < 1 || input > 3) {
				System.out.println("Please pick a choice between 1 and 3...");
				in.next();
			}
		} catch (Exception e) {
			System.out.println("Please pick a valid number...");
			in.next();
		}
		return input;
	}
}
