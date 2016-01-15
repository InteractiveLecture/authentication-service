package org.lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by rene on 05.10.15.
 */
@RestController
@RequestMapping("/me")
public class LoginController {

  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public Principal login(Principal principal) {
    return principal;
  }
}
