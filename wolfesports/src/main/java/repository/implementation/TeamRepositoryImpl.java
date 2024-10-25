package repository.implementation;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Team;
import repository.interfaces.TeamRepository;
import util.PersistenceUtil;

public class TeamRepositoryImpl implements TeamRepository {
    private static final Logger logger = LoggerFactory.getLogger(TeamRepositoryImpl.class);

    @Override
    public Optional<Team> get(long id) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        Team team = null;
        try {
            TypedQuery<Team> typedQuery;

            typedQuery = entityManager.createQuery(
                    "SELECT DISTINCT t FROM Team t LEFT JOIN FETCH t.players p WHERE (p.isDeleted = false OR p.id IS NULL) AND t.id = :id ORDER BY t.id ASC",
                    Team.class);

            typedQuery.setParameter("id", id);

            team = typedQuery.getSingleResult();

        } catch (Exception e) {
            logger.error("Error getting team: ", e);
        } finally {
            entityManager.close();
        }

        return Optional.ofNullable(team);
    }

    @Override
    public List<Team> getAll() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Team> teams = null;

        try {
            TypedQuery<Team> typedQuery;

            typedQuery = entityManager.createQuery(
                    "SELECT DISTINCT t FROM Team t WHERE t.isDeleted = false ORDER BY t.id ASC",
                    Team.class);

            teams = typedQuery.getResultList();

        } catch (Exception e) {
            logger.error("Error listing teams: ", e);
        } finally {
            entityManager.close();
        }

        return teams;
    }

    @Override
    public void save(Team team) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(team);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error creating team: ", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void update(Team team) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(team);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error updating team: ", e);
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
            Team team = entityManager.find(Team.class, id);
            if (team != null) {
                team.setIsDeleted(true);
                entityManager.merge(team);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error deleting team: ", e);
        } finally {
            entityManager.close();
        }
    }

}
