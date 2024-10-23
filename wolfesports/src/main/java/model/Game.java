package model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "title shouldn't be null")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "averageGameplayTime shouldn't be null")
    @Column(name = "average_gameplay_time", nullable = false)
    private Double averageGameplayTime;

    @NotNull
	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isDeleted;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<Tournament> tournaments;

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", averageGameplayTime='" + averageGameplayTime + '\'' +
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

    public Double getAverageGameplayTime() {
        return this.averageGameplayTime;
    }

    public void setAverageGameplayTime(Double value) {
        this.averageGameplayTime = value;
    }

    public boolean getIsDeleted() {
      return this.isDeleted;
    }
    public void setIsDeleted(boolean value) {
      this.isDeleted = value;
    }

    public List<Tournament> getTournaments() {
      return this.tournaments;
    }
    public void setTournaments(List<Tournament> value) {
      this.tournaments = value;
    }
}
