package ch.supsi.webapp.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {

	@Id
	private String name;

	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	Set<SubCategory> subCategory;

	public Category() {
		subCategory = new HashSet<>();
	}

	public Category(String name) {
		subCategory = new HashSet<>();
		this.name = name;
	}

	public Set<SubCategory> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<SubCategory> subCategory) {
		this.subCategory = subCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String category) {
		this.name = category;
	}

	@Override
	public String toString() {
		return getName();
	}

}
