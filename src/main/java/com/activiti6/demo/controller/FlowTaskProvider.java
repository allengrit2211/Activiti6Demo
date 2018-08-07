package com.activiti6.demo.controller;

import com.activiti6.demo.service.ActivitiService;
import com.activiti6.demo.util.R;
import com.activiti6.demo.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @Date: 2018/6/25 13:52
 * @Description: 任务相关
 */
@RestController
@Slf4j
@Api(value = "任务控制类")
@RequestMapping({"/flowTask"})
public class FlowTaskProvider{


    @Autowired
    ActivitiService activitiService;

    /***
     * 查询待办任务
     * @param queryWaitTaskReq
     * @return
     */

    @PostMapping(value = "/queryWaitTask")
    @ApiOperation(value = "查询待办任务", notes = "查询待办任务")
    public R queryWaitTask(@RequestBody @ApiParam(value = "查询任务请求对象") @Valid QueryWaitTaskReq queryWaitTaskReq) {
        List list = activitiService.queryWaitTask(queryWaitTaskReq.getFirstResult(), queryWaitTaskReq.getMaxResults());
        return R.ok().put("list", list);
    }


    /***
     * 查询用户任务列表
     * @return
     */

    @PostMapping(value = "/queryTask")
    @ApiOperation(value = "查询任务", notes = "查询任务")
    public R queryTask(@RequestBody @ApiParam(value = "查询任务请求对象") @Valid QueryTaskReq queryTaskReq) {
        try {
            List list = activitiService.queryTask(queryTaskReq.getAssignee(), queryTaskReq.getCandidateUser(), queryTaskReq.getCandidateGroup(), queryTaskReq.getFirstResult(), queryTaskReq.getMaxResults());
            return R.ok().put("list", list);
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }


    /***
     * 审核任务
     * @param completeTaskReq
     * @return
     */
    @PostMapping(value = "/completeTask")
    @ApiOperation(value = "审核任务", notes = "审核任务")
    public R completeTask(@RequestBody @ApiParam(value = "审核任务请求对象") @Valid CompleteTaskReq completeTaskReq) {
        try {
            if (StringUtils.isBlank(completeTaskReq.getAssignee())) {//审核人不能为空
                return R.error(-1, "审核人不能为空");
            }

            Map<String, Object> param = new HashMap<>();
            param.put("isFinish", false);//流程是否完成

            if (completeTaskReq.getIsReviewPass() == 1) {
                activitiService.completeTask(completeTaskReq.getTaskId(), completeTaskReq.getAssignee(), completeTaskReq.getVariables(), param);
            }

            if (completeTaskReq.getIsReviewPass() == 0) {//驳回
                activitiService.rejectTask(completeTaskReq.getTaskId(), completeTaskReq.getAssignee(), completeTaskReq.getReturnStart() == 1);
            }

            //流程是否结束
            return R.ok().put("isFinish", param.get("isFinish"));
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }

    /****
     * 删除任务
     * @param deleteTaskReq
     * @return
     */

    @PostMapping(value = "/deleteTask")
    @ApiOperation(value = "删除任务", notes = "删除任务")
    public R deleteTask(@RequestBody @ApiParam(value = "删除任务求对象") @Valid DeleteTaskReq deleteTaskReq) {
        try {
            activitiService.deleteTask(deleteTaskReq.getTaskId());
            return R.ok();
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }

    /***
     * 任务指派
     * @param claimTaskReq
     * @return
     */
    @ApiOperation(value = "任务指派", notes = "任务指派")
    @PostMapping(value = "/claimTask")
    public R claimTask(@RequestBody @ApiParam(value = "指派任务求对象") @Valid ClaimTaskReq claimTaskReq) {
        try {
            activitiService.claimTask(claimTaskReq.getTaskId(), claimTaskReq.getAssignee());
            return R.ok();
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }


    /****
     * 查询任务历史记录
     * @param queryTaskHistoryReq
     * @return
     */
    @ApiOperation(value = "查询任务历史记录", notes = "查询任务历史记录")
    @PostMapping(value = "/queryTaskHistory")
    public R queryTaskHistory(@RequestBody @ApiParam(value = "查询任务历史记录对象") QueryTaskHistoryReq queryTaskHistoryReq) {
        try {
            List list = activitiService.queryTaskHistory(queryTaskHistoryReq.getProcessInstanceId());
            return R.ok().put("list", list);
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }

}
