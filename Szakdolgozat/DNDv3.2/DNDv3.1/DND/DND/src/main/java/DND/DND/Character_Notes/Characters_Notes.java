package DND.DND.Character_Notes;

import jakarta.persistence.*;

@Entity
@Table(name = "character_notes")
public class Characters_Notes
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    @Column(columnDefinition = "TEXT")
    private String notes;
    @Column(columnDefinition = "TEXT")
    private String inventory;
    @Column(columnDefinition = "TEXT")
    private String story;

    public Characters_Notes(){};

    public Characters_Notes(long id, String name, String email, String notes, String inventory, String story) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.notes = notes;
        this.inventory = inventory;
        this.story = story;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
