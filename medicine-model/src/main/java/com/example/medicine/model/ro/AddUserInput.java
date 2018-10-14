package com.example.medicine.model.ro;

import com.example.medicine.model.expands.ApiDescription;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class AddUserInput {

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty("姓名")
    private String name;
    @Max(value = 150, message = "年龄不能超过150")
    @ApiModelProperty("年龄")
    private int age;
    @Pattern(regexp = "[M|F]", message = "性别只能为M或者F")
    private String sex;

}
