package org.lecture.model;

/*
* Copyright (c) 2015 .
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 3 of the License, or
* (at your option) any later version.
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software Foundation,
* Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entity that represents Users.
 * @author Rene Richter
 */
@Entity
@Table(name="users")
public class User extends BaseEntity implements UserDetails {

  @Column(unique = true)
  private String username;
  @JsonIgnore
  private String password;
  private boolean enabled;
  @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
  private List<Authority> authorities = new ArrayList<>();
  

  public User(){}


  /**
   * a convenience constructor.
   */
  public  User(String username,String password) {

    this.username = username;
    this.password = password;
    this.enabled = true;
    
  }

  public void setAuthorities(List<Authority> authorities) {
    this.authorities = authorities;
  }

  public void addAuthority(Authority authority) {

  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  
  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public String getPassword() {
    return this.password;
  }
  
}
