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
@Table(name = "players")
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "First Name shouldn't be null")
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NotNull(message = "Last Name shouldn't be null")
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@NotNull(message = "Last Name shouldn't be null")
	@Column(name = "username", nullable = false)
	private String username;

	@NotNull(message = "Email  shouldn't be null")
	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@NotNull
	@Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private boolean isDeleted;

	@JoinColumn(name = "team_id", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private Team team;

	@Override
	public String toString() {
		return "Player{" +
				"id=" + id +
				", username='" + username + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName=" + lastName +
				", email=" + email +
				", isDeleted=" + isDeleted +
				"}";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(boolean value) {
		this.isDeleted = value;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String value) {
		this.username = value;
	}

    public Team getTeam() {
      return this.team;
    }
    public void setTeam(Team value) {
      this.team = value;
    }
}
