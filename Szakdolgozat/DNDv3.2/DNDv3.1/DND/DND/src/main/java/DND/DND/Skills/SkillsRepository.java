package DND.DND.Skills;

import DND.DND.Characters.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillsRepository extends JpaRepository<Skills, Long>
{
    @Query("SELECT s FROM Skills s WHERE s.name = :name AND s.email = :email")
    List<Skills> findCharactersBySkill(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Skills s WHERE s.name = :name AND s.email = :email")
    void deleteSkillByNameAndEmail(@Param("name") String name, @Param("email") String email);

}
