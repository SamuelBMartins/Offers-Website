package ch.supsi.webapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SubCategory {
    @Id
    private String name;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Category category;

    public SubCategory() {
    }

    public SubCategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
