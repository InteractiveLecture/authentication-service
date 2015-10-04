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

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entity that represents Authoritys.
 * @author Rene Richter
 */
@Entity
@Table(name = "authorities")
public class Authority extends BaseEntity implements GrantedAuthority {

  private String authority;
  @ManyToOne
  private User user;
  

  public Authority(){}


  /**
   * a convenience constructor.
   */
  public  Authority(String authority,User user) {
    this.authority = authority;
    this.user = user;
    
  }
  
  public void setAuthority(String authority) {
    this.authority = authority;
  }

  public String getAuthority() {
    return this.authority;
  }
  
  public void setUser(org.lecture.model.User user) {
    this.user = user;
  }

  public org.lecture.model.User getUser() {
    return this.user;
  }
  
}
