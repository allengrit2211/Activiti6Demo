package com.activiti6.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: Allen
 * @Date: 2018/7/30 15:41
 * @Description: 用户与组关联请求对象
 */
@Data
@ApiModel
public class MembershipReq{

    @ApiModelProperty(value = "用户ID", notes = "用户ID", required = true, dataType = "String")
    @NotBlank(message = "用户ID不能为空")
    private String userId;

    @ApiModelProperty(value = "用户组ID", notes = "用户组ID", required = true, dataType = "String")
    @NotBlank(message = "用户组ID不能为空")
    private String groupId;

}
