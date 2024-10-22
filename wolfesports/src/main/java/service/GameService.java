package service;

import repository.interfaces.GameRepository;

public class GameService {

    GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }
}
