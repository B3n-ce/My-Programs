package DND.DND.Spells;

import jakarta.persistence.*;

@Entity
@Table(name = "spells")
public class Spells
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String email;
    private String name;
    private String spell_name;
    private String casting_action;
    private String range_area;
    private String spell_school;
    private String spell_level;
    @Column(columnDefinition = "TEXT")
    private String spell_description;
    private String spell_duration;

    public Spells(){}

    public Spells(long id, String email, String character_name, String spell_name, String casting_action, String range_area, String spell_school, String spell_level, String spell_description, String spell_duration) {
        this.id = id;
        this.email = email;
        this.name = character_name;
        this.spell_name = spell_name;
        this.casting_action = casting_action;
        this.range_area = range_area;
        this.spell_school = spell_school;
        this.spell_level = spell_level;
        this.spell_description = spell_description;
        this.spell_duration = spell_duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell_name() {
        return spell_name;
    }

    public void setSpell_name(String spell_name) {
        this.spell_name = spell_name;
    }

    public String getCasting_action() {
        return casting_action;
    }

    public void setCasting_action(String casting_action) {
        this.casting_action = casting_action;
    }

    public String getRange_area() {
        return range_area;
    }

    public void setRange_area(String range_area) {
        this.range_area = range_area;
    }

    public String getSpell_school() {
        return spell_school;
    }

    public void setSpell_school(String spell_school) {
        this.spell_school = spell_school;
    }

    public String getSpell_level() {
        return spell_level;
    }

    public void setSpell_level(String spell_level) {
        this.spell_level = spell_level;
    }

    public String getSpell_description() {
        return spell_description;
    }

    public void setSpell_description(String spell_description) {
        this.spell_description = spell_description;
    }

    public String getSpell_duration() {
        return spell_duration;
    }

    public void setSpell_duration(String spell_duration) {
        this.spell_duration = spell_duration;
    }
}
