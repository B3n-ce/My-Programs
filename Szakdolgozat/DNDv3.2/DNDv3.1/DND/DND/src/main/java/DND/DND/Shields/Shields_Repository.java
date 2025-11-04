package DND.DND.Shields;

import DND.DND.armor.Armors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Shields_Repository extends JpaRepository<Shields, Long>
{
    @Query("SELECT s FROM Shields s WHERE s.email = :email AND s.name = :name")
    List<Shields> findShieldsByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Shields s WHERE s.name = :name AND s.email = :email")
    void deleteShieldsByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
