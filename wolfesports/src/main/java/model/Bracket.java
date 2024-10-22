package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "brackets")
public class Bracket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Bracket position should not be null")
    @Column(name = "position", nullable = false)
    private int position;

    @JoinColumn(name = "tournament_id", nullable = false)
    @ManyToOne
    private Tournament tournament;

    @JoinColumn(name = "team_id", nullable = true)
    @ManyToOne(fetch = FetchType.EAGER)
    private Team team;

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int value) {
        this.position = value;
    }

    public Tournament getTournament() {
        return this.tournament;
    }

    public void setTournament(Tournament value) {
        this.tournament = value;
    }

    public Team getTeam() {
        return this.team;
    }

    public void setTeam(Team value) {
        this.team = value;
    }
}
