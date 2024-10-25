package app.controller;

import app.view.GameView;
import app.view.interfaces.Menu;
import app.view.menu.GameMenu;
import model.Game;
import service.GameService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import app.util.AppContext;
import app.util.IO;

public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    ApplicationContext applicationContext = AppContext.getAppContext();
    GameService gameService = applicationContext.getBean("gameService", GameService.class);
    private Menu gameMenu = new GameMenu();
    private GameView gameView = new GameView();

    private boolean isRunning = true;

    public void startGameMenu() {
        isRunning = true;
        do {
            IO.clear();
            int choice = gameMenu.display();
            handleGameMenuChoice(choice);
        } while (isRunning );
    }

    public void startSubGameMenu(Game game) {
        boolean loop = true;
        do {
            IO.clear();
            int choice = gameView.subGameMenu(game);
            loop = handleSubGameMenuChoice(choice, game);
        } while (loop);
    }

    public void handleGameMenuChoice(int choice) {

        switch (choice) {
            case 1:
                try {
                    List<Game> games = gameService.getAllGames();
                    Game selectedGame = gameView.listGames(games);
                    if (selectedGame != null) {
                        startSubGameMenu(selectedGame);
                    }
                } catch (Exception e) {
                    logger.error("Error listing games", e);
                    System.out.println("Error lising games");
                }
                break;
            case 2:
                try {
                    Game addedGame = gameView.addGameUI(null);

                    gameService.addGame(addedGame);
                    System.out.println("Game added successfully!");

                } catch (Exception e) {
                    logger.error("Error adding game", e);
                    System.out.println("Error adding game");
                }
                break;
            case 3:
                isRunning = false;
                break;
            default:
                break;
        }
    }

    public boolean handleSubGameMenuChoice(int choice, Game game) {
        switch (choice) {
            case 1:
                try {
                    game = gameView.addGameUI(game);
                    gameService.updateGame(game);
                    System.out.println("Game updated successfully!");
                } catch (Exception e) {
                    logger.error("Error adding game", e);
                    System.out.println("Error adding game");
                }
                break;
            case 2:
                try {
                    gameService.deleteGame(game.getId());
                    System.out.println("Game deleted successfully!");
                } catch (Exception e) {
                    logger.error("Error deleting game", e);
                    System.out.println("Error deleting game");
                }

                break;
            default:
                break;
        }

        return false;
    }
}
