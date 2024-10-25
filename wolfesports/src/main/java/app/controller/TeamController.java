package app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import app.util.AppContext;
import app.util.IO;
import app.view.TeamView;
import app.view.interfaces.Menu;
import app.view.menu.TeamMenu;
import model.Team;
import service.TeamService;

public class TeamController {
    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    ApplicationContext applicationContext = AppContext.getAppContext();
    TeamService teamService = applicationContext.getBean("teamService", TeamService.class);

    private Menu teamMenu = new TeamMenu();
    private TeamView teamView = new TeamView();

    private boolean isRunning = true;

    public void startTeamMenu() {
        isRunning = true;
        do {
            IO.clear();
            int choice = teamMenu.display();
            handleTeamMenuChoice(choice);
        } while (isRunning);
    }

    public void startSubTeamMenu(Team team) {
        boolean loop = true;
        do {
            IO.clear();
            int choice = teamView.subTeamMenu(team);
            loop = handleSubTeamMenuChoice(choice, team);
        } while (loop);
    }

    public void handleTeamMenuChoice(int choice) {

        switch (choice) {
            case 1:
                try {
                    List<Team> teams = teamService.getAllTeams();
                    Team selectedTeam = teamView.listTeams(teams);
                    if (selectedTeam != null) {
                        startSubTeamMenu(selectedTeam);
                    }
                } catch (Exception e) {
                    logger.error("Error listing teams", e);
                    System.out.println("Error lising teams");
                }
                break;
            case 2:
                try {
                    Team addedTeam = teamView.addTeamUI(null);

                    teamService.addTeam(addedTeam);
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

    public boolean handleSubTeamMenuChoice(int choice, Team team) {

        switch (choice) {
            case 1:
                try {
                    team = teamView.addTeamUI(team);
                    teamService.updateTeam(team);
                    System.out.println("Team updated successfully!");
                } catch (Exception e) {
                    logger.error("Error adding team", e);
                    System.out.println("Error adding team");
                }
                break;
            case 2:
                try {
                    teamService.deleteTeam(team.getId());
                    System.out.println("Team deleted successfully!");
                } catch (Exception e) {
                    logger.error("Error deleting team", e);
                    System.out.println("Error deleting team");
                }

                break;
            default:
                break;
        }

        return false;
    }
}
