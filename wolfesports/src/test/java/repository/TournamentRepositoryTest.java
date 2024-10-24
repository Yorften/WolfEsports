package repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import model.Game;
import model.Tournament;
import service.GameService;
import service.TournamentService;
import util.PersistenceUtil;

public class TournamentRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(TournamentRepositoryTest.class);

    private static ApplicationContext applicationContext;
    private static TournamentService tournamentService;
    private static GameService gameService;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        tournamentService = applicationContext.getBean("tournamentService", TournamentService.class);
        gameService = applicationContext.getBean("gameService", GameService.class);
        System.setProperty("persistence.unit.name", "H2_WOLFESPORTS_PU");
    }

    @AfterClass
    public static void truncate() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Game").executeUpdate();
        transaction.commit();
        entityManager.close();
    }

    @After
    public void terminate() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Tournament").executeUpdate();
        entityManager.createQuery("DELETE FROM Bracket").executeUpdate();
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testAddTournament() {

        Game game = new Game();
        game.setTitle("League of Legends");
        game.setAverageGameplayTime(35D);

        gameService.addGame(game);
        
        Tournament tournament = new Tournament();
        tournament.setTitle("Worlds League of Legends");
        tournament.setDescription("description");
        tournament.setStartDate(LocalDate.of(2024, 11, 1));
        tournament.setFinishDate(LocalDate.of(2024, 12, 12));
        tournament.setTotalPlaces(32);
        tournament.setGame(game);
        tournament.setCeremonyTime(30);
        tournament.setPauseTime(20);

        tournamentService.addTournament(tournament);

        Optional<Tournament> fetchedTournament = tournamentService.getTournament(tournament.getId());
        assertTrue(fetchedTournament.isPresent());
        assertEquals("Worlds League of Legends", fetchedTournament.get().getTitle());
        assertEquals("League of Legends", fetchedTournament.get().getGame().getTitle());
        assertEquals(66, fetchedTournament.get().getBrackets().size());
    }

    @Test
    public void testGetAllTournaments() {
        Game game = new Game();
        game.setTitle("League of Legends");
        game.setAverageGameplayTime(35D);

        gameService.addGame(game);
        Tournament tournament1 = new Tournament();
        tournament1.setTitle("Worlds League of Legends");
        tournament1.setDescription("description");
        tournament1.setStartDate(LocalDate.of(2024, 11, 1));
        tournament1.setFinishDate(LocalDate.of(2024, 12, 12));
        tournament1.setTotalPlaces(32);
        tournament1.setGame(game);
        tournament1.setCeremonyTime(30);
        tournament1.setPauseTime(20);

        Tournament tournament2 = new Tournament();
        tournament2.setTitle("LCK League of Legends");
        tournament2.setDescription("description");
        tournament2.setStartDate(LocalDate.of(2024, 9, 1));
        tournament2.setFinishDate(LocalDate.of(2024, 10, 12));
        tournament2.setTotalPlaces(32);
        tournament2.setGame(game);
        tournament2.setCeremonyTime(30);
        tournament2.setPauseTime(20);

        tournamentService.addTournament(tournament1);
        tournamentService.addTournament(tournament2);

        List<Tournament> allTournaments = tournamentService.getAllTournaments();
        assertTrue(allTournaments.size() >= 2);
    }

    @Test
    public void testUpdateTournament() {
        Game game = new Game();
        game.setTitle("League of Legends");
        game.setAverageGameplayTime(35D);

        gameService.addGame(game);

        Tournament tournament = new Tournament();
        tournament.setTitle("Worlds League of Legends");
        tournament.setDescription("description");
        tournament.setStartDate(LocalDate.of(2024, 12, 1));
        tournament.setFinishDate(LocalDate.of(2024, 12, 12));
        tournament.setTotalPlaces(32);
        tournament.setGame(game);
        tournament.setCeremonyTime(30);
        tournament.setPauseTime(20);

        tournamentService.addTournament(tournament);

        tournament.setTitle("Updated Title");

        tournamentService.updateTournament(tournament);

        Optional<Tournament> updatedTournament = tournamentService.getTournament(tournament.getId());
        assertTrue(updatedTournament.isPresent());
        assertEquals("Updated Title", updatedTournament.get().getTitle());
    }

    @Test
    public void testDeleteTournament() {
        Game game = new Game();
        game.setTitle("League of Legends");
        game.setAverageGameplayTime(35D);

        gameService.addGame(game);
        Tournament tournament = new Tournament();
        tournament.setTitle("Worlds League of Legends");
        tournament.setDescription("description");
        tournament.setStartDate(LocalDate.of(2024, 12, 1));
        tournament.setFinishDate(LocalDate.of(2024, 12, 12));
        tournament.setTotalPlaces(32);
        tournament.setGame(game);
        tournament.setCeremonyTime(30);
        tournament.setPauseTime(20);

        tournamentService.addTournament(tournament);

        tournamentService.deleteTournament(tournament.getId());

        Optional<Tournament> deletedTournament = tournamentService.getTournament(tournament.getId());
        assertTrue(deletedTournament.isPresent());
        assertTrue(deletedTournament.get().getIsDeleted());
    }
}
