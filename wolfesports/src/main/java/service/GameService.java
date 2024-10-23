package service;

import java.util.List;
import java.util.Optional;

import model.Game;
import repository.interfaces.GameRepository;

public class GameService {

    GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public Optional<Game> getGame(long id) {
		return gameRepository.get(id);
	}

	public List<Game> getAllGames() {
		return gameRepository.getAll();
	}

	public List<Game> getAllGamesWithTournaments() {
		return gameRepository.getAllWithTournaments();
	}


	public void addGame(Game user) {
		gameRepository.save(user);
	}

	public void updateGame(Game user) {
		gameRepository.update(user);
	}

	public void deleteGame(long id) {
		gameRepository.delete(id);
	}

}
