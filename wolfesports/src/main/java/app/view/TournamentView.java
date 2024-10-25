package app.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Game;
import model.Tournament;
import model.enums.TournamentStatus;
import app.util.IO;
import app.util.InputValidator;

public class TournamentView {
	private static final Logger logger = LoggerFactory.getLogger(TournamentView.class);
	private GameView gameView = new GameView();

	Scanner in = IO.getScanner();

	public Tournament addTournamentUI(List<Game> games, Tournament tournament) {
		IO.clear();

		String title = null;
		String description = null;
		Integer total_places = 0;
		LocalDate startDate = null;
		LocalDate finishDate = null;
		Double pauseTime = null;
		Double ceremonyTime = null;

		Game game = new Game();

		if (tournament == null) {
			tournament = new Tournament();

			title = InputValidator.promptAndParseString("Title of the tournament : ");
			logger.info("title : " + title);
			description = InputValidator.promptAndParseString("Tournament's description : ");
			logger.info("description : " + description);
			total_places = InputValidator.promptAndParseTournamentPlaces("Tournament total places : ");
			startDate = InputValidator.promptAndParseDate("Start date (dd-MM-yyyy): ");

			do {
				finishDate = InputValidator.promptAndParseDate("Finish date (dd-MM-yyyy): ");
				if (finishDate.isBefore(startDate))
					System.out.println("Finish date is before the start date of the tournament");
			} while (finishDate.isBefore(startDate));

			pauseTime = InputValidator.promptAndParseDouble("Tournament pause time (mins) : ");
			ceremonyTime = InputValidator.promptAndParseDouble("Tournament's ceremony estimated time (mins) : ");

		} else {
			boolean loop = false;

			title = InputValidator.promptAndParseNullableString("Title of the tournament : ");
			description = InputValidator.promptAndParseNullableString("Tournament's description : ");
			total_places = InputValidator.promptAndParseNullableTournamentPlaces("Tournament total places : ");
			startDate = InputValidator.promptAndParseNullableDate("Start date (dd-MM-yyyy): ");

			do {
				loop = false;
				finishDate = InputValidator.promptAndParseNullableDate("Finish date (dd-MM-yyyy): ");
				if (finishDate == null) {
					if (startDate != null) {
						if (tournament.getFinishDate().isBefore(startDate)) {
							System.out.println("Finish date is before the start date of the tournament");
							loop = true;
						}
					}
				} else {
					if (startDate == null) {
						if (finishDate.isBefore(tournament.getStartDate())) {
							System.out.println("Finish date is before the start date of the tournament");
							loop = true;
						}
					} else {
						if (finishDate.isBefore(startDate)) {
							System.out.println("Finish date is before the start date of the tournament");
							loop = true;
						}
					}
				}

			} while (loop);

			pauseTime = InputValidator.promptAndParseNullableDouble("Tournament pause time (mins) : ");
			ceremonyTime = InputValidator.promptAndParseNullableDouble("Tournament's ceremony estimated time (mins) : ");

		}

		logger.info("Tournament : " + tournament.toString());

		tournament.setTitle(title == null ? tournament.getTitle() : title);
		tournament.setDescription(description == null ? tournament.getDescription() : description);
		tournament.setTotalPlaces(total_places == null ? tournament.getTotalPlaces() : total_places.intValue());
		tournament.setStartDate(startDate == null ? tournament.getStartDate() : startDate);
		tournament.setFinishDate(finishDate == null ? tournament.getFinishDate() : finishDate);
		tournament.setPauseTime(pauseTime == null ? tournament.getPauseTime() : pauseTime);
		tournament.setCeremonyTime(ceremonyTime == null ? tournament.getCeremonyTime() : ceremonyTime);

		logger.info("New Tournament : " + tournament.toString());

		if(games != null){
			game = gameView.listGames(games);

			if (game == null) {
				System.out.println("No games found\nPlase create a game before creating a tournament");
				return null;
			}

			tournament.setGame(game);

		}

		return tournament;
	}

	public Tournament listTournaments(List<Tournament> tournaments) {
		Tournament selectedTournament = null;

		if (tournaments.size() == 0) {
			IO.clear();
			System.out.println(
					"+-------------------------------------------------------------------------------------------------------------+");
			System.out.println(
					"|                                                                                                             |");
			System.out.println(
					"|                                             No tournaments found                                            |");
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
					"+----------------------------------------------------------------------------------------------------+");
			System.out.println(
					"|  Id  |             Title            |  Total Places  |  Start Date  |  Finish Date  |    Status    |");
			System.out.println(
					"+----------------------------------------------------------------------------------------------------+");
			for (Tournament tournament : tournaments) {
				System.out.printf("|  %-3d | %-28s | %-14d | %-12s | %-13s | %-12s |\n",
						tournament.getId(),
						tournament.getTitle(),
						tournament.getTotalPlaces(),
						tournament.getStartDate(),
						tournament.getFinishDate(),
						tournament.getTournamentStatus().toString());
				System.out.println(
						"+----------------------------------------------------------------------------------------------------+");
			}

			System.out.print(
					"0 - Return to Tournament Menu \nPlease pick a tournament by entering their ID \nTournament ID : ");
			try {
				int selectedId = IO.getScanner().nextInt();
				if (selectedId == 0)
					return selectedTournament;
				selectedTournament = tournaments.stream()
						.filter(client -> client.getId() == selectedId)
						.findFirst()
						.orElse(null);

				if (selectedTournament == null) {
					System.out.println("Invalid ID. Please try again.");
					IO.getScanner().next();
				}
			} catch (Exception e) {
				System.out.println("Invalid input. Please enter a valid number.");
				IO.getScanner().next();
				IO.getScanner().next();
			}

		} while (selectedTournament == null);

		return selectedTournament;

	}

	public int subTournamentMenu(Tournament tournament) {
		int input = -1;

		IO.clear();
		System.out.println(
				"+----------------------------------------------------------------------------------------------------+");
		System.out.println(
				"|  Id  |             Title            |  Total Places  |  Start Date  |  Finish Date  |    Status    |");
		System.out.println(
				"+----------------------------------------------------------------------------------------------------+");
		System.out.printf("|  %-3d | %-28s | %-14d | %-12s | %-12s | %-12s |\n",
				tournament.getId(),
				tournament.getTitle(),
				tournament.getTotalPlaces(),
				tournament.getStartDate(),
				tournament.getFinishDate(),
				tournament.getTournamentStatus().toString());
		System.out.println(
				"+----------------------------------------------------------------------------------------------------+");

		if (tournament.getTournamentStatus() == TournamentStatus.COMPLETED) {
			IO.getScanner().next();
			return 3;
		}

		System.out.println("\t\t+---------------------------------------------+");
		System.out.println("\t\t|                                             |");
		System.out.println("\t\t|     1- Update tournament                    |");
		System.out.println("\t\t|     2- Delete tournament                    |");
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
