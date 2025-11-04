package DND.DND.saving_throws;
import jakarta.persistence.*;

@Entity
@Table(name = "saving_throws")
public class Saving_throws
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private int str;
    private boolean str_p;
    private int dex;
    private boolean dex_p;
    private int con;
    private boolean con_p;
    private int intelligence;
    private boolean intelligence_p;
    private int wis;
    private boolean wis_p;
    private int cha;
    private boolean cha_p;

    public Saving_throws()
    {
        super();
    }

    public Saving_throws(long id, String name, String email, int str, boolean str_p, int dex, boolean dex_p, int con, boolean con_p, int intelligence, boolean intelligence_p, int wis, boolean wis_p, int cha, boolean cha_p) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.str = str;
        this.str_p = str_p;
        this.dex = dex;
        this.dex_p = dex_p;
        this.con = con;
        this.con_p = con_p;
        this.intelligence = intelligence;
        this.intelligence_p = intelligence_p;
        this.wis = wis;
        this.wis_p = wis_p;
        this.cha = cha;
        this.cha_p = cha_p;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStr() {
        return str;
    }

    public void setStr(int str) {
        this.str = str;
    }

    public boolean isStr_p() {
        return str_p;
    }

    public void setStr_p(boolean str_p) {
        this.str_p = str_p;
    }

    public int getDex() {
        return dex;
    }

    public void setDex(int dex) {
        this.dex = dex;
    }

    public boolean isDex_p() {
        return dex_p;
    }

    public void setDex_p(boolean dex_p) {
        this.dex_p = dex_p;
    }

    public int getCon() {
        return con;
    }

    public void setCon(int con) {
        this.con = con;
    }

    public boolean isCon_p() {
        return con_p;
    }

    public void setCon_p(boolean con_p) {
        this.con_p = con_p;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public boolean isIntelligence_p() {
        return intelligence_p;
    }

    public void setIntelligence_p(boolean intelligence_p) {
        this.intelligence_p = intelligence_p;
    }

    public int getWis() {
        return wis;
    }

    public void setWis(int wis) {
        this.wis = wis;
    }

    public boolean isWis_p() {
        return wis_p;
    }

    public void setWis_p(boolean wis_p) {
        this.wis_p = wis_p;
    }

    public int getCha() {
        return cha;
    }

    public void setCha(int cha) {
        this.cha = cha;
    }

    public boolean isCha_p() {
        return cha_p;
    }

    public void setCha_p(boolean cha_p) {
        this.cha_p = cha_p;
    }
}
