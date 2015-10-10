package org.lecture.config;

import org.lecture.repository.UserRepository;
import org.lecture.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by rene on 10.10.15.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  UserRepository userRepository;
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(new UserDetailServiceImpl(userRepository));
  }
}
