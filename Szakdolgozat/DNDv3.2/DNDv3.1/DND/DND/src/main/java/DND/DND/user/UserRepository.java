package DND.DND.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.imageurl = :imageurl WHERE u.name = :name AND u.email = :email")
    void updateUserImageByNameAndEmail(@Param("imageurl") String imageurl, @Param("name") String name, @Param("email") String email);

    @Query("SELECT u.imageurl FROM User u WHERE u.name = :name AND u.email = :email")
    String findUserImageByNameAndEmail(@Param("name") String name, @Param("email") String email);

}
