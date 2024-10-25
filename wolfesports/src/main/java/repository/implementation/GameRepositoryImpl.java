package repository.implementation;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Game;
import repository.interfaces.GameRepository;
import util.PersistenceUtil;

public class GameRepositoryImpl implements GameRepository {
	private static final Logger logger = LoggerFactory.getLogger(GameRepositoryImpl.class);

    @Override
    public Optional<Game> get(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return Optional.ofNullable(entityManager.find(Game.class, id));
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Game> getAll() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Game> games = null;

        try {
            TypedQuery<Game> typedQuery;

            typedQuery = entityManager.createQuery(
                    "SELECT g FROM Game g WHERE g.isDeleted = false ORDER BY g.id ASC",
                    Game.class);

            games = typedQuery.getResultList();

        } catch (Exception e) {
            logger.error("Error listing games: ", e);
        } finally {
            entityManager.close();
        }

        return games;
    }

    @Override
    public List<Game> getAllWithTournaments() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Game> games = null;

        try {
            TypedQuery<Game> typedQuery;

            typedQuery = entityManager.createQuery(
                    "SELECT DISTINCT g FROM Game g LEFT JOIN FETCH g.tournaments t WHERE (t.isDeleted = false OR t.id IS NULL) ORDER BY g.title ASC",
                    Game.class);

            games = typedQuery.getResultList();

        } catch (Exception e) {
            logger.error("Error listing games: ", e);
        } finally {
            entityManager.close();
        }

        return games;
    }


    @Override
    public void save(Game game) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(game);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error creating game: ", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Game game) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(game);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error updating game: ", e);
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
            Game game = entityManager.find(Game.class, id);
            if (game != null) {
                game.setIsDeleted(true);
                entityManager.merge(game);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error deleting game: ", e);
        } finally {
            entityManager.close();
        }
    }

}
