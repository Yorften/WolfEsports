package repository.interfaces;

import java.util.Optional;

import model.Bracket;

public interface BracketRepository {
    Optional<Bracket> get(long id);

	void save(Bracket bracket);

	void update(Bracket bracket);
}
