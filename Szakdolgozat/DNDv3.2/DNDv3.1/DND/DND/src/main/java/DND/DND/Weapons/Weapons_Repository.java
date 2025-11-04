package DND.DND.Weapons;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Weapons_Repository extends JpaRepository<Weapons, Long>
{
    @Query("SELECT w FROM Weapons w WHERE w.email = :email AND w.name = :name")
    List<Weapons> findWeaponsByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Weapons w WHERE w.name = :name AND w.email = :email")
    void deleteWeaponByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
