package com.activiti6.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: Allen
 * @Date: 2018/7/30 15:26
 * @Description:
 */
@Data
@ApiModel
public class UserReq {

    @ApiModelProperty(value = "用户ID", notes = "用户ID", required = true, dataType = "String")
    @NotBlank(message = "用户ID不能为空")
    private String id;

    @ApiModelProperty(value = "Email", notes = "Email", required = true, dataType = "String")
    private String email;

    @ApiModelProperty(value = "名字", notes = "名字", required = true, dataType = "String")
    private String firstName;

    @ApiModelProperty(value = "姓", notes = "姓", required = true, dataType = "String")
    private String lastName;

    @ApiModelProperty(value = "密码", notes = "密码", required = true, dataType = "String")
    private String password;

}
