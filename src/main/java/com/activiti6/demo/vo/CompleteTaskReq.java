package com.activiti6.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @Auther: Allen
 * @Date: 2018/7/27 13:25
 * @Description:
 */

@Data
@ApiModel
public class CompleteTaskReq {


    @ApiModelProperty(value = "任务ID", notes = "任务ID", required = true, dataType = "String")
    @NotBlank(message = "任务ID不能为空")
    private String taskId;

    @ApiModelProperty(value = "审批人", notes = "审批人", required = true, dataType = "String")
    @NotBlank(message = "审批人不能为空")
    private String assignee;

    /***
     * 是否通过审核 0 未通过 1 通过  默认值 1 通过
     */
    @ApiModelProperty(value = "是否通过审核", notes = "是否通过审核", required = true, dataType = "String")
    private int isReviewPass = 1;

    /***
     * 是否返回到开始节点 0 否 1 是 默认0  回退到上一个节点
     */
    @ApiModelProperty(value = "是否返回到开始节点", notes = "是否返回到开始节点", required = true, dataType = "String")
    private int returnStart = 0;

    @ApiModelProperty(value = "自定义参数", notes = "自定义参数", required = true, dataType = "Map")
    private Map<String, Object> variables;


}
