package app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import app.util.AppContext;
import app.util.IO;
import app.view.TournamentView;
import app.view.interfaces.Menu;
import app.view.menu.TournamentMenu;
import model.Game;
import model.Tournament;
import service.GameService;
import service.TournamentService;

public class TournamentController {
    private static final Logger logger = LoggerFactory.getLogger(TournamentController.class);

    ApplicationContext applicationContext = AppContext.getAppContext();
    TournamentService tournamentService = applicationContext.getBean("tournamentService", TournamentService.class);
    GameService gameService = applicationContext.getBean("gameService", GameService.class);

    private Menu tournamentMenu = new TournamentMenu();
    private TournamentView tournamentView = new TournamentView();

    private boolean isRunning = true;

    public void startTournamentMenu() {
        isRunning = true;
        do {
            IO.clear();
            int choice = tournamentMenu.display();
            handleTournamentMenuChoice(choice);
        } while (isRunning);
    }

    public void startSubTournamentMenu(Tournament tournament) {
        boolean loop = true;
        do {
            IO.clear();
            int choice = tournamentView.subTournamentMenu(tournament);
            loop = handleSubTournamentMenuChoice(choice, tournament);
        } while (loop);
    }

    public void handleTournamentMenuChoice(int choice) {

        switch (choice) {
            case 1:
                try {
                    List<Tournament> tournaments = tournamentService.getAllTournaments();
                    Tournament selectedTournament = tournamentView.listTournaments(tournaments);
                    if (selectedTournament != null) {
                        startSubTournamentMenu(selectedTournament);
                    }
                } catch (Exception e) {
                    logger.error("Error listing tournaments", e);
                    System.out.println("Error lising tournaments");
                }
                break;
            case 2:
                try {
                    List<Game> games = gameService.getAllGames();
                    Tournament addedTournament = tournamentView.addTournamentUI(games, null);

                    tournamentService.addTournament(addedTournament);
                    System.out.println("Tournament added successfully!");

                } catch (Exception e) {
                    logger.error("Error adding tournament", e);
                    System.out.println("Error adding tournament");
                }
                break;
            case 3:
                isRunning = false;
                break;
            default:
                break;
        }
    }

    public boolean handleSubTournamentMenuChoice(int choice, Tournament tournament) {
        switch (choice) {
            case 1:
                try {
                    tournament = tournamentView.addTournamentUI(null, tournament);

                    tournamentService.updateTournament(tournament);
                    System.out.println("Tournament updated successfully!");
                } catch (Exception e) {
                    logger.error("Error adding tournament", e);
                    System.out.println("Error adding tournament");
                }
                break;
            case 2:
                try {
                    tournamentService.deleteTournament(tournament.getId());
                    System.out.println("Tournament deleted successfully!");
                } catch (Exception e) {
                    logger.error("Error deleting tournament", e);
                    System.out.println("Error deleting tournament");
                }

                break;
            default:
                break;
        }

        return false;
    }

}
