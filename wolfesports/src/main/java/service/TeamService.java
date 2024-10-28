package service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Team;
import repository.interfaces.TeamRepository;

public class TeamService {

	TeamRepository teamRepository;

	public TeamService(TeamRepository teamRepository) {
		this.teamRepository = teamRepository;
	}

	public Optional<Team> getTeam(long id) {
		return teamRepository.get(id);
	}

	public List<Team> getAllTeams() {
		return teamRepository.getAll();
	}

	public List<Team> getAllWarnedTeams() {
		List<Team> teams = teamRepository.getAll();
		return teams.stream().filter(team -> team.getWarning() != null).collect(Collectors.toList());
	}

	public List<Team> getAllNonWarnedTeams() {
		List<Team> teams = teamRepository.getAll();
		return teams.stream().filter(team -> team.getWarning() == null).collect(Collectors.toList());
	}

	public void addTeam(Team team) {
		teamRepository.save(team);
	}

	public void updateTeam(Team team) {
		teamRepository.update(team);
	}

	public void deleteTeam(long id) {
		teamRepository.delete(id);
	}
}
