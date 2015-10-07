package org.lecture.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by rene on 05.10.15.
 */
@Controller
public class RedirectionController {

  @RequestMapping("/")
  public String forwardRoot() {
    return "forward: /user-web-ui";
  }
}
