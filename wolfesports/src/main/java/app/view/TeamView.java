package app.view;

import java.util.List;

import model.Team;
import app.util.IO;
import app.util.InputValidator;

public class TeamView {

	public Team addTeamUI() {
		IO.clear();
		String teamName = InputValidator.promptAndParseString("Name of the team : ");
		String tag = InputValidator.promptAndParseString("Team's tag : ");

		Team team = new Team();

		team.setTeamName(teamName);
		team.setTag(tag);

		return team;
	}

	public Team listTeams(List<Team> teams) {
        Team selectedTeam = null;

        if (teams.size() == 0) {
            IO.clear();
            System.out.println(
                    "+-------------------------------------------------------------------------------------------------------------+");
            System.out.println(
                    "|                                                                                                             |");
            System.out.println(
                    "|                                                No teams found                                               |");
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
                    "+-------------------------------------------------+");
            System.out.println(
                    "|  Id  |          Team Name          |     Tag    |");
            System.out.println(
                    "+-------------------------------------------------+");
            for (Team team : teams) {
                System.out.printf("|  %-3d | %-27s | %-10s |\n",
                        team.getId(),
                        team.getTeamName(),
                        team.getTag()
                      );
                System.out.println(
                        "+-------------------------------------------------+");
            }

            System.out.print(
                    "0 - Return back \nPlease pick a team by entering their ID \nTeam ID : ");
            try {
                int selectedId = IO.getScanner().nextInt();
                if (selectedId == 0)
                    return selectedTeam;
                selectedTeam = teams.stream()
                        .filter(client -> client.getId() == selectedId)
                        .findFirst()
                        .orElse(null);

                if (selectedTeam == null) {
                    System.out.println("Invalid ID. Please try again.");
                    IO.getScanner().next();
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                IO.getScanner().next();
                IO.getScanner().next();
            }

        } while (selectedTeam == null);

        return selectedTeam;

    }
}
