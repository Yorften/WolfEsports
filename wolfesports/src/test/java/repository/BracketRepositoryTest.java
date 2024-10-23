package repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

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

import model.Bracket;
import model.Game;
import model.Team;
import model.Tournament;
import service.BracketService;
import service.GameService;
import service.TeamService;
import service.TournamentService;
import util.PersistenceUtil;

public class BracketRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(BracketRepositoryTest.class);

    private static ApplicationContext applicationContext;
    private static BracketService bracketService;
    private static TournamentService tournamentService;
    private static GameService gameService;
    private static TeamService teamService;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        bracketService = applicationContext.getBean("bracketService", BracketService.class);
        tournamentService = applicationContext.getBean("tournamentService", TournamentService.class);
        gameService = applicationContext.getBean("gameService", GameService.class);
        teamService = applicationContext.getBean("teamService", TeamService.class);

        System.setProperty("persistence.unit.name", "test_WOLFESPORTS_PU");

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
        entityManager.createQuery("DELETE FROM Game").executeUpdate();
        entityManager.createQuery("DELETE FROM Team").executeUpdate();
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testGetFirstRoundBrackets() {
        Game game = new Game();
        game.setTitle("League of Legends");
        game.setAverageGameplayTime(35D);

        Tournament tournament = new Tournament();
        tournament.setTitle("Worlds League of Legends");
        tournament.setDescription("description");
        tournament.setStartDate(LocalDate.of(2024, 11, 1));
        tournament.setFinishDate(LocalDate.of(2024, 12, 12));
        tournament.setTotalPlaces(32);
        tournament.setGame(game);
        tournament.setCeremonyTime(30);
        tournament.setPauseTime(20);

        gameService.addGame(game);

        tournamentService.addTournament(tournament);

        List<Bracket> brackets = bracketService.getFirstRoundBrackets(1L);

        assertTrue(brackets.size() == 32);
    }

    @Test
    public void testGetTournamentWinners() {
        Team team1 = new Team();
        team1.setTeamName("SK Telecom T1");
        team1.setTag("SKT");

        Team team2 = new Team();
        team2.setTeamName("Samsung Galaxy");
        team2.setTag("SSG");

        Team team3 = new Team();
        team3.setTeamName("Royal Never Give Up");
        team3.setTag("RNG");

        teamService.addTeam(team1);
        teamService.addTeam(team2);
        teamService.addTeam(team3);

        Game game = new Game();
        game.setTitle("League of Legends");
        game.setAverageGameplayTime(35D);

        Tournament tournament = new Tournament();
        tournament.setTitle("Worlds League of Legends");
        tournament.setDescription("description");
        tournament.setStartDate(LocalDate.of(2024, 11, 1));
        tournament.setFinishDate(LocalDate.of(2024, 12, 12));
        tournament.setTotalPlaces(32);
        tournament.setGame(game);
        tournament.setCeremonyTime(30);
        tournament.setPauseTime(20);

        gameService.addGame(game);

        tournamentService.addTournament(tournament);

        Bracket bracket66 = bracketService.getBracket(66).get(); // 1st place bracket
        Bracket bracket65 = bracketService.getBracket(65).get(); // finals bracket
        Bracket bracket64 = bracketService.getBracket(64).get(); // finals bracket
        Bracket bracket63 = bracketService.getBracket(63).get(); // 3rd place bracket

        bracket66.setTeam(team1);
        bracket65.setTeam(team1);
        bracket64.setTeam(team2);
        bracket63.setTeam(team3);

        bracketService.updateBracket(bracket63);
        bracketService.updateBracket(bracket64);
        bracketService.updateBracket(bracket65);
        bracketService.updateBracket(bracket66);

        List<Bracket> winners = bracketService.getTournamentWinners(1);

        winners.forEach(bracket -> logger.info("Bracket : " + bracket.toString()));

        assertEquals(3, winners.size());
    }
}
