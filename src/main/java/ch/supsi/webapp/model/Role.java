package ch.supsi.webapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {

	@Id
	private String name;

	public Role() {
	}

	public Role(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String role) {
		this.name = "ROLE_" + role.toUpperCase();
	}

	@Override
	public String toString() {
		return getName();
	}

}
