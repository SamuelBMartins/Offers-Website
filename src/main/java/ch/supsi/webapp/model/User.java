package ch.supsi.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class User {

	@Id
	private String username;
	private String name;
	private String surname;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private Role role;

	private String password;

	public User() {
	}

	public User(String username, String name, String surname, Role role, String password) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.role = role;
		setPassword(password);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return getUsername();
	}
}
