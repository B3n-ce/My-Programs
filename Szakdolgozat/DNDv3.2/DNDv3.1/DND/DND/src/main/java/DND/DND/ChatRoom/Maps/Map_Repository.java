package DND.DND.ChatRoom.Maps;

import DND.DND.armor.Armors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Map_Repository extends JpaRepository<Map, Long>
{
    @Query("SELECT m FROM Map m WHERE m.email = :email")
    List<Map> findAllMaps(@Param("email") String email);
}
