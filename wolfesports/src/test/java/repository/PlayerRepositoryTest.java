package repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Player;
import service.PlayerService;
import util.PersistenceUtil;

public class PlayerRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(PlayerRepositoryTest.class);

    private static ApplicationContext applicationContext;
    private static PlayerService playerService;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        playerService = applicationContext.getBean("playerService", PlayerService.class);
        System.setProperty("persistence.unit.name", "test_WOLFESPORTS_PU");
    }

    @After
    public void terminate() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Player").executeUpdate();
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testAddPlayer() {
        Player newPlayer = new Player();
        newPlayer.setFirstName("Gamer");
        newPlayer.setLastName("Player");
        newPlayer.setUsername("player1");
        newPlayer.setEmail("player@gmail.com");

        playerService.addPlayer(newPlayer);

        Optional<Player> fetchedPlayer = playerService.getPlayer(newPlayer.getId());
        assertTrue(fetchedPlayer.isPresent());
        assertEquals("player@gmail.com", fetchedPlayer.get().getEmail());
    }

    @Test
    public void testGetPlayerById() {
        Player newPlayer = new Player();
        newPlayer.setFirstName("Gamer");
        newPlayer.setLastName("Player");
        newPlayer.setUsername("player1");
        newPlayer.setEmail("player@gmail.com");

        playerService.addPlayer(newPlayer);

        Optional<Player> fetchedPlayer = playerService.getPlayer(newPlayer.getId());
        assertTrue(fetchedPlayer.isPresent());
        assertEquals("player@gmail.com", fetchedPlayer.get().getEmail());

    }

    @Test
    public void testGetAllPlayers() {
        Player player1 = new Player();
        player1.setFirstName("Gamer");
        player1.setLastName("Player");
        player1.setUsername("player1");
        player1.setEmail("player1@gmail.com");

        Player player2 = new Player();
        player2.setFirstName("Gamer");
        player2.setLastName("Player");
        player2.setUsername("player2");
        player2.setEmail("player2@gmail.com");

        playerService.addPlayer(player1);
        playerService.addPlayer(player2);

        List<Player> allPlayers = playerService.getAllPlayers();
        assertTrue(allPlayers.size() >= 2);
    }

    @Test
    public void testUpdatePlayer() {
        Player newPlayer = new Player();
        newPlayer.setFirstName("Gamer");
        newPlayer.setLastName("Player");
        newPlayer.setUsername("player1");
        newPlayer.setEmail("player@gmail.com");

        playerService.addPlayer(newPlayer);

        newPlayer.setEmail("updatedmail@gmail.com");

        playerService.updatePlayer(newPlayer);

        Optional<Player> updatedPlayer = playerService.getPlayer(newPlayer.getId());
        assertTrue(updatedPlayer.isPresent());
        assertEquals("updatedmail@gmail.com", updatedPlayer.get().getEmail());
    }

    @Test
    public void testDeletePlayer() {
        Player newPlayer = new Player();
        newPlayer.setFirstName("Gamer");
        newPlayer.setLastName("Player");
        newPlayer.setUsername("player1");
        newPlayer.setEmail("player@gmail.com");
        
        playerService.addPlayer(newPlayer);

        playerService.deletePlayer(newPlayer.getId());

        Optional<Player> deletedPlayer = playerService.getPlayer(newPlayer.getId());
        assertTrue(deletedPlayer.isPresent());
        assertTrue(deletedPlayer.get().getIsDeleted());
    }
}
