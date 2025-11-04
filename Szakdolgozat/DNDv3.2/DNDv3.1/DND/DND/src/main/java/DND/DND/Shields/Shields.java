package DND.DND.Shields;

import jakarta.persistence.*;

@Entity
@Table(name = "shields")
public class Shields
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private String shield_name;
    private String ac;
    private int weight;
    private String properties;

    public Shields(){};

    public Shields(long id, String name, String email, String shield_name, String ac, int weight, String properties) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.shield_name = shield_name;
        this.ac = ac;
        this.weight = weight;
        this.properties = properties;
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

    public String getShield_name() {
        return shield_name;
    }

    public void setShield_name(String shield_name) {
        this.shield_name = shield_name;
    }

    public String getAc() {
        return ac;
    }

    public void setAc(String ac) {
        this.ac = ac;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
