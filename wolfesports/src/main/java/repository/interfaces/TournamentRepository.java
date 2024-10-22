package repository.interfaces;

import java.util.List;
import java.util.Optional;

import model.Tournament;

public interface TournamentRepository {
    Optional<Tournament> get(long id);

	List<Tournament> getAll();

	void save(Tournament tournament);

	void update(Tournament tournament);

	void delete(long id);

	double calculateEstimatedTournamentDuration(Tournament tournament);
}
