package user_service.userservice.security.models;

import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import user_service.userservice.models.Role;

@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
//    private Role role;
    private String authority;

    public CustomGrantedAuthority() {}


    public CustomGrantedAuthority(Role role) {
//        this.role = role;
        this.authority = role.getName();
    }

    @Override
    public String getAuthority() {
//        return role.getName();
        return authority;
    }
}
