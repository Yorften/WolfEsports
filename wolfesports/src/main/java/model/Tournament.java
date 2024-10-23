package model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import model.enums.TournamentStatus;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tournament Title shouldn't be null")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "Tournament description shouldn't be null")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Tournament total places should not be null")
    @Column(name = "total_places", nullable = false)
    private int totalPlaces;

    @NotNull(message = "Tournament start date should not be null")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull(message = "Tournament finish date should not be null")
    @Column(name = "finish_date", nullable = false)
    private LocalDate finishDate;

    @NotNull(message = "Tournament estimated time should not be null")
    @Column(name = "estimated_time", nullable = false)
    private double estimatedTime;

    @NotNull(message = "Tournament pause time should not be null")
    @Column(name = "pause_time", nullable = false)
    private double pauseTime;

    @NotNull(message = "Tournament ceremony time should not be null")
    @Column(name = "ceremony_time", nullable = false)
    private double ceremonyTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "tournament_status", nullable = false, columnDefinition = "ENUM('SCHEDULED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED')")
    private TournamentStatus tournamentStatus = TournamentStatus.SCHEDULED;

    @NotNull
    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted;

    @JoinColumn(name = "game_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Game game;

    @OneToMany(mappedBy = "tournament")
    private List<Bracket> brackets;

    @ManyToMany(mappedBy = "tournaments")
    private List<Team> teams;

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", totalPlaces=" + totalPlaces +
                ", startDate=" + startDate +
                ", finishDate=" + finishDate +
                ", estimatedTime=" + estimatedTime +
                ", pauseTime=" + pauseTime +
                ", ceremonyTime=" + ceremonyTime +
                ", tournamentStatus=" + tournamentStatus +
                ", isDeleted=" + isDeleted +
                "}";
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public int getTotalPlaces() {
        return this.totalPlaces;
    }

    public void setTotalPlaces(int value) {
        this.totalPlaces = value;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate value) {
        this.startDate = value;
    }

    public LocalDate getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(LocalDate value) {
        this.finishDate = value;
    }

    public double getEstimatedTime() {
        return this.estimatedTime;
    }

    public void setEstimatedTime(double value) {
        this.estimatedTime = value;
    }

    public double getPauseTime() {
        return this.pauseTime;
    }

    public void setPauseTime(double value) {
        this.pauseTime = value;
    }

    public double getCeremonyTime() {
        return this.ceremonyTime;
    }

    public void setCeremonyTime(double value) {
        this.ceremonyTime = value;
    }

    public TournamentStatus getTournamentStatus() {
        return this.tournamentStatus;
    }

    public void setTournamentStatus(TournamentStatus value) {
        this.tournamentStatus = value;
    }

    public List<Team> getTeams() {
        return this.teams;
    }

    public void setTeams(List<Team> value) {
        this.teams = value;
    }

    public List<Bracket> getBrackets() {
        return this.brackets;
    }

    public void setBrackets(List<Bracket> value) {
        this.brackets = value;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game value) {
        this.game = value;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean value) {
        this.isDeleted = value;
    }
}
