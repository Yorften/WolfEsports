package service;

import java.util.List;
import java.util.Optional;

import model.Player;
import repository.interfaces.PlayerRepository;

public class PlayerService {

    PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	public Optional<Player> getPlayer(long id) {
		return playerRepository.get(id);
	}

	public List<Player> getAllPlayers() {
		return playerRepository.getAll();
	}


	public void addPlayer(Player user) {
		playerRepository.save(user);
	}

	public void updatePlayer(Player user) {
		playerRepository.update(user);
	}

	public void deletePlayer(long id) {
		playerRepository.delete(id);
	}


}
