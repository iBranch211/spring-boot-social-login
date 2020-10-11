package com.spring.social.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;

@ApiModel(value = "Login Model")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {

	@ApiModelProperty(position = 0,required = true)
	@NotBlank
	String username;

	@ApiModelProperty(position = 1,required = true)
	@NotBlank
	String password;
}