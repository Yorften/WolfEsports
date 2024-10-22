package repository.interfaces;

import java.util.List;
import java.util.Optional;

import model.Team;

public interface TeamRepository {
    Optional<Team> get(long id);

	List<Team> getAll();

	void save(Team team);

	void update(Team team);

	void delete(long id);
}
