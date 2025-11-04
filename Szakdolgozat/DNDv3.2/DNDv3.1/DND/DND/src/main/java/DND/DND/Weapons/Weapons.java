package DND.DND.Weapons;

import jakarta.persistence.*;

@Entity
@Table(name = "weapons")
public class Weapons
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private String weapon_name;
    private int cost;
    private String damage;
    private int weight;
    private String properties;

    public Weapons()
    {
        super();
    }

    public Weapons(long id, String name, String email, String weapon_name, int cost, String damage, int weight, String properties) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.weapon_name = weapon_name;
        this.cost = cost;
        this.damage = damage;
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

    public String getWeapon_name() {
        return weapon_name;
    }

    public void setWeapon_name(String weapon_name) {
        this.weapon_name = weapon_name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDamage() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage = damage;
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
