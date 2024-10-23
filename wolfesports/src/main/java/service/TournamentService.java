package service;

import java.util.List;
import java.util.Optional;

import model.Tournament;
import repository.interfaces.TournamentRepository;

public class TournamentService {

    TournamentRepository tournamentRepository;

    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public Optional<Tournament> getTournament(long id) {
        return tournamentRepository.get(id);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.getAll();
    }

    public void addTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    public void updateTournament(Tournament tournament) {
        tournamentRepository.update(tournament);
    }

    public void deleteTournament(long id) {
        tournamentRepository.delete(id);
    }

    public double calculateDuration(Tournament tournament) {
        return tournamentRepository.calculateEstimatedTournamentDuration(tournament);
    }
}
