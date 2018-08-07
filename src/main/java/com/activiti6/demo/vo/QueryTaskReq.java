package com.activiti6.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: Allen
 * @Date: 2018/7/27 10:53
 * @Description: 查询待办请求对象
 */
@Data
@ApiModel
public class QueryTaskReq {


    @ApiModelProperty(value = "开始记录数", notes = "开始记录数", dataType = "int")
    private int firstResult = 0;

    @ApiModelProperty(value = "每页记录数", notes = "每页记录数", dataType = "int" )
    private int maxResults = 100;

    @ApiModelProperty(value = "处理人", notes = "处理人", dataType = "String" )
    private String assignee;

    @ApiModelProperty(value = "候选用户", notes = "候选用户", dataType = "String" )
    private String candidateUser;

    @ApiModelProperty(value = "候选用户组", notes = "候选用户组", dataType = "String" )
    private String candidateGroup;

}
