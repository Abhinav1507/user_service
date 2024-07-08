package user_service.userservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user_service.userservice.models.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByemail(String Email);
}
