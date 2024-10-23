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

import model.Team;
import service.TeamService;
import util.PersistenceUtil;

public class TeamRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(GameRepositoryTest.class);

    private static ApplicationContext applicationContext;
    private static TeamService teamService;

    @BeforeClass
    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        teamService = applicationContext.getBean("teamService", TeamService.class);
        System.setProperty("persistence.unit.name", "test_WOLFESPORTS_PU");
    }

    @After
    public void terminate() {
        EntityManager entityManager = PersistenceUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.createQuery("DELETE FROM Team").executeUpdate();
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testAddGame() {
        Team newTeam = new Team();
        newTeam.setTeamName("SK Telecom T1");
        newTeam.setTag("SKT");

        teamService.addTeam(newTeam);

        Optional<Team> fetchedTeam = teamService.getTeam(newTeam.getId());
        assertTrue(fetchedTeam.isPresent());
        assertEquals("SK Telecom T1", fetchedTeam.get().getTeamName());
    }

    @Test
    public void testGetGameById() {
        Team newTeam = new Team();
        newTeam.setTeamName("SK Telecom T1");
        newTeam.setTag("SKT");

        teamService.addTeam(newTeam);

        Optional<Team> fetchedTeam = teamService.getTeam(newTeam.getId());
        assertTrue(fetchedTeam.isPresent());
        assertEquals("SK Telecom T1", fetchedTeam.get().getTeamName());

    }

    @Test
    public void testGetAllGames() {
        Team team1 = new Team();
        team1.setTeamName("SK Telecom T1");
        team1.setTag("SKT");

        Team team2 = new Team();

        team2.setTeamName("Samsung Galaxy");
        team2.setTag("SSG");

        teamService.addTeam(team1);
        teamService.addTeam(team2);

        List<Team> allGames = teamService.getAllTeams();
        assertTrue(allGames.size() >= 2);
    }

    @Test
    public void testUpdateGame() {
        Team newTeam = new Team();
        newTeam.setTeamName("SK Telecom T1");
        newTeam.setTag("SKT");

        teamService.addTeam(newTeam);

        newTeam.setTeamName("T1");
        newTeam.setTag("T1");

        teamService.updateTeam(newTeam);

        Optional<Team> updatedGame = teamService.getTeam(newTeam.getId());
        assertTrue(updatedGame.isPresent());
        assertEquals("T1", updatedGame.get().getTeamName());
        assertEquals("T1", updatedGame.get().getTag());
    }

    @Test
    public void testDeleteGame() {
        Team newTeam = new Team();
        newTeam.setTeamName("SK Telecom T1");
        newTeam.setTag("SKT");

        teamService.addTeam(newTeam);

        teamService.deleteTeam(newTeam.getId());

        Optional<Team> deletedGame = teamService.getTeam(newTeam.getId());
        assertTrue(deletedGame.isPresent());
        assertTrue(deletedGame.get().getIsDeleted());
    }
}
