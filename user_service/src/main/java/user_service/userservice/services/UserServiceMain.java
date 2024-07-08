package user_service.userservice.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import user_service.userservice.models.Token;
import user_service.userservice.models.User;
import user_service.userservice.repositories.TokenRepository;
import user_service.userservice.repositories.UserRepository;

@Service
public class UserServiceMain implements UserService {

    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceMain(UserRepository userRepository,TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository=userRepository;
        this.tokenRepository=tokenRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public User signup(String fullname, String email, String password) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'signup'");
        String password_enc=bCryptPasswordEncoder.encode(password);
        Optional<User> prevUser = userRepository.findByemail(email);
        if (prevUser.isEmpty()){
            User user = new User();
            user.setEmail(email);
            user.setName(fullname);
            user.setHashpassword(password_enc);
            User u =userRepository.save(user);
            return u;
        }
        
        return null;
    }

    @Override
    public Token login(String Email, String password) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'login'");
        Optional<User> prevUser = userRepository.findByemail(Email);
        if (prevUser.isEmpty()){
            return null;
        }
        User user = prevUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getHashpassword())){
            return null;
        }
        Token token = getToken(user);
        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }

    public static Token getToken(User user){
        LocalDate today = LocalDate.now();
        LocalDate ThirtydaysLater = today.plus(30,ChronoUnit.DAYS);
        Date expireAt=Date.from(
            ThirtydaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setExpireAt(expireAt);
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        return token;
    }

    @Override
    public void logout(String token) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'logout'");
        Optional<Token> tokens = tokenRepository.findByValueAndDeletedEquals(token, false);
        if(tokens.isEmpty()){
            return;
        }
        Token tk=tokens.get();
        tk.setDeleted(true);
        tokenRepository.save(tk);
        return;
    }

    @Override
    public User validateToken(String token) {
        //TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'validateToken'");
        Optional<Token> tokens = tokenRepository.findByValueAndDeletedAndExpireAtGreaterThan(token, false, new Date());
        if (tokens.isEmpty()){
            return null;
        }
        return tokens.get().getUser();
    }
    
}
