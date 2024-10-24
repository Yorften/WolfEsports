package app.view;

import java.util.List;

import model.Game;
import app.util.IO;
import app.util.InputValidator;

public class GameView {


    public Game addGameUI() {
        IO.clear();
        String title = InputValidator.promptAndParseString("Title of the game : ");
        Double averageGameplayTime = InputValidator.promptAndParseDouble("Enter the game's average match time : ", 10, 200);

        Game game = new Game();

        game.setTitle(title);
        game.setAverageGameplayTime(averageGameplayTime);

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
                System.out.printf("|  %-3d | %-22s | %-28d |\n",
                        game.getId(),
                        game.getTitle(),
                        game.getAverageGameplayTime()
                      );
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
}
