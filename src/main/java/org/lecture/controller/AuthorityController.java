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

import org.lecture.model.Authority;
import org.lecture.resource.AuthorityResource;
import org.lecture.repository.AuthorityRepository;
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
 * A controller for Authority Routes.
 * @author Rene Richter
 */
@RestController
@RequestMapping("/authorities")
@ExposesResourceFor(Authority.class)
public class AuthorityController extends BaseController {


  @Autowired
  AuthorityRepository authorityRepository;


  /**
   * Returns a list of authoritys.
   *
   * @param pageable  The number of items, gotten through the url
   * @return a Resource representing the page.
   */
  @RequestMapping(method = RequestMethod.GET)
  public Page<Authority> getAll(@PageableDefault(size = 20, page = 0)
                                         Pageable pageable) {
    return this.authorityRepository.findAll(pageable);
  }

  /**
   * Creates a new Authority
   * @param entity the authority from the post-request. This authority is deserialized by
   *              jackson.
   * @return A respoonse containing a link to the new resource.
   */
  @RequestMapping(method = RequestMethod.POST)
  public void create(@RequestBody Authority entity) {
    this.authorityRepository.save(entity);
  }

  /**
   * Returns one Authority.
   *
   * @param id the id of the  authority to return.
   * @return a response.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public AuthorityResource getOne(@PathVariable String id) {
    return new AuthorityResource(authorityRepository.findOne(id));
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<?> delete(@PathVariable String id) {
    authorityRepository.delete(id);
    return ResponseEntity.noContent().build();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<?> update(@PathVariable String id,
                                  @RequestBody Authority newValues) {
    newValues.setId(id);
    authorityRepository.save(newValues);
    return ResponseEntity.noContent().build();
  }


}
