package user_service.userservice.Dtos;

import lombok.Getter;
import lombok.Setter;
//import user_service.userservice.models.Token;

@Getter
@Setter
public class LogoutRequestDto {
    private String token;
}
