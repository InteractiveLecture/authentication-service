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

import org.lecture.model.User;
import org.springframework.hateoas.ResourceSupport;

/**
 * A User-resource.
 * @author Rene Richter
 */
public class UserResource extends ResourceSupport {
  
  private String username;
  
  private String password;
  

  /**
   * Reads all attributes from entity that should get serialized.
   */
  public  UserResource( User entity) {
    
    this.username = entity.getUsername();
    
    this.password = entity.getPassword();
    
  }

  
  public void setUsername(String username) {
    this.username = username;
  }

  public String getUsername() {
    return this.username;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }

  public String getPassword() {
    return this.password;
  }
  
}
