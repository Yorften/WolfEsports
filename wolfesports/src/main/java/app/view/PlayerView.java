package app.view;

import java.util.List;

import model.Player;
import model.Team;
import app.util.IO;
import app.util.InputValidator;

public class PlayerView {
	private TeamView teamView = new TeamView();

	public Player addPlayerUI(List<Team> teams) {
		IO.clear();
		String firstName = InputValidator.promptAndParseString("First name : ");
		String lastName = InputValidator.promptAndParseString("Last name : ");
		String email = InputValidator.promptAndParseString("Enter the player's email : ");
		String userName = InputValidator.promptAndParseString("Enter the player's username : ");

		Player player = new Player();

		player.setFirstName(firstName);
		player.setLastName(lastName);
		player.setEmail(email);
		player.setUsername(userName);

		Team team = teamView.listTeams(teams);

		player.setTeam(team);

		return player;
	}

	public Player listPlayers(List<Player> players) {
		Player selectedPlayer = null;

		if (players.size() == 0) {
			IO.clear();
			System.out.println(
					"+-------------------------------------------------------------------------------------------------------------+");
			System.out.println(
					"|                                                                                                             |");
			System.out.println(
					"|                                                No players found                                               |");
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
					"+--------------------------------------------------------------------------------------------------------+");
			System.out.println(
					"|  Id  |    First Name    |     Last Name    |         Email         |       Usename      |     Team     |");
			System.out.println(
					"+--------------------------------------------------------------------------------------------------------+");
			for (Player player : players) {
				System.out.printf("|  %-3d | %-16s | %-16s | %-21s | %-18s | %-12s |\n",
						player.getId(),
						player.getFirstName(),
						player.getLastName(),
						player.getEmail(),
						player.getUsername(),
						player.getTeam().getTag());
				System.out.println(
						"+--------------------------------------------------------------------------------------------------------+");
			}

			System.out.print(
					"0 - Return to Player Menu \nPlease pick a player by entering their ID \nPlayer ID : ");
			try {
				int selectedId = IO.getScanner().nextInt();
				if (selectedId == 0)
					return selectedPlayer;
				selectedPlayer = players.stream()
						.filter(client -> client.getId() == selectedId)
						.findFirst()
						.orElse(null);

				if (selectedPlayer == null) {
					System.out.println("Invalid ID. Please try again.");
					IO.getScanner().next();
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter a valid number.");
				IO.getScanner().next();
				IO.getScanner().next();
			}

		} while (selectedPlayer == null);

		return selectedPlayer;

	}
}
