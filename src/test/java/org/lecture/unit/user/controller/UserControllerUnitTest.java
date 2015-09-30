package org.lecture.unit.user.controller;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.lecture.assembler.UserAssembler;
import org.lecture.controller.UserController;
import org.lecture.repository.UserRepository;
import org.lecture.resource.UserResource;
import org.lecture.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

/**
* Unit test for User controllers.
* @author Rene Richter
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserControllerUnitTestConfig.class})
public class UserControllerUnitTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserAssembler userAssembler;

  @Autowired
  private PagedResourcesAssembler pagedResourcesAssembler;

  @Autowired
  private UserController testInstance;


  /**
   * sets up the test.
   */
  @Before
  public void setUp() {
    reset(userRepository,userAssembler,pagedResourcesAssembler);
  }


  @Test
  public void getAllShouldReturnAPageOfUser() throws Exception {

    List<User> sampleData = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User instance = new User();
      instance.setId(Long.valueOf(i));
      sampleData.add(instance);
    }
    Page<User> page = new PageImpl<>(sampleData);
    when(userRepository.findAll(any(Pageable.class))).thenReturn(page);
    when(pagedResourcesAssembler.toResource(page,userAssembler))
      .thenReturn(new PagedResources(sampleData,null));

    Pageable pageable = new PageRequest(2,2);
    PagedResources result = testInstance.getAll(pageable,pagedResourcesAssembler);
    assertEquals(10,result.getContent().size());
    verify(userRepository, times(1)).findAll(any(Pageable.class));
    verify(pagedResourcesAssembler,times(1)).toResource(eq(page),eq(userAssembler));
    verifyNoMoreInteractions(userRepository);
    verifyNoMoreInteractions(userAssembler);
    verifyNoMoreInteractions(pagedResourcesAssembler);
  }

  @Test
  public void getOneShouldReturnResponseContainingTheDataOfOneUserAsJson() throws Exception {
    User instance = new User();
    instance.setId(Long.valueOf(1));
    UserResource testResource = new UserResource(instance);
    when(userRepository.findOne(Long.valueOf(1))).thenReturn(instance);
    when(userAssembler.toResource(instance)).thenReturn(testResource);
    ResponseEntity response = testInstance.getOne(Long.valueOf(1));
    assertEquals(200,response.getStatusCode().value());
    verify(userRepository, times(1)).findOne(Long.valueOf(1));
    verify(userAssembler, times(1)).toResource(instance);
  }
}