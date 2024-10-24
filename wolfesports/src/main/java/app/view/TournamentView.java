package app.view;

import java.time.LocalDate;
import java.util.List;

import model.Tournament;
import app.util.IO;
import app.util.InputValidator;

public class TournamentView {

	public Tournament addTournamentUI() {
		IO.clear();
		String title = InputValidator.promptAndParseString("Title of the tournament : ");
		String description = InputValidator.promptAndParseString("Title of the tournament : ");
		int total_places = InputValidator.promptAndParseTournamentPlaces("Tournament places : ");
		LocalDate statDate = InputValidator.promptAndParseDate("Start date : ");
		LocalDate finishDate = InputValidator.promptAndParseDate("Finish date : ");
		Double pauseTime = InputValidator.promptAndParseDouble("Tournament pause time (mins) : ");
		Double ceremonyTime = InputValidator.promptAndParseDouble("Tournament's ceremony estimated time (mins) : ");

		Tournament tournament = new Tournament();

		tournament.setTitle(title);
		tournament.setDescription(description);
		tournament.setTotalPlaces(total_places);
		tournament.setStartDate(statDate);
		tournament.setFinishDate(finishDate);
		tournament.setPauseTime(pauseTime);
		tournament.setCeremonyTime(ceremonyTime);

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
				System.out.printf("|  %-3d | %-28s | %-14d | %-12s | %-12s | %-12s |\n",
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
}
