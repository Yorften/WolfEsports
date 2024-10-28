package app.controller;

import java.time.LocalDateTime;
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
import model.Warning;
import service.TeamService;
import service.WarningService;

public class TeamController {
    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    ApplicationContext applicationContext = AppContext.getAppContext();
    TeamService teamService = applicationContext.getBean("teamService", TeamService.class);
    WarningService warningService = applicationContext.getBean("warningService", WarningService.class);

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
                    List<Team> teams = teamService.getAllWarnedTeams();
                    Team selectedTeam = teamView.listTeams(teams);
                    if (selectedTeam != null) {
                        startSubTeamMenu(selectedTeam);
                    }
                } catch (Exception e) {
                    logger.error("Error listing teams", e);
                    System.out.println("Error lising teams");
                }
                break;
            case 3:
                try {
                    List<Team> teams = teamService.getAllNonWarnedTeams();
                    Team selectedTeam = teamView.listTeams(teams);
                    if (selectedTeam != null) {
                        startSubTeamMenu(selectedTeam);
                    }
                } catch (Exception e) {
                    logger.error("Error listing teams", e);
                    System.out.println("Error lising teams");
                }
                break;
            case 4:
                try {
                    Team addedTeam = teamView.addTeamUI(null);

                    teamService.addTeam(addedTeam);
                    System.out.println("Team added successfully!");

                } catch (Exception e) {
                    logger.error("Error adding team", e);
                    System.out.println("Error adding team");
                }
                break;
            case 5:
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
            case 3:
                Warning warning = applicationContext.getBean("warning", Warning.class);
                try {
                    warning.setIssueDate(LocalDateTime.now());
                    warningService.addWarning(warning);
                    team.setWarning(warning);
                    teamService.updateTeam(team);
                    System.out.println("Team warned successfully!");
                } catch (Exception e) {
                    logger.error("Error assigning a warning to team", e);
                    System.out.println("Error assigning a warning to team");
                }
                break;
            default:
                break;
        }

        return false;
    }
}
