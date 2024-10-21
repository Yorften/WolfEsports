package listener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import model.Player;
import util.PersistenceUtil;

public class JpaInitializer implements ServletContextListener {
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {    
        // Initialize EntityManagerFactory
        entityManagerFactory = PersistenceUtil.getEntityManagerFactory();
        // Populate the database
        populateDatabase();
    }

    private void populateDatabase() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Player player1 = new Player();
            player1.setFirstName("client");
            player1.setLastName("1");
            player1.setEmail("player1@youcode.ma");
            player1.setUsername("username1");
            entityManager.persist(player1);

            Player player2 = new Player();
            player2.setFirstName("client");
            player2.setLastName("1");
            player2.setEmail("player2@youcode.ma");
            player2.setUsername("username2");
            entityManager.persist(player2);

            Player player3 = new Player();
            player3.setFirstName("client");
            player3.setLastName("1");
            player3.setEmail("player3@youcode.ma");
            player3.setUsername("username3");
            entityManager.persist(player3);

            Player player4 = new Player();
            player4.setFirstName("client");
            player4.setLastName("1");
            player4.setEmail("player4@youcode.ma");
            player4.setUsername("username4");
            entityManager.persist(player4);

            Player player5 = new Player();
            player5.setFirstName("client");
            player5.setLastName("1");
            player5.setEmail("player5@youcode.ma");
            player5.setUsername("username5");
            entityManager.persist(player5);

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}

