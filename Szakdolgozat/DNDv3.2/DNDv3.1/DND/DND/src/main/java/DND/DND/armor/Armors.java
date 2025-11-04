package DND.DND.armor;

import jakarta.persistence.*;

@Entity
@Table(name = "armors")
public class Armors
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private String armor_name;
    private String ac;
    private int weight;
    private String properties;

    public Armors(){};

    public Armors(long id, String name, String email, String armor_name, String ac, int weight, String properties) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.armor_name = armor_name;
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

    public String getArmor_name() {
        return armor_name;
    }

    public void setArmor_name(String armor_name) {
        this.armor_name = armor_name;
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
