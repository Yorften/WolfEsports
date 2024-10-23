package repository.implementation;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Bracket;
import model.Tournament;
import repository.interfaces.TournamentRepository;
import util.PersistenceUtil;

public class TournamentRepositoryImpl implements TournamentRepository {
    private static final Logger logger = LoggerFactory.getLogger(TournamentRepositoryImpl.class);

    @Override
    public Optional<Tournament> get(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        Tournament tournament = null;
        try {
            tournament = entityManager.createQuery(
                    "SELECT t FROM Tournament t LEFT JOIN FETCH t.teams e WHERE (e.isDeleted = false OR e.id IS NULL) AND t.id = :tournamentId",
                    Tournament.class).setParameter("tournamentId", id).getSingleResult();

            tournament = entityManager.createQuery(
                    "SELECT DISTINCT t from Tournament t LEFT JOIN FETCH t.brackets b WHERE t = :tournament",
                    Tournament.class).setParameter("tournament", tournament).getSingleResult();

        } catch (Exception e) {
            logger.error("Error getting tournament: ", e);
        } finally {
            entityManager.close();
        }

        return Optional.ofNullable(tournament);
    }

    @Override
    public List<Tournament> getAll() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Tournament> tournaments = null;

        try {
            TypedQuery<Tournament> typedQuery;

            typedQuery = entityManager.createQuery(
                    "SELECT DISTINCT t FROM Tournament t ORDER BY t.startDate ASC",
                    Tournament.class);

            tournaments = typedQuery.getResultList();

        } catch (Exception e) {
            logger.error("Error listing tournaments: ", e);
        } finally {
            entityManager.close();
        }

        return tournaments;
    }

    @Override
    public void save(Tournament tournament) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;

        double estimatedTime = calculateEstimatedTournamentDuration(tournament);
        tournament.setEstimatedTime(estimatedTime);

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(tournament);
            createBracketsForTournament(tournament, entityManager);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error creating tournament: ", e);
        } finally {
            entityManager.close();
        }
    }

    private void createBracketsForTournament(Tournament tournament, EntityManager entityManager) {
        int brackets = tournament.getTotalPlaces() * 2 + 2;
        for (int i = 0; i < brackets; i++) {
            Bracket bracket = new Bracket();
            bracket.setTournament(tournament);
            bracket.setPosition(i + 1);
            entityManager.persist(bracket);
        }

    }

    @Override
    public void update(Tournament tournament) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;

        double estimatedTime = calculateEstimatedTournamentDuration(tournament);
        tournament.setEstimatedTime(estimatedTime);

        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(tournament);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error updating tournament: ", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Tournament tournament = entityManager.find(Tournament.class, id);
            if (tournament != null) {
                tournament.setIsDeleted(true);
                entityManager.merge(tournament);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error deleting tournament: ", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public double calculateEstimatedTournamentDuration(Tournament tournament) {
        return (tournament.getTotalPlaces() * tournament.getGame().getAverageGameplayTime())
                + tournament.getPauseTime();
    }

}
