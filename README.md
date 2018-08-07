# Activiti6 SpringBoot Demo

> 版本: v1.0.0 <br>
> 日期: 2018-07-06 <br>
> 编辑: Allen <br>



## 一、工作流文件

目录:/resources/processes
+ 测试例子：mymodel1.bpmn20.xml




### 


## 二、接口

### 1. 部署工作流规则文件
描述: 发布工作流文件BPMN <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /processDefinition/deploy<br>
参数说明: <br>


| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|file|file|.zip 扩展的BPMN 文件压缩包|可选必填|process_core_ent_credit.zip|
|bpmnName|string|bpmn文件名称 包含.png 文件|可选必填|myLeave.bpmn20|

请求示例:




返回值说明:


+ code 返回代码
+ msg 消息提示

返回数据样例:

    {
       "code": "1",
       "msg": "ok",
    }


### 2. 开始流程
描述: 发布BPMN文件后开启流程实例 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /processDefinition/startProcessInstance<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|instanceKey|string|BPMN文件实例名称|是|process_core_ent_credit|
|applyUserId|string|启动者ID流程发起人|是| 资产经理||
|params|object|表单参数|是|{"flowId":"2","param1":"内容1","param2":"内容2","param3":"内容3","param4":"内容4","param5":"内容5","applyUserId":"资产经理","assignee":"资产经理"}|
|+assignee|string|审核人|是| 资产经理|



请求示例:

    {
        "instanceKey": "process_core_ent_credit",
        "params": {
            "flowId": "2",
            "param1": "内容1",
            "param2": "内容2",
            "param3": "内容3",
            "param4": "内容4",
            "param5": "内容5",
            "applyUserId": "资产经理",
            "assignee": "资产经理"
        }
    }

返回值说明:


+ code 返回代码
+ msg 消息提示
+ processInstanceId 实例ID

返回数据样例:

    {
        "msg": "ok",
        "processInstanceId": "5001",
        "code": 1
    }





### 3. 流程定义明细列表
描述: 通过key获取流程定义明细列表 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /processDefinition/queryProcessInstance<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|instanceKey|string|BPMN文件实例名称|否|process_core_ent_credit|



请求示例:

    {
        "instanceKey": ""
    }


返回值说明:


+ code 返回代码
+ msg 消息提示
+ list 流程定义列表

返回数据样例:

    {
        "msg": "ok",
        "code": 1,
        "list": [
            {
                "id": "process_core_ent_credit:3:2508",
                "name": "XXXX",
                "description": "XXXX",
                "key": "process_core_ent_credit",
                "version": 3,
                "category": "http://www.activiti.org/processdef",
                "deploymentId": "2505",
                "resourceName": "processes/process_core_ent_credit.bpmn20.xml",
                "tenantId": "",
                "historyLevel": null,
                "diagramResourceName": null,
                "variables": null,
                "hasStartFormKey": false,
                "suspensionState": 1,
                "engineVersion": null,
                "identityLinksInitialized": false,
                "graphicalNotationDefined": true
            },
            {
                "id": "process_core_ent_project:1:5020",
                "name": "XXX",
                "description": null,
                "key": "process_core_ent_project",
                "version": 1,
                "category": "http://www.activiti.org/processdef",
                "deploymentId": "5017",
                "resourceName": "processes/process_core_ent_project.bpmn20.xml",
                "tenantId": "",
                "historyLevel": null,
                "diagramResourceName": null,
                "variables": null,
                "hasStartFormKey": false,
                "suspensionState": 1,
                "engineVersion": null,
                "identityLinksInitialized": false,
                "graphicalNotationDefined": true
            }
        ]
    }



### 4. 查询用户任务列表
描述: 查询用户任务列表 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /flowUser/queryTask<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|assignee|string|审核人|否|Allen|
|candidateUser|string|审核候选人|否|Allen|
|candidateGroup|string|审核候选组|否|风控部门组|

请求示例:

    {
        "assignee": "",
        "candidateUser": "deptLeader",
        "candidateGroup": "hrGroups"
    }


返回值说明:


+ code 返回代码
+ msg 消息提示
+ list 任务列表

返回数据样例:

    {
        "msg": "ok",
        "code": 1,
        "list": [
            {
                "id": "5013",
                "owner": null,
                "assigneeUpdatedCount": 0,
                "originalAssignee": null,
                "assignee": null,
                "parentTaskId": null,
                "name": "XXX",
                "localizedName": null,
                "description": null,
                "localizedDescription": null,
                "priority": 50,
                "createTime": "2018-07-06 03:04:47",
                "dueDate": null,
                "suspensionState": 1,
                "category": null,
                "executionId": "5010",
                "processInstanceId": "5001",
                "processDefinitionId": "process_core_ent_credit:3:2508",
                "taskDefinitionKey": "sid-1E9202D7-134D-44D0-A8C7-17C21D867102",
                "formKey": null,
                "eventName": null,
                "tenantId": "",
                "forcedUpdate": false,
                "claimTime": null,
                "variables": {
                    "param5": "内容5",
                    "assignee": "资产经理",
                    "param3": "内容3",
                    "flowId": "2",
                    "param4": "内容4",
                    "applyUserId": "资产经理",
                    "param1": "内容1",
                    "param2": "内容2"
                },
                "deleted": false,
                "canceled": false,
                "identityLinksInitialized": false
            }
        ]
    }
    
    
    
### 5. 查询待办任务
描述: 查询待办任务 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /flowUser/queryWaitTask<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|tmp|string|随便参数名|是|xxx|

请求示例:

    {"tmp":""}


返回值说明:


+ code 返回代码
+ msg 消息提示
+ list 任务列表

返回数据样例:

    {
        "msg": "ok",
        "code": 1,
        "list": [
            {
                "id": "5013",
                "owner": null,
                "assigneeUpdatedCount": 0,
                "originalAssignee": null,
                "assignee": null,
                "parentTaskId": null,
                "name": "XXX",
                "localizedName": null,
                "description": null,
                "localizedDescription": null,
                "priority": 50,
                "createTime": "2018-07-06 03:04:47",
                "dueDate": null,
                "suspensionState": 1,
                "category": null,
                "executionId": "5010",
                "processInstanceId": "5001",
                "processDefinitionId": "process_core_ent_credit:3:2508",
                "taskDefinitionKey": "sid-1E9202D7-134D-44D0-A8C7-17C21D867102",
                "formKey": null,
                "eventName": null,
                "tenantId": "",
                "forcedUpdate": false,
                "claimTime": null,
                "variables": null,
                "deleted": false,
                "canceled": false,
                "identityLinksInitialized": false
            }
        ]
    }
    
    
### 6. 完成任务
描述: 用户完成任务操作 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /flowUser/completeTask<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|taskId|string|任务ID|是|1212|
|assignee|string|审批人|否|1212|
|isReviewPass|int|是否通过审核 [0:未通过,1:通过]  默认值 1|否|1|
|returnStart|int|是否返回到开始节点 [0:返回上一个节点,1:返回到起始节点]  默认值 0|否|1|
|variables|map|表单参数|是|{"param1":"232","param2":"23"}|

请求示例:

    {
        "taskId": "5013",
        "assignee": "资产总经理",
        "isReviewPass": 1,
        "returnStart": 0,
        "params": {
            "param1": "内容1",
            "param2": "内容2",
            "param3": "内容3",
            "param4": "内容4",
            "param5": "内容5",
            "review": true,
            "reviewConteng": "没问题"
        }
    } 
    
    
返回值说明:


+ code 返回代码
+ msg 消息提示
+ isFinish 流程是否结束

返回数据样例:

    {
        "msg": "ok",
        "code": 1,
        "isFinish": false
    }    
    


### 7. 任务指派
描述: 任务指派 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /flowUser/claimTask<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|taskId|string|任务ID|是|1212|
|userId|string|被指派人用户ID|否|1212|

请求示例:

    {
        "taskId": "112522",
        "userId": "hr1"
    }

返回值说明:


+ code 返回代码
+ msg 消息提示
+ isFinish 流程是否结束

返回数据样例:

    {
        "msg": "ok",
        "code": 1
    }    



### 8. 查询审核历史
描述: 任务指派 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /flowUser/queryTaskHistory<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|processInstanceId|string|任务实例ID|是|1212|

请求示例:

    {
        "processInstanceId": "5"
    }

返回值说明:


+ code 返回代码
+ msg 消息提示
+ list 流程实例列表

返回数据样例:

    {
        "msg": "ok",
        "code": 1,
        "list": [
            {
                "executionId": "14",
                "name": "XXXX",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": "XX",
                "taskDefinitionKey": "sid-1E9202D7-134D-44D0-A8C7-17C21D867102",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": "2018-07-05 09:22:05",
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:21:50",
                "endTime": "2018-07-05 09:22:05",
                "durationInMillis": 15379,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "没问题",
                    "param5": "内容5",
                    "review": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "14",
                "name": "XX",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": "XX",
                "taskDefinitionKey": "sid-B26EAAEE-C2BE-4A9D-8FE4-7AF1A629ACFC",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:22:05",
                "endTime": "2018-07-05 09:22:44",
                "durationInMillis": 38517,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "没风险，同意",
                    "param5": "内容5",
                    "review": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "14",
                "name": "XX XX",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": "XX",
                "taskDefinitionKey": "sid-E39E699C-B70E-488F-82F1-718517ECF215",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": "2018-07-05 09:23:41",
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:22:44",
                "endTime": "2018-07-05 09:23:42",
                "durationInMillis": 57850,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "同意，没问题",
                    "param5": "内容5",
                    "review": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "14",
                "name": "法务经理审核",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": "法务经理",
                "taskDefinitionKey": "sid-D2260A87-C8DD-4837-BBB7-0DB2B9CC4D90",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:23:42",
                "endTime": "2018-07-05 09:24:00",
                "durationInMillis": 18366,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "同意，没问题",
                    "param5": "内容5",
                    "review": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "14",
                "name": "XX 理审核",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": "XXX",
                "taskDefinitionKey": "sid-D257D11D-C44B-4F83-9B55-AC2E1088785F",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": "2018-07-05 09:24:19",
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:24:00",
                "endTime": "2018-07-05 09:24:19",
                "durationInMillis": 19077,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "同意，没问题",
                    "param5": "内容5",
                    "review": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "14",
                "name": "XXXX",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": "秘书",
                "taskDefinitionKey": "sid-EF4E6C58-6FAA-4A8A-8B98-792B0F3B3BA7",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:24:19",
                "endTime": "2018-07-05 09:25:23",
                "durationInMillis": 63528,
                "deleteReason": null,
                "variables": {
                    "param5": "内容5",
                    "assigneeList": [
                        "zhangsan",
                        "lisi",
                        "wangwu",
                        "zhaoliu"
                    ],
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "105",
                "name": "委员投票",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": null,
                "taskDefinitionKey": "sid-DA97B90B-B68C-4F82-841E-9401F52A06C8",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:25:23",
                "endTime": "2018-07-05 09:26:43",
                "durationInMillis": 80225,
                "deleteReason": null,
                "variables": {
                    "param5": "内容5",
                    "multi_vote_pass": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "106",
                "name": "委员投票",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": null,
                "taskDefinitionKey": "sid-DA97B90B-B68C-4F82-841E-9401F52A06C8",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:25:23",
                "endTime": "2018-07-05 09:27:33",
                "durationInMillis": 130008,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "同意1",
                    "param5": "内容5",
                    "multi_vote_pass": true,
                    "review": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "107",
                "name": "委员投票",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": null,
                "taskDefinitionKey": "sid-DA97B90B-B68C-4F82-841E-9401F52A06C8",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:25:23",
                "endTime": "2018-07-05 09:29:17",
                "durationInMillis": 234197,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "不同意",
                    "param5": "内容5",
                    "multi_vote_pass": false,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "108",
                "name": "委员投票",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": null,
                "taskDefinitionKey": "sid-DA97B90B-B68C-4F82-841E-9401F52A06C8",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:25:23",
                "endTime": "2018-07-05 09:33:51",
                "durationInMillis": 508181,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "同意",
                    "param5": "内容5",
                    "multi_vote_pass": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            },
            {
                "executionId": "14",
                "name": "XXXX",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": null,
                "taskDefinitionKey": "sid-0BAC4ED4-8C81-4632-8209-5B9A02928D77",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:33:51",
                "endTime": "2018-07-05 09:34:33",
                "durationInMillis": 41353,
                "deleteReason": null,
                "variables": {
                    "multi_vote_count": 3,
                    "param1": "XXX"
                }
            },
            {
                "executionId": "14",
                "name": "主任委员签发",
                "localizedName": null,
                "parentTaskId": null,
                "description": null,
                "localizedDescription": null,
                "owner": null,
                "assignee": null,
                "taskDefinitionKey": "sid-55586A2F-4682-4C24-B6C4-7402F40459F9",
                "formKey": null,
                "priority": 50,
                "dueDate": null,
                "claimTime": null,
                "category": null,
                "tenantId": "",
                "processInstanceId": "5",
                "processDefinitionId": "process_core_ent_credit:1:4",
                "startTime": "2018-07-05 09:34:33",
                "endTime": "2018-07-05 09:35:30",
                "durationInMillis": 57257,
                "deleteReason": null,
                "variables": {
                    "reviewConteng": "同意2",
                    "param5": "内容5",
                    "review": true,
                    "param3": "内容3",
                    "param4": "内容4",
                    "param1": "内容1",
                    "param2": "内容2"
                }
            }
        ]
    } 

    
    
    
### 9. 展示流程图
描述: 流程中的节点状态展示 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /processDefinition/viewProcessInstanceImage<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|instanceKey|string|任务实例KEY|是|process_core_ent_project|
|processInstanceId|string|任务实例ID|是|5|

请求示例:

    /processDefinition/viewImage?instanceKey=process_core_ent_project&processInstanceId=5
    
    
### 10. 流程是否结束
描述: 流程是否结束 <br>
功能: <br>

API: <br>
方法: POST <br>
请求地址: /processDefinition/isFinishProcess<br>
参数说明: <br>

| 参数明|类型|说明|是否必填|示例|
|:--------:|:-----:|:-----:|:----:|:-----|
|processInstanceId|string|任务实例ID|是|5|

请求示例:

    {
        "processInstanceId": "5"
    }

返回值说明:


+ code 返回代码
+ msg 消息提示
+ isFinish 流程是否结束

返回数据样例:

    {
        "msg": "ok",
        "code": 1,
        "isFinish":true
    }    
