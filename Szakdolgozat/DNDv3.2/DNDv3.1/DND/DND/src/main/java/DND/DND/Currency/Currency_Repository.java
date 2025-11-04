package DND.DND.Currency;

import DND.DND.armor.Armors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Currency_Repository extends JpaRepository<Currency, Long>
{
    @Query("SELECT c FROM Currency c WHERE c.email = :email AND c.name = :name")
    Currency findCurrenciesByNameAndEmail(@Param("name") String name, @Param("email") String email);

    @Modifying
    @Query("DELETE FROM Currency c WHERE c.name = :name AND c.email = :email")
    void deleteCurrenciesByNameAndEmail(@Param("name") String name, @Param("email") String email);
}
