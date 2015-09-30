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

import org.apache.http.auth.AUTH;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Entity that represents Users.
 * @author Rene Richter
 */
@Entity
public class User extends BaseEntity implements UserDetails {

  private String username;
  private String password;
  private boolean enabled;
  private List<Authority> authoities = new ArrayList<>();
  

  public User(){}


  /**
   * a convenience constructor.
   */
  public  User(String username,String password) {

    this.username = username;
    this.password = password;
    this.enabled = true;
    
  }

  public List<Authority> getAuthoities() {
    return authoities;
  }

  public void setAuthoities(List<Authority> authoities) {
    this.authoities = authoities;
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
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.enabled;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authoities;
  }

  public String getPassword() {
    return this.password;
  }
  
}
