package org.lecture.unit.authority.controller;

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

import org.lecture.assembler.AuthorityAssembler;
import org.lecture.controller.AuthorityController;
import org.lecture.repository.AuthorityRepository;
import org.lecture.resource.AuthorityResource;
import org.lecture.model.Authority;
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
* Unit test for Authority controllers.
* @author Rene Richter
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AuthorityControllerUnitTestConfig.class})
public class AuthorityControllerUnitTest {

  @Autowired
  private AuthorityRepository authorityRepository;

  @Autowired
  private AuthorityAssembler authorityAssembler;

  @Autowired
  private PagedResourcesAssembler pagedResourcesAssembler;

  @Autowired
  private AuthorityController testInstance;


  /**
   * sets up the test.
   */
  @Before
  public void setUp() {
    reset(authorityRepository,authorityAssembler,pagedResourcesAssembler);
  }


  @Test
  public void getAllShouldReturnAPageOfAuthority() throws Exception {

    List<Authority> sampleData = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Authority instance = new Authority();
      instance.setId(Long.valueOf(i));
      sampleData.add(instance);
    }
    Page<Authority> page = new PageImpl<>(sampleData);
    when(authorityRepository.findAll(any(Pageable.class))).thenReturn(page);
    when(pagedResourcesAssembler.toResource(page,authorityAssembler))
      .thenReturn(new PagedResources(sampleData,null));

    Pageable pageable = new PageRequest(2,2);
    PagedResources result = testInstance.getAll(pageable,pagedResourcesAssembler);
    assertEquals(10,result.getContent().size());
    verify(authorityRepository, times(1)).findAll(any(Pageable.class));
    verify(pagedResourcesAssembler,times(1)).toResource(eq(page),eq(authorityAssembler));
    verifyNoMoreInteractions(authorityRepository);
    verifyNoMoreInteractions(authorityAssembler);
    verifyNoMoreInteractions(pagedResourcesAssembler);
  }

  @Test
  public void getOneShouldReturnResponseContainingTheDataOfOneAuthorityAsJson() throws Exception {
    Authority instance = new Authority();
    instance.setId(Long.valueOf(1));
    AuthorityResource testResource = new AuthorityResource(instance);
    when(authorityRepository.findOne(Long.valueOf(1))).thenReturn(instance);
    when(authorityAssembler.toResource(instance)).thenReturn(testResource);
    ResponseEntity response = testInstance.getOne(Long.valueOf(1));
    assertEquals(200,response.getStatusCode().value());
    verify(authorityRepository, times(1)).findOne(Long.valueOf(1));
    verify(authorityAssembler, times(1)).toResource(instance);
  }
}