package repository.interfaces;

import java.util.List;
import java.util.Optional;

import model.Player;

public interface PlayerRepository {
    Optional<Player> get(long id);

	List<Player> getAll();

	void save(Player player);

	void update(Player player);

	void delete(long id);
}
