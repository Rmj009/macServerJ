package com.asecl.simdc.org.simdc_project.security.jwt;

import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.service.UserService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService mUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = null;
        List<GrantedAuthority> authorities = new ArrayList<>();
        try{
            user = mUserService.getByEmployeeID(s);
            if(null == user){
                throw new RuntimeException("Not Found Account By EmployeeID");
            }
            authorities.add(new SimpleGrantedAuthority(user.getRole().getName()));
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return new JwtUserDetail(user, authorities);
    }
}
