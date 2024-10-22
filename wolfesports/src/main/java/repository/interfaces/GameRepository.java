package repository.interfaces;

import java.util.List;
import java.util.Optional;

import model.Game;

public interface GameRepository {
    Optional<Game> get(long id);

	List<Game> getAll();

	void save(Game game);

	void update(Game game);

	void delete(long id);
}
