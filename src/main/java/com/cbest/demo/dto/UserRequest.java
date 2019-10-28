package com.cbest.demo.dto;

import com.cbest.demo.dto.inner.PageRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class UserRequest extends PageRequest {

    private Integer id;

    @NotBlank(message = "name can not be null")
    private String name;

    @NotNull(message = "age can not be null")
    private Integer age;
}
