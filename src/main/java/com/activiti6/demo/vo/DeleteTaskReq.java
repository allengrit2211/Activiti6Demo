package com.activiti6.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Auther: Allen
 * @Date: 2018/7/27 13:38
 * @Description:
 */
@Data
@ApiModel
public class DeleteTaskReq {

    @ApiModelProperty(value = "任务ID", notes = "任务ID", required = true, dataType = "String")
    @NotBlank(message = "任务ID不能为空")
    private String taskId;

}
