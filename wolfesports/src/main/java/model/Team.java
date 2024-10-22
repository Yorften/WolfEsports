package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Team Name shouldn't be null")
    @Column(name = "team_name", nullable = false)
    private String teamName;

    @NotNull(message = "Tag Name shouldn't be null")
    @Column(name = "tag", nullable = false)
    private String tag;

    @ManyToMany
    @JoinTable(name = "brackets", joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "tournament_id"))
    private List<Tournament> tournaments;

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                ", tag='" + tag + '\'' +
                "}";
    }

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Player> players;

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String value) {
        this.teamName = value;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String value) {
        this.tag = value;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<Player> value) {
        this.players = value;
    }
}