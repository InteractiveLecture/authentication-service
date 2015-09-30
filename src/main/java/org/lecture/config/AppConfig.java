package org.lecture.config;

/*
* Copyright (c) 2015 Rene Richter.
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

import org.lecture.repository.UserRepository;
import org.lecture.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
* Application config-class.
*
* @author  Rene Richter
*/

@Configuration
@EnableWebMvc
@EnableJpaRepositories(basePackages = "org.lecture.repository")
@EntityScan(basePackages = "org.lecture.repository")
@EnableSpringDataWebSupport
@EnableRedisHttpSession
public class AppConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserRepository userRepository;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    super.configure(web);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
            //.antMatchers("/login", "/register").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().disable();
            //.and()
            //.userDetailsService(new UserDetailServiceImpl(userRepository));
  }

    @Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
        return new HeaderHttpSessionStrategy();
    }

}
