package org.lecture.controller;

import org.lecture.assembler.UserAssembler;
import org.lecture.model.User;
import org.lecture.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by rene on 05.10.15.
 */
@RestController
@RequestMapping("/me")
public class LoginController {

  @Autowired
  private UserAssembler userAssembler;

  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<UserResource> login(@AuthenticationPrincipal User activeUser) {

    System.out.println(activeUser);
    UserResource resource = new UserResource(activeUser);
    resource.add(linkTo(LoginController.class).withRel("self"));
    return ResponseEntity.ok(resource);

  }
}
