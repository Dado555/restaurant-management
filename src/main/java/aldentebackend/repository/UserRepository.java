package aldentebackend.repository;

import aldentebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u join fetch u.authorities where u.username=:email")
    Optional<User> findByEmailFetchAuthorities(@Param("email") String email);

    @Query("select u from User u join fetch u.authorities where u.id=:id")
    Optional<User> findOneFetchAuthorities(@Param("id") Long id);

    @Query("select u from User u join fetch u.authorities where u.active = true")
    List<User> findAllWithAuthorities();

    @Query("select u from User u join fetch u.authorities as auths where auths.name = 'BARTENDER' or auths.name = 'COOK' or auths.name = 'WAITER'")
    List<User> findAllWorkers();
}
