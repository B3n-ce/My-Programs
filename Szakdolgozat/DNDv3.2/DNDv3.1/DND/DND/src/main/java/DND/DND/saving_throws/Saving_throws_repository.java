package DND.DND.saving_throws;

import DND.DND.Skills.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Saving_throws_repository extends JpaRepository<Saving_throws, Long>
{
    @Query("SELECT s FROM Saving_throws s WHERE s.name = :name AND s.email = :email")
    List<Saving_throws> findSavingThrowsByEmailAndName(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Saving_throws s WHERE s.name = :name AND s.email = :email")
    void deleteSavingThrowByNameAndEmail(@Param("name") String name, @Param("email") String email);

}
