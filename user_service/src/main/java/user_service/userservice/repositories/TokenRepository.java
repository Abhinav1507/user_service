package user_service.userservice.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import user_service.userservice.models.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    //Token save(Token token);

    Optional<Token> findByValueAndDeletedEquals(String token,boolean deleted);

    Optional<Token> findByValueAndDeletedAndExpireAtGreaterThan(String value, boolean deleted , Date ExpireAt);
    
}

