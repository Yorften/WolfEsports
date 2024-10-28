package model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "warnings")
public class Warning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "title shouldn't be null")
    @Column(name = "colour", nullable = false)
    private String colour;

    @NotNull(message = "Issue date shouldn't be null")
    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;

    @OneToOne(mappedBy = "warning")
    private Team team;

    @Override
    public String toString() {
        return "Warning{" +
                "id=" + id +
                ", colour='" + colour + '\'' +
                ", issueDate='" + issueDate + '\'' +
                "}";
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long value) {
        this.id = value;
    }

    public String getColour() {
        return this.colour;
    }

    public void setColour(String value) {
        this.colour = value;
    }

    public LocalDateTime getIssueDate() {
        return this.issueDate;
    }

    public void setIssueDate(LocalDateTime value) {
        this.issueDate = value;
    }

    public Team getTeam() {
      return this.team;
    }
    public void setTeam(Team value) {
      this.team = value;
    }
}
