package repository.implementation;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Player;
import repository.interfaces.PlayerRepository;
import util.PersistenceUtil;

public class PlayerRepositoryImpl implements PlayerRepository {
	private static final Logger logger = LoggerFactory.getLogger(PlayerRepositoryImpl.class);

    @Override
    public Optional<Player> get(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
		try {
			return Optional.ofNullable(entityManager.find(Player.class, id));
		} finally {
			entityManager.close();
		}
    }

    @Override
    public List<Player> getAll() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Player> players = null;

        try {
            TypedQuery<Player> typedQuery;

            typedQuery = entityManager.createQuery(
                    "SELECT p FROM Player p WHERE p.isDeleted = false ORDER BY p.id ASC",
                    Player.class);

            players = typedQuery.getResultList();

        } catch (Exception e) {
            logger.error("Error listing users: ", e);
        } finally {
            entityManager.close();
        }

        return players;
    }

    @Override
    public void save(Player player) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error creating user: ", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Player player) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(player);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error updating player: ", e);
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
            Player player = entityManager.find(Player.class, id);
            if (player != null) {
                player.setIsDeleted(true);
                entityManager.merge(player);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error deleting player: ", e);
        } finally {
            entityManager.close();
        }
    }

}
