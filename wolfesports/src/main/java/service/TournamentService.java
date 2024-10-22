package service;

import repository.interfaces.TournamentRepository;

public class TournamentService {

    TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }
}
