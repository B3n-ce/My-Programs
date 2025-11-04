package DND.DND.Characters;

import DND.DND.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "characters")
public class Character
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private int level;
    private String race;
    private String klass;
    private String background;
    private String alignment;
    private int strength;
    private int strength_b;
    private int dexterity;
    private int dexterity_b;
    private int constitution;
    private int constitution_b;
    private int intelligence;
    private int intelligence_b;
    private int wisdom;
    private int wisdom_b;
    private int charisma;
    private int charisma_b;
    private String user;
    private int hp;
    private int xp;
    private int proficiency_bonus;
    private int initiative;
    private int armor_class;
    private int hit_dice;
    private int speed;
    private int deity;
    private String image_URL;


    public Character(){};

    public Character(long id, String name, int level, String race, String klass, String background, String alignment, int strength, int strength_b, int dexterity, int dexterity_b, int constitution, int constitution_b, int intelligence, int intelligence_b, int wisdom, int wisdom_b, int charisma, int charisma_b, String user, int hp, int xp, int proficiency_bonus, int initiative, int armor_class, int hit_dice, int speed, int deity, String url) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.race = race;
        this.klass = klass;
        this.background = background;
        this.alignment = alignment;
        this.strength = strength;
        this.strength_b = strength_b;
        this.dexterity = dexterity;
        this.dexterity_b = dexterity_b;
        this.constitution = constitution;
        this.constitution_b = constitution_b;
        this.intelligence = intelligence;
        this.intelligence_b = intelligence_b;
        this.wisdom = wisdom;
        this.wisdom_b = wisdom_b;
        this.charisma = charisma;
        this.charisma_b = charisma_b;
        this.user = user;
        this.hp = hp;
        this.xp = xp;
        this.proficiency_bonus = proficiency_bonus;
        this.initiative = initiative;
        this.armor_class = armor_class;
        this.hit_dice = hit_dice;
        this.speed = speed;
        this.deity = deity;
        this.image_URL = url;
    }

    public String getImage_URL() {
        return image_URL;
    }

    public void setImage_URL(String image_URL) {
        this.image_URL = image_URL;
    }

    public int getStrength_b() {
        return strength_b;
    }

    public void setStrength_b(int strength_b) {
        this.strength_b = strength_b;
    }

    public int getDexterity_b() {
        return dexterity_b;
    }

    public void setDexterity_b(int dexterity_b) {
        this.dexterity_b = dexterity_b;
    }

    public int getConstitution_b() {
        return constitution_b;
    }

    public void setConstitution_b(int constitution_b) {
        this.constitution_b = constitution_b;
    }

    public int getIntelligence_b() {
        return intelligence_b;
    }

    public void setIntelligence_b(int intelligence_b) {
        this.intelligence_b = intelligence_b;
    }

    public int getWisdom_b() {
        return wisdom_b;
    }

    public void setWisdom_b(int wisdom_b) {
        this.wisdom_b = wisdom_b;
    }

    public int getCharisma_b() {
        return charisma_b;
    }

    public void setCharisma_b(int charisma_b) {
        this.charisma_b = charisma_b;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getProficiency_bonus() {
        return proficiency_bonus;
    }

    public void setProficiency_bonus(int proficiency_bonus) {
        this.proficiency_bonus = proficiency_bonus;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public int getArmor_class() {
        return armor_class;
    }

    public void setArmor_class(int armor_class) {
        this.armor_class = armor_class;
    }

    public int getHit_dice() {
        return hit_dice;
    }

    public void setHit_dice(int hit_dice) {
        this.hit_dice = hit_dice;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDeity() {
        return deity;
    }

    public void setDeity(int deity) {
        this.deity = deity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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

    public String getKlass() {
        return klass;
    }

    public void setKlass(String klass) {
        this.klass = klass;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", race='" + race + '\'' +
                ", klass='" + klass + '\'' +
                ", background='" + background + '\'' +
                ", alignment='" + alignment + '\'' +
                ", strength=" + strength +
                ", dexterity=" + dexterity +
                ", constitution=" + constitution +
                ", intelligence=" + intelligence +
                ", wisdom=" + wisdom +
                ", charisma=" + charisma +
                ", user='" + user + '\'' +
                '}';
    }
}
