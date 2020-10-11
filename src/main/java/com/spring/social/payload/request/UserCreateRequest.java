package com.spring.social.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "User Create Model")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {

    @ApiModelProperty(position = 0,required = true)
    @NotBlank
    @Size(max = 100)
    String username;

    @ApiModelProperty(position = 1,required = true)
    @Size(max = 120)
    String password;


    @ApiModelProperty(position = 2,required = true)
    @Size(max = 50)
    @Email
    String email;
}