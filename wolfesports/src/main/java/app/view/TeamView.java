package app.view;

import java.util.List;
import java.util.Scanner;

import model.Team;
import app.util.IO;
import app.util.InputValidator;

public class TeamView {
	Scanner in = IO.getScanner();

	public Team addTeamUI(Team team) {
		IO.clear();

		String teamName;
		String tag;

		if (team == null) {
			team = new Team();

			teamName = InputValidator.promptAndParseString("Name of the team : ");
			tag = InputValidator.promptAndParseString("Team's tag : ");
		} else {
			teamName = InputValidator.promptAndParseNullableString("Name of the team : ");
			tag = InputValidator.promptAndParseNullableString("Team's tag : ");
		}

		team.setTeamName(teamName == null ? team.getTeamName() : teamName);
		team.setTag(tag == null ? team.getTag() : tag);

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
						team.getTag());
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

	public int subTeamMenu(Team team) {
		int input = -1;

		IO.clear();
		System.out.println(
				"+-------------------------------------------------+");
		System.out.println(
				"|  Id  |          Team Name          |     Tag    |");
		System.out.println(
				"+-------------------------------------------------+");
		System.out.printf("|  %-3d | %-27s | %-10s |\n",
				team.getId(),
				team.getTeamName(),
				team.getTag());
		System.out.println(
				"+-------------------------------------------------+");

		System.out.println("\t\t+---------------------------------------------+");
		System.out.println("\t\t|                                             |");
		System.out.println("\t\t|     1- Update team                          |");
		System.out.println("\t\t|     2- Delete team                          |");
		System.out.println("\t\t|     3- Warn a team                          |");
		System.out.println("\t\t|     4- Back                                 |");
		System.out.println("\t\t|                                             |");
		System.out.println("\t\t+---------------------------------------------+");
		System.out.print("Pick your choice : ");

		try {
			input = in.nextInt();
			if (input < 1 || input > 4) {
				System.out.println("Please pick a choice between 1 and 4...");
				in.next();
			}
		} catch (Exception e) {
			System.out.println("Please pick a valid number...");
			in.next();
		}
		return input;
	}
}
