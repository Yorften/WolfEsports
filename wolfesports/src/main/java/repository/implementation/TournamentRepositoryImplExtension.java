package repository.implementation;

import java.util.List;
import java.util.Optional;

import model.Tournament;
import repository.interfaces.TournamentRepository;

public class TournamentRepositoryImplExtension implements TournamentRepository{

    @Override
    public Optional<Tournament> get(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public List<Tournament> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public void save(Tournament tournament) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Tournament tournament) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public double calculateEstimatedTournamentDuration(Tournament tournament) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'calculateEstimatedTournamentDuration'");
    }
    
}
