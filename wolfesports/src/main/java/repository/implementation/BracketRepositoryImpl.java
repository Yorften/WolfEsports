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
import repository.interfaces.BracketRepository;
import util.PersistenceUtil;

public class BracketRepositoryImpl implements BracketRepository {
    private static final Logger logger = LoggerFactory.getLogger(BracketRepositoryImpl.class);

    @Override
    public Optional<Bracket> get(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(Bracket.class, id));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Bracket> getAll(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Bracket> brackets = null;

        try {
            Tournament tournament = Optional.ofNullable(entityManager.find(Tournament.class, id)).orElse(null);

            if (tournament != null) {

                TypedQuery<Bracket> typedQuery;

                typedQuery = entityManager.createQuery(
                        "SELECT b FROM Bracket b WHERE b.position <= :totalPlaces AND b.team.id IS NULL AND b.tournament.id = :tournamentId",
                        Bracket.class);

                typedQuery.setParameter("totalPlaces", tournament.getTotalPlaces());
                typedQuery.setParameter("tournamentId", tournament.getId());

                brackets = typedQuery.getResultList();

            } else {
                logger.warn("Tournament not found");
            }
        } catch (Exception e) {
            logger.error("Error listing brackets: ", e);
        } finally {
            entityManager.close();
        }

        return brackets;
    }

    @Override
    public List<Bracket> getWinners(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Bracket> brackets = null;

        try {
            Tournament tournament = Optional.ofNullable(entityManager.find(Tournament.class, id)).orElse(null);

            if (tournament != null) {

                TypedQuery<Bracket> typedQuery;

                typedQuery = entityManager.createQuery(
                        "SELECT b FROM Bracket b WHERE b.tournament.id = :tournamentId GROUP BY b.id, b.position, b.team.id ORDER BY b.position DESC",
                        Bracket.class);

                typedQuery.setParameter("tournamentId", tournament.getId());
                typedQuery.setMaxResults(3); // Get top 3

                brackets = typedQuery.getResultList();

            } else {
                logger.warn("Tournament not found");
            }
        } catch (Exception e) {
            logger.error("Error listing brackets: ", e);
        } finally {
            entityManager.close();
        }

        return brackets;
    }

    @Override
    public void update(Bracket bracket) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(bracket);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error updating bracket: ", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Bracket> getAllByTeamId(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Bracket> brackets = null;
        try {
            brackets = entityManager.createQuery(
                "SELECT b FROM Bracket b LEFT JOIN FETCH b.tournament t WHERE b.team.id = :teamId GROUP BY b.id, b.position, b.tournament.id",
                Bracket.class).setParameter("teamId", id).getResultList();

        } catch (Exception e) {
            logger.error("Error fetching bracket by team id: ", e);

        }

        return brackets;

    }

}
