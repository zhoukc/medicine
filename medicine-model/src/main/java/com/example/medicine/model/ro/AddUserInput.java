package com.example.medicine.model.ro;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AddUserInput {

    @NotBlank(message = "姓名不能为空")
    private String name;
    @Max(value = 150, message = "年龄不能超过150")
    private int age;
    @Pattern(regexp = "[M|F]", message = "性别只能为M或者F")
    private String sex;

}
