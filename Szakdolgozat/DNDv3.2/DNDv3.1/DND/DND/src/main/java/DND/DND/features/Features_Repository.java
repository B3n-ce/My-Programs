package DND.DND.features;

import DND.DND.Skills.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface Features_Repository extends JpaRepository<Features, Long>
{
    @Query("SELECT f FROM Features f WHERE f.name = :name AND f.email = :email")
    Features findFeaturesByCharacter(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Features f WHERE f.name = :name AND f.email = :email")
    void deleteFeaturesByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
