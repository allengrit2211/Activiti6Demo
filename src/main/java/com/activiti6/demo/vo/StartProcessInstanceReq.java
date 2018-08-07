package com.activiti6.demo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author Allen
 * @Date: 2018/7/27 10:10
 * @Description: 开始流程实例请求类
 */
@Data
@ApiModel
public class StartProcessInstanceReq {


    @ApiModelProperty(value = "实例名称", notes = "实例名称", required = true, dataType = "String")
    @NotBlank(message = "实例名称不能为空")
    private String instanceKey;


    @ApiModelProperty(value = "流程发起人", notes = "流程发起人", required = true, dataType = "String")
    @NotBlank(message = "流程发起人不能为空")
    private String applyUserId;


    @ApiModelProperty(value = "自定义参数", notes = "自定义参数", required = true, dataType = "Map")
    private Map<String, Object> variables;

}
