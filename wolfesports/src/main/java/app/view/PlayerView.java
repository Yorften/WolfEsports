package app.view;

import java.util.List;
import java.util.Scanner;

import model.Player;
import model.Team;
import app.util.IO;
import app.util.InputValidator;

public class PlayerView {
	Scanner in = IO.getScanner();

	private TeamView teamView = new TeamView();

	public Player addPlayerUI(List<Team> teams, Player player) {
		IO.clear();

		String firstName;
		String lastName;
		String email;
		String userName;
		Team team = new Team();

		if (player == null) {
			player = new Player();

			firstName = InputValidator.promptAndParseString("First name : ");
			lastName = InputValidator.promptAndParseString("Last name : ");
			email = InputValidator.promptAndParseString("Enter the player's email : ");
			userName = InputValidator.promptAndParseString("Enter the player's username : ");

		} else {

			firstName = InputValidator.promptAndParseNullableString("First name : ");
			lastName = InputValidator.promptAndParseNullableString("Last name : ");
			email = InputValidator.promptAndParseNullableString("Enter the player's email : ");
			userName = InputValidator.promptAndParseNullableString("Enter the player's username : ");
		}

		player.setFirstName(firstName == null ? player.getFirstName() : firstName);
		player.setLastName(lastName == null ? player.getLastName() : lastName);
		player.setEmail(email == null ? player.getEmail() : email);
		player.setUsername(userName == null ? player.getUsername() : userName);

		if (InputValidator.promptYesOrNo("Do you want to assign a team to the player ?")) {
			team = teamView.listTeams(teams);

			if (team == null) {
				System.out.println("No teams found\nPlase create a game before creating a tournament");
				return null;
			}

			player.setTeam(team);

		}
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
					"|                                               No players found                                              |");
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
						player.getTeam() == null ? "N/A" : player.getTeam().getTag());
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

	public int subPlayerMenu(Player player) {
		int input = -1;

		IO.clear();
		System.out.println(
				"+--------------------------------------------------------------------------------------------------------+");
		System.out.println(
				"|  Id  |    First Name    |     Last Name    |         Email         |       Usename      |     Team     |");
		System.out.println(
				"+--------------------------------------------------------------------------------------------------------+");
		System.out.printf("|  %-3d | %-16s | %-16s | %-21s | %-18s | %-12s |\n",
				player.getId(),
				player.getFirstName(),
				player.getLastName(),
				player.getEmail(),
				player.getUsername(),
				player.getTeam() == null ? "N/A" : player.getTeam().getTag());
		System.out.println(
				"+--------------------------------------------------------------------------------------------------------+");

		System.out.println("\t\t+---------------------------------------------+");
		System.out.println("\t\t|                                             |");
		System.out.println("\t\t|     1- Update player                        |");
		System.out.println("\t\t|     2- Delete player                        |");
		System.out.println("\t\t|     3- Back                                 |");
		System.out.println("\t\t|                                             |");
		System.out.println("\t\t+---------------------------------------------+");
		System.out.print("Pick your choice : ");

		try {
			input = in.nextInt();
			if (input < 1 || input > 3) {
				System.out.println("Please pick a choice between 1 and 3...");
				in.next();
			}
		} catch (Exception e) {
			System.out.println("Please pick a valid number...");
			in.next();
		}
		return input;
	}
}
