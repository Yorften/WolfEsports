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

import model.Game;
import service.GameService;
import util.PersistenceUtil;

public class GameRepositoryImplTest {
    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryImplTest.class);

    private static ApplicationContext applicationContext;
    private static GameService gameService;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        gameService = applicationContext.getBean("gameService", GameService.class);
        System.setProperty("persistence.unit.name", "H2_WOLFESPORTS_PU");
    }

    @After
    public void terminate() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Game").executeUpdate();
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testAddGame() {
        Game newGame = new Game();
        newGame.setTitle("Test Game");
        newGame.setAverageGameplayTime(50D);
        gameService.addGame(newGame);

        Optional<Game> fetchedGame = gameService.getGame(newGame.getId());
        assertTrue(fetchedGame.isPresent());
        assertEquals("Test Game", fetchedGame.get().getTitle());
    }

    @Test
    public void testGetGameById() {
        Game newGame = new Game();
        newGame.setTitle("Sample Game");
        newGame.setAverageGameplayTime(50D);
        gameService.addGame(newGame);

        Optional<Game> fetchedGame = gameService.getGame(newGame.getId());
        assertTrue(fetchedGame.isPresent());
        assertEquals("Sample Game", fetchedGame.get().getTitle());
    }

    @Test
    public void testGetAllGames() {
        Game game1 = new Game();
        game1.setTitle("Game One");
        game1.setAverageGameplayTime(50D);

        Game game2 = new Game();
        game2.setTitle("Game Two");
        game2.setAverageGameplayTime(50D);

        gameService.addGame(game1);
        gameService.addGame(game2);

        List<Game> allGames = gameService.getAllGames();
        assertTrue(allGames.size() >= 2);
    }

    @Test
    public void testUpdateGame() {
        Game newGame = new Game();
        newGame.setTitle("Old Name");
        newGame.setAverageGameplayTime(50D);
        gameService.addGame(newGame);

        newGame.setTitle("Updated Name");
        gameService.updateGame(newGame);

        Optional<Game> updatedGame = gameService.getGame(newGame.getId());
        assertTrue(updatedGame.isPresent());
        assertEquals("Updated Name", updatedGame.get().getTitle());
    }

    @Test
    public void testDeleteGame() {
        Game newGame = new Game();
        newGame.setTitle("Game to Delete");
        newGame.setAverageGameplayTime(50D);
        gameService.addGame(newGame);

        gameService.deleteGame(newGame.getId());

        Optional<Game> deletedGame = gameService.getGame(newGame.getId());
        assertTrue(deletedGame.isPresent());
        assertTrue(deletedGame.get().getIsDeleted());
    }
}
