package org.lecture.integration.user;

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

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration class for org.lecture integration test.
 * @author Rene Richter
 */
@Configuration
@ComponentScan(basePackages = {"org.lecture.repository",
    "org.lecture.controller",
    "org.lecture.assembler",
    "org.lecture.resource"})

@EnableHypermediaSupport(type = {EnableHypermediaSupport.HypermediaType.HAL})
@EnableSpringDataWebSupport
@EnableWebMvc
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "org.lecture.repository")
@EnableTransactionManagement
@EntityScan(basePackages = {"org.lecture.model"})
public class UserIntegrationTestConfig extends WebMvcConfigurerAdapter {
}
