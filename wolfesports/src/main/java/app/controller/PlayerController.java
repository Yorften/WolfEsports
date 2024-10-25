package app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import app.util.AppContext;
import app.util.IO;
import app.view.PlayerView;
import app.view.interfaces.Menu;
import app.view.menu.PlayerMenu;
import model.Player;
import model.Team;
import service.PlayerService;
import service.TeamService;

public class PlayerController {
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    ApplicationContext applicationContext = AppContext.getAppContext();
    TeamService teamService = applicationContext.getBean("teamService", TeamService.class);
    PlayerService playerService = applicationContext.getBean("playerService", PlayerService.class);

    private Menu playerMenu = new PlayerMenu();
    private PlayerView playerView = new PlayerView();

    private boolean isRunning = true;

    public void startPlayerMenu() {
        isRunning = true;
        do {
            IO.clear();
            int choice = playerMenu.display();
            handlePlayerMenuChoice(choice);
        } while (isRunning);
    }

    public void startSubPlayerMenu(Player player) {
        boolean loop = true;
        do {
            IO.clear();
            int choice = playerView.subPlayerMenu(player);
            loop = handleSubPlayerMenuChoice(choice, player);
        } while (loop);
    }

    public void handlePlayerMenuChoice(int choice) {

        switch (choice) {
            case 1:
                try {
                    List<Player> players = playerService.getAllPlayers();
                    Player selectedPlayer = playerView.listPlayers(players);
                    if (selectedPlayer != null) {
                        startSubPlayerMenu(selectedPlayer);
                    }
                } catch (Exception e) {
                    logger.error("Error listing players", e);
                    System.out.println("Error lising players");
                    IO.sysPause();
                }
                break;
            case 2:
                try {
                    List<Team> teams = teamService.getAllTeams();
                    Player addedPlayer = playerView.addPlayerUI(teams, null);

                    playerService.addPlayer(addedPlayer);
                    System.out.println("Team added successfully!");

                } catch (Exception e) {
                    logger.error("Error adding team", e);
                    System.out.println("Error adding team");
                }
                break;
            case 3:
                isRunning = false;
                break;
            default:
                break;
        }
    }

    public boolean handleSubPlayerMenuChoice(int choice, Player player) {
        switch (choice) {
            case 1:
                try {
                    List<Team> teams = teamService.getAllTeams();
                    player = playerView.addPlayerUI(teams, player);
                    playerService.updatePlayer(player);
                    System.out.println("Player updated successfully!");
                } catch (Exception e) {
                    logger.error("Error adding player", e);
                    System.out.println("Error adding player");
                }
                break;
            case 2:
                try {
                    playerService.deletePlayer(player.getId());
                    System.out.println("Player deleted successfully!");
                } catch (Exception e) {
                    logger.error("Error deleting player", e);
                    System.out.println("Error deleting player");
                }
                break;
            default:
                break;
        }

        return false;
    }
}