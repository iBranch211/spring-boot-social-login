package com.spring.social.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Error: Email is already in use!")
public class EmailAlreadyExistsException extends RuntimeException {

}