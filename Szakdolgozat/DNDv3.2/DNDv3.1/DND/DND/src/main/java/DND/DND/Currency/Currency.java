package DND.DND.Currency;

import jakarta.persistence.*;

@Entity
@Table(name = "currency")
public class Currency
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private int copper;
    private int silver;
    private int gold;
    private int platinum;

    public Currency(){};

    public Currency(long id, String name, String email, int copper, int silver, int gold, int platinum) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.copper = copper;
        this.silver = silver;
        this.gold = gold;
        this.platinum = platinum;
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

    public int getCopper() {
        return copper;
    }

    public void setCopper(int copper) {
        this.copper = copper;
    }

    public int getSilver() {
        return silver;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getPlatinum() {
        return platinum;
    }

    public void setPlatinum(int platinum) {
        this.platinum = platinum;
    }
}
