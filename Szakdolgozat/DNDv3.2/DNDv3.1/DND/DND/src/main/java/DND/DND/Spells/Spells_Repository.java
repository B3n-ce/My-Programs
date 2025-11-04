package DND.DND.Spells;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Spells_Repository extends JpaRepository<Spells, Long>
{
    @Query("SELECT s FROM Spells s WHERE s.name = :name AND s.email = :email")
    List<Spells> findSpellsByCharacter(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Spells s WHERE s.name = :name AND s.email = :email")
    void deleteSpellsByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
