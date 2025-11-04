package DND.DND.ChatRoom.Maps;

import jakarta.persistence.*;

@Entity
@Table(name = "maps")
public class Map
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String email;
    private String map_name;
    @Column(columnDefinition = "TEXT")
    private String map_data;

    public Map(){};

    public Map(long id, String email, String map_name, String map_data) {
        this.id = id;
        this.email = email;
        this.map_name = map_name;
        this.map_data = map_data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMap_name() {
        return map_name;
    }

    public void setMap_name(String map_name) {
        this.map_name = map_name;
    }

    public String getMap_data() {
        return map_data;
    }

    public void setMap_data(String map_data) {
        this.map_data = map_data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}


