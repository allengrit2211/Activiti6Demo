package com.activiti6.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: Allen
 * @Date: 2018/7/27 11:22
 * @Description:
 */
@Data
@ApiModel
public class QueryWaitTaskReq{

    @ApiModelProperty(value = "开始记录数", notes = "开始记录数", dataType = "int")
    private int firstResult = 0;

    @ApiModelProperty(value = "每页记录数", notes = "每页记录数", dataType = "int" )
    private int maxResults = 100;

}
