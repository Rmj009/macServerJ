package com.asecl.simdc.org.simdc_project.security.jwt;

import com.asecl.simdc.org.simdc_project.db.entity.Role;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetail implements UserDetails {

    private User mUser;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetail(User mUser, Collection<? extends GrantedAuthority> authorities) {
        this.mUser = mUser;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.mUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.mUser.getEmployeeID();
    }

    public User getUser() {
        return mUser;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
