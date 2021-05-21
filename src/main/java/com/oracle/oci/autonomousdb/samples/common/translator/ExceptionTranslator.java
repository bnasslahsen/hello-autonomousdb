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

package com.oracle.oci.autonomousdb.samples.common.translator;

/**
 * @author bnasslashen
 */

import com.oracle.oci.autonomousdb.samples.common.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionTranslator {

	private static final Logger LOG = LoggerFactory.getLogger(ExceptionTranslator.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageDetail> handleRunTimeException(Exception e) {
		return error(HttpStatus.INTERNAL_SERVER_ERROR, e);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<MessageDetail> handleNotFoundException(NotFoundException e) {
		return error(HttpStatus.NOT_FOUND, e);
	}

	private ResponseEntity<MessageDetail> error(HttpStatus status, Exception e) {
		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status))
			LOG.error("Exception ", e);
		else
			LOG.warn("Exception : {} ", e.getMessage());
		return ResponseEntity.status(status).body(new MessageDetail(e.getMessage()));
	}
}