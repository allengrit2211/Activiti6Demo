package com.activiti6.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: Allen
 * @Date: 2018/7/27 13:41
 * @Description: 指派任务请求对象
 */
@Data
@ApiModel
public class ClaimTaskReq {

    @ApiModelProperty(value = "任务ID", notes = "任务ID", required = true, dataType = "String")
    @NotBlank(message = "任务ID不能为空")
    private String taskId;


    @ApiModelProperty(value = "审批人", notes = "审批人", required = true, dataType = "String")
    @NotBlank(message = "审批人不能为空")
    private String assignee;

}
