package com.jiang.blog.model.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails, Serializable {

    private List<String> permissions;
    @Autowired
    private User user;
    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authoritys;

    public LoginUser(User user, List<String> permissions) {
        this.permissions = permissions;
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //把permissions中String类型的权限信息封装成SimpleGrantedAuthority对象
        if (authoritys != null) {
            return authoritys;
        }

        authoritys = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authoritys;

    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
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
