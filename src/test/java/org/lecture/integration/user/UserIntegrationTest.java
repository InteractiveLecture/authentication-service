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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static util.TestUtil.toJson;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.lecture.model.User;
import org.lecture.integration.user.UserIntegrationTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import util.TestUtil;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static util.TestUtil.toJson;


/**
 * A integration test for Users
 * @author Rene Richter
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserIntegrationTestConfig.class})
@WebAppConfiguration
//TODO add custom sample-data in userSampleData.xml
@DatabaseSetup("/sampledata/userSampleData.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
public class UserIntegrationTest {

  private MockMvc mockMvc;


  @Autowired
  private WebApplicationContext webApplicationContext;

  /**
   * sets up the test.
   */
  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }


  @Test
  public void testGetUsers() throws Exception {

    mockMvc.perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.HAL_JSON));
  }

  @Test
  public void itShouldContainLinksToModulesOfficersAssistantsSelfAndRootModule()
      throws Exception {
    mockMvc.perform(get("/users/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.HAL_JSON))
        .andExpect(
            jsonPath("$._links.self.href", is("http://localhost/users/1")));
  }

  @Test
  public void testCreateUser() throws Exception {
    mockMvc.perform(post("/users")
        .contentType(TestUtil.APPLICATION_JSON_UTF8)
        .content(toJson(new User())))
        .andExpect(status().isCreated())
        .andExpect(redirectedUrl("http://localhost/users/11"));

  }
}