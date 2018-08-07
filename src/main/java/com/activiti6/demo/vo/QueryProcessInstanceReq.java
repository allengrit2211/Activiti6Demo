package com.activiti6.demo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author Allen
 * @Date: 2018/7/27 10:10
 * @Description: 开始流程实例请求类
 */
@Data
@ApiModel
public class QueryProcessInstanceReq  {

    @ApiModelProperty(value = "实例名称", notes = "实例名称", dataType = "String")
    @NotBlank(message = "实例名称不能为空")
    private String instanceKey;

}
