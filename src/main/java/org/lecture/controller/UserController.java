package org.lecture.controller;

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

import org.lecture.assembler.UserAssembler;
import org.lecture.model.User;
import org.lecture.resource.UserResource;
import org.lecture.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * A controller for User Routes.
 * @author Rene Richter
 */
@RestController
@RequestMapping("/users")
@ExposesResourceFor(User.class)
public class UserController extends BaseController {

  @Autowired
  UserAssembler userAssembler;

  @Autowired
  UserRepository userRepository;


  /**
   * Returns a list of users.
   *
   * @param pageable  The number of items, gotten through the url
   * @param assembler the assembler injected by spring.
   * @return a Resource representing the page.
   */
  @RequestMapping(method = RequestMethod.GET)
  public PagedResources<User> getAll(@PageableDefault(size = 20, page = 0)
                                         Pageable pageable,
                                         PagedResourcesAssembler assembler) {

    Page<User> pageResult = this.userRepository.findAll(pageable);
    return assembler.toResource(pageResult, userAssembler);
  }

  /**
   * Creates a new User
   * @param entity the user from the post-request. This user is deserialized by
   *              jackson.
   * @return A respoonse containing a link to the new resource.
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> create(@RequestBody User entity) {
    return super.createEntity(this.userRepository.save(entity));
  }

  /**
   * Returns one User.
   *
   * @param username the username of the  user to return.
   * @return a response.
   */
  @RequestMapping(value = "/{username}", method = RequestMethod.GET)
  public ResponseEntity<UserResource> getOne(@PathVariable String username) {
    UserResource result
        = userAssembler.toResource(userRepository.findByUsername(username));
    return ResponseEntity.ok().body(result);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> delete(@PathVariable Long id) {
    userRepository.delete(id);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<?> update(@PathVariable Long id,
                                  @RequestBody User newValues) {
    newValues.setId(id);
    userRepository.save(newValues);
    return ResponseEntity.noContent().build();
  }


}
