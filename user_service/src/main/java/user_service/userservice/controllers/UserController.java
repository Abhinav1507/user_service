package user_service.userservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import user_service.userservice.Dtos.LoginRequestDto;
import user_service.userservice.Dtos.LogoutRequestDto;
import user_service.userservice.Dtos.SignUpRequest;
import user_service.userservice.Dtos.UserDto;
import user_service.userservice.models.Token;
import user_service.userservice.models.User;
import user_service.userservice.services.UserService;

@RestController
@RequestMapping("/userservice")
public class UserController {
    private UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto request){
        return userService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequest signUpRequest){
        User u =userService.signup(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());
        return UserDto.from(u);
    }

    @PostMapping("/logout")
    public  ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        userService.logout(logoutRequestDto.getToken());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String token) {
        return UserDto.from(userService.validateToken(token));
    }


}
