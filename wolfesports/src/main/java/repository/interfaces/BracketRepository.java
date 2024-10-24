package repository.interfaces;

import java.util.List;
import java.util.Optional;

import model.Bracket;

public interface BracketRepository {

    Optional<Bracket> get(long id);

    List<Bracket> getAllByTeamId(long teamId);

    List<Bracket> getAll(long id);

	List<Bracket> getWinners(long id);

	void update(Bracket bracket);

}
