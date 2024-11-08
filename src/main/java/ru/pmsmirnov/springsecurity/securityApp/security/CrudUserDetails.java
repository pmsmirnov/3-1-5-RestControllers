package ru.pmsmirnov.springsecurity.securityApp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.pmsmirnov.springsecurity.securityApp.models.CrudUser;

import java.util.Collection;

public class CrudUserDetails implements UserDetails {

    private final CrudUser crudUser;

    public CrudUserDetails (CrudUser cu) {
        this.crudUser = cu;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return crudUser.getRoles();
    }

    @Override
    public String getPassword() {
        return this.crudUser.getPass();
    }

    @Override
    public String getUsername() {
        return this.crudUser.getNickName();
    }

    public CrudUser getCrudUser() {
        return this.crudUser;
    }
}
