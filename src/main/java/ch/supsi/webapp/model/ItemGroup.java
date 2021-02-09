package ch.supsi.webapp.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ItemGroup {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private String description;
    private Date date;

    @ManyToOne
    private User creator;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> members;

    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private Set<Item> items;

    public ItemGroup() {
        members = new HashSet<>();
        items = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
