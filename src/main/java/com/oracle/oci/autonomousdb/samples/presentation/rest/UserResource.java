/*
 *
 *  *
 *  *  * Copyright 2019-2020 the original author or authors.
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package com.oracle.oci.autonomousdb.samples.presentation.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.oracle.oci.autonomousdb.samples.common.exceptions.NotFoundException;
import com.oracle.oci.autonomousdb.samples.model.UserEntity;
import com.oracle.oci.autonomousdb.samples.presentation.dto.UserDTO;
import com.oracle.oci.autonomousdb.samples.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bnasslashen
 */
@RestController
@RequestMapping("/users")
public class UserResource {

	private final UserRepository usersRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

	public UserResource(UserRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@GetMapping
	public List<UserDTO> getUsers() {
		LOGGER.debug("get all Users");
		List<UserEntity> userEntities = usersRepository.findAll();
		List<UserDTO> userDTOList = new ArrayList<>();
		userEntities.forEach(userEntity -> {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(userEntity, userDTO);
			userDTOList.add(userDTO);
		});
		return userDTOList;
	}

	@GetMapping("/{id}")
	public UserDTO getUser(@PathVariable String id) {
		LOGGER.debug("getUser with id: {} ", id);
		UserEntity usersEntity = usersRepository.findById(UUID.fromString(id)).orElseThrow(() -> new NotFoundException("Customer with id: " + id));
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(usersEntity, userDTO);
		return userDTO;
	}

	@PostMapping
	public UserDTO createUser(@RequestBody UserDTO userDTO) {
		LOGGER.debug("createUser: {} ", userDTO);
		UserEntity usersEntity = new UserEntity();
		BeanUtils.copyProperties(userDTO, usersEntity);
		usersRepository.save(usersEntity);
		BeanUtils.copyProperties(usersEntity, userDTO);
		return userDTO;
	}

}
