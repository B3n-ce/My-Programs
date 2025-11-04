package DND.DND.armor;

import DND.DND.Weapons.Weapons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Armors_Repository extends JpaRepository<Armors, Long>
{
    @Query("SELECT a FROM Armors a WHERE a.email = :email AND a.name = :name")
    List<Armors> findArmorsByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Armors a WHERE a.name = :name AND a.email = :email")
    void deleteArmorsByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
