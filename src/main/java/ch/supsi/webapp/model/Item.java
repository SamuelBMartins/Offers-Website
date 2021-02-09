package ch.supsi.webapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Item {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;
    private Date date;
    private Announce type;

    @Lob
    private byte[] image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private User author;

    @ManyToOne
    private SubCategory category;

    @ManyToOne
    private ItemGroup group;

    public Item() {
    }

    public Item(String title, String description, User author, SubCategory category, Announce type, byte[] image) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.category = category;
        this.type = type;
        this.image = image;
        date = new Date();
    }

    public ItemGroup getGroup() {
        return group;
    }

    public void setGroup(ItemGroup group) {
        this.group = group;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public SubCategory getCategory() {
        return category;
    }

    public void setCategory(SubCategory category) {
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Announce getType() {
        return type;
    }

    public void setType(Announce type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return getId().equals(item.getId());
    }

    @Override
    public int hashCode() {
        return getId();
    }

}
