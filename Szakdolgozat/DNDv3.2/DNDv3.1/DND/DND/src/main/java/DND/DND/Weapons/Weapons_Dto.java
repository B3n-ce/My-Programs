package DND.DND.Weapons;

public class Weapons_Dto
{
    private String name;
    private String email;
    private String weapon_name;
    private int cost;
    private String damage;
    private int weight;
    private String properties;

    public Weapons_Dto(){};

    public Weapons_Dto(String name, String email, String weapon_name, int cost, String damage, int weight, String properties) {
        this.name = name;
        this.email = email;
        this.weapon_name = weapon_name;
        this.cost = cost;
        this.damage = damage;
        this.weight = weight;
        this.properties = properties;
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
