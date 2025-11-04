package DND.DND.Character_Notes;

import DND.DND.armor.Armors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Characters_Notes_Repository extends JpaRepository<Characters_Notes, Long>
{
    @Query("SELECT c FROM Characters_Notes c WHERE c.email = :email AND c.name = :name")
    Characters_Notes findCharacters_NotesByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Characters_Notes c WHERE c.name = :name AND c.email = :email")
    void deleteCharacters_NotesByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
