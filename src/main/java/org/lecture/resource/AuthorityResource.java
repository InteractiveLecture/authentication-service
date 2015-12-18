package org.lecture.resource;

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

import org.lecture.model.Authority;
import org.springframework.hateoas.ResourceSupport;

/**
 * A Authority-resource.
 * @author Rene Richter
 */
public class AuthorityResource extends ResourceSupport {
  
  private String id;
  
  private String authority;
  
  private org.lecture.model.User user;
  

  /**
   * Reads all attributes from entity that should get serialized.
   */
  public  AuthorityResource( Authority entity) {
    
    this.id = entity.getId();
    
    this.authority = entity.getAuthority();
    
    this.user = entity.getUser();
    
  }

  
  public void setId(String id) {
    this.id = id;
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
