package org.lecture.assembler;

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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.lecture.model.Authority;
import org.lecture.controller.AuthorityController;
import org.lecture.resource.AuthorityResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 * Assembler to create Authority resources.
 * @author Rene Richter
 */
@Component
public class AuthorityAssembler extends BaseAssembler<Authority, AuthorityResource> {

  /**
   * Creates a new {@link ResourceAssemblerSupport}
   * using the given controller class and resource type.
   */
  public AuthorityAssembler() {
    super(AuthorityController.class, AuthorityResource.class);
  }


  @Autowired
  EntityLinks entityLinks;


  @Override
  public AuthorityResource toResource(Authority entity) {
    AuthorityResource resource = createResource(entity);

    //TODO add links

    return resource;
  }
}