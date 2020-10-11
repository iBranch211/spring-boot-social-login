package com.spring.social.payload.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ApiModel(value = "Product Create Model")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequest {

    @ApiModelProperty(position = 0,required = true)
    @NotBlank
    @Size(max = 100)
    String name;

    @ApiModelProperty(position = 1,required = true)
    @Size(max = 120)
    String price;
}