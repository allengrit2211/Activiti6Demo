package com.activiti6.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: Allen
 * @Date: 2018/7/30 14:51
 * @Description:
 */
@Data
@ApiModel
public class GroupReq {

    @ApiModelProperty(value = "用户组ID", notes = "用户组ID", required = true, dataType = "String")
    @NotBlank(message = "用户组ID不能为空")
    private String id;

    @ApiModelProperty(value = "用户组名称", notes = "用户组名称", required = true, dataType = "String")
    private String name;


}
