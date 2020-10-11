package com.spring.social.exception.oauth2;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
public class UnauthorizedRedirectURIException extends RuntimeException {

}