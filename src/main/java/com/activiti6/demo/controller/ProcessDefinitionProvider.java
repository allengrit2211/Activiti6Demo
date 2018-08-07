package com.activiti6.demo.controller;

import com.activiti6.demo.service.ActivitiService;
import com.activiti6.demo.util.R;
import com.activiti6.demo.vo.IsFinishProcessReq;
import com.activiti6.demo.vo.QueryProcessInstanceReq;
import com.activiti6.demo.vo.StartProcessInstanceReq;
import com.activiti6.demo.vo.ViewProcessInstanceImageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Allen
 * @Date: 2018/6/25 13:52
 * @Description: 流程定义相关
 */
@RestController
@Slf4j
@Api(value = "流程定义控制类")
@RequestMapping({"/processDefinition"})
public class ProcessDefinitionProvider  {


    @Autowired
    ProcessEngine processEngine;

    @Autowired
    ActivitiService activitiService;

    @Autowired
    HistoryService historyService;

    @Autowired
    RepositoryService repositoryService;


    /***
     * 部署工作流规则文件
     * @param file BPMN 流程文件zip压缩包
     * @param bpmnName bpmn文件名称 包含.png 文件
     * @return
     */
    @PostMapping(value = "/deploy")
    @ApiOperation(value = "部署工作流规则文件", notes = "部署工作流规则文件")
    public R deploy(@ApiParam(value = "BPMN 流程文件zip压缩包") @RequestParam(value = "file", required = false) MultipartFile file,
                    @ApiParam(value = "bpmn文件名称 包含.png 文件") @RequestParam(value = "bpmnName", required = false) String bpmnName) {
        try {

            if (file == null && StringUtils.isBlank(bpmnName)) {
                return R.error(-1, "Please select a file to upload or input BPMN file name.");
            }


            if (file != null && !file.isEmpty()) {//发布zip 文件

                String fileName = file.getOriginalFilename();
                if (StringUtils.isBlank(fileName))
                    return R.error(-2, "bpmn 文件名称不能为空");


                InputStream fileInputStream = file.getInputStream();

                activitiService.deploy(fileInputStream, fileName);
            } else {
                activitiService.deploy(bpmnName);
            }

            return R.ok();
        } catch (Exception e) {
            return R.error(-100, "error:" + e.getMessage());
        }
    }

    /***
     * 开始流程
     */
    @PostMapping(value = "/startProcessInstance")
    @ApiOperation(value = "开始流程", notes = "开始流程")
    public R startProcessInstance(@RequestBody @Validated @ApiParam(value = "开始流程请求对象") @Valid StartProcessInstanceReq startProcessInstanceReq) {
        try {
            Map<String, Object> variables = startProcessInstanceReq.getVariables();//流程配置参数
            variables.put("applyUserId", startProcessInstanceReq.getApplyUserId());//流程发起人
            ProcessInstance processInstance = activitiService.startProcessInstance(startProcessInstanceReq.getInstanceKey(), variables);
            log.debug("processInstance:" + processInstance.getProcessDefinitionId());
            return R.ok().put("processInstanceId", processInstance.getId());
        } catch (Exception e) {
            return R.error(-1, e.getMessage());
        }
    }

    /****
     * 通过key获取流程定义明细列表
     * @return
     */
    @PostMapping(value = "/queryProcessInstance")
    @ApiOperation(value = "通过key获取流程定义明细列表", notes = "通过key获取流程定义明细列表")
    public R processDefinitionQuery(@RequestBody @ApiParam(value = "获取流程定义明细请求对象") @Valid QueryProcessInstanceReq queryProcessInstanceReq) {
        try {
            List<ProcessDefinition> list = activitiService.processDefinitionQuery(queryProcessInstanceReq.getInstanceKey());
            return R.ok().put("list", list);
        } catch (Exception e) {
            return R.error(-1, "error:" + e.getMessage());
        }
    }


    @PostMapping(value = "/isFinishProcess")
    @ApiOperation(value = "流程是否结束", notes = "流程是否结束")
    public R isFinishProcess(@RequestBody @ApiParam(value = "流程是否结束请求对象") @Valid IsFinishProcessReq isFinishProcessReq) {
        boolean flag = activitiService.isFinishProcess(isFinishProcessReq.getProcessInstanceId());
        return R.ok().put("isFinish", flag);
    }

    /****
     * 显示流程实例处理状态图片
     * @param request
     * @param response
     * @param viewProcessInstanceImageReq
     * @throws IOException
     */
    @PostMapping(value = "/viewProcessInstanceImage")
    @ApiOperation(value = "显示流程实例处理状态图片", notes = "显示流程实例处理状态图片")
    public void viewProcessInstanceImage(HttpServletRequest request, HttpServletResponse response, @RequestBody @ApiParam(value = "流程实例处理状态请求对象") @Valid ViewProcessInstanceImageReq viewProcessInstanceImageReq) {
        //logger.info("[开始]-获取流程图图像");
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(viewProcessInstanceImageReq.getProcessInstanceId()).singleResult();

            if (historicProcessInstance == null) {
                //throw new BusinessException("获取流程实例ID[" + pProcessInstanceId + "]对应的历史流程实例失败！");
            } else {
                // 获取流程定义
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(viewProcessInstanceImageReq.getProcessInstanceId()).orderByHistoricActivityInstanceId().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<String>();
                int index = 1;
                //logger.info("获取已经执行的节点ID");
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());

                    //logger.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
                    index++;
                }

                BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());


                // 获取流程走过的线 (getHighLightedFlows是下面的方法)
                List<String> flowIds = getHighLightedFlows(bpmnModel, processDefinition, historicActivityInstanceList);


                // 获取流程图图像字符流
                ProcessDiagramGenerator pec = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
                //配置字体
                InputStream imageStream = pec.generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds, "宋体", "微软雅黑", "黑体", null, 2.0);

                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
            //logger.info("[完成]-获取流程图图像");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //logger.error("【异常】-获取流程图失败！" + e.getMessage());
            //throw new BusinessException("获取流程图失败！" + e.getMessage());
        }
    }


    /****
     *
     * @param bpmnModel
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    public List<String> getHighLightedFlows(BpmnModel bpmnModel, ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //24小时制
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId

        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 对历史流程节点进行遍历
            // 得到节点定义的详细信息
            FlowNode activityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(i).getActivityId());


            List<FlowNode> sameStartTimeNodes = new ArrayList<FlowNode>();// 用以保存后续开始时间相同的节点
            FlowNode sameActivityImpl1 = null;

            HistoricActivityInstance activityImpl_ = historicActivityInstances.get(i);// 第一个节点
            HistoricActivityInstance activityImp2_;

            for (int k = i + 1; k <= historicActivityInstances.size() - 1; k++) {
                activityImp2_ = historicActivityInstances.get(k);// 后续第1个节点

                if (activityImpl_.getActivityType().equals("userTask") && activityImp2_.getActivityType().equals("userTask") &&
                        df.format(activityImpl_.getStartTime()).equals(df.format(activityImp2_.getStartTime()))) //都是usertask，且主节点与后续节点的开始时间相同，说明不是真实的后继节点
                {

                } else {
                    sameActivityImpl1 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstances.get(k).getActivityId());//找到紧跟在后面的一个节点
                    break;
                }

            }
            sameStartTimeNodes.add(sameActivityImpl1); // 将后面第一个节点放在时间相同节点的集合里
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后续第一个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后续第二个节点

                if (df.format(activityImpl1.getStartTime()).equals(df.format(activityImpl2.getStartTime()))) {// 如果第一个节点和第二个节点开始时间相同保存
                    FlowNode sameActivityImpl2 = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循环
                    break;
                }
            }
            List<SequenceFlow> pvmTransitions = activityImpl.getOutgoingFlows(); // 取出节点的所有出去的线

            for (SequenceFlow pvmTransition : pvmTransitions) {// 对所有的线进行遍历
                FlowNode pvmActivityImpl = (FlowNode) bpmnModel.getMainProcess().getFlowElement(pvmTransition.getTargetRef());// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }

        }
        return highFlows;

    }

}
