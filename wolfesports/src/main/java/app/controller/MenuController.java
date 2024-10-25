package app.controller;

import app.util.IO;
import app.view.interfaces.Menu;
import app.view.menu.MainMenu;

public class MenuController {
    private Menu mainMenu = new MainMenu();

    GameController gameController = new GameController();
    TeamController teamController = new TeamController();
    TournamentController tournamentController = new TournamentController();
    PlayerController playerController = new PlayerController();

    private boolean isRunning = true;

    public void startMainMenu() {
        while (isRunning) {
            IO.clear();
            int choice = mainMenu.display();
            handleChoice(choice);
        }
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                tournamentController.startTournamentMenu();
                break;
            case 2:
                teamController.startTeamMenu();
                break;
            case 3:
                playerController.startPlayerMenu();
                break;
            case 4:
                gameController.startGameMenu();
                break;
            case 5:
                isRunning = false;
                System.out.println("Exiting...");
                break;
            default:
                break;
        }
    }

}
