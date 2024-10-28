package repository.implementation;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Warning;
import repository.interfaces.WarningRepository;
import util.PersistenceUtil;

public class WarningRepositoryImpl implements WarningRepository {
    private static final Logger logger = LoggerFactory.getLogger(WarningRepositoryImpl.class);

    @Override
    public void save(Warning warning) {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(warning);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            logger.error("Error creating warning: ", e);
        } finally {
            entityManager.close();
        }
    }

}
