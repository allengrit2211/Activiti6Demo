package com.activiti6.demo.service.impl;

import com.activiti6.demo.service.ActivitiService;
import com.activiti6.demo.vo.HistoricTaskInstanceVo;
import com.activiti6.demo.vo.ProcessDefinitionVo;
import com.activiti6.demo.vo.TaskVo;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;


@Service
@Slf4j
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    ProcessEngine processEngine;

    @Autowired
    RepositoryService repositoryService;


    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    HistoryService historyService;

    @Autowired
    IdentityService identityService;

    @Autowired
    ManagementService managementService;


    /**
     * 发布规则文件
     *
     * @param bpmnName
     */
    public void deploy(String bpmnName) {

        String bpmn = "processes/" + bpmnName + ".xml";
        String png = "processes/" + bpmnName + ".png";


        log.debug(String.format("xml:%s,png", bpmn, png));
        repositoryService.createDeployment()//创建一个部署对象
                .name(bpmnName)//添加部署的名称
                .addInputStream(bpmn, this.getClass().getClassLoader().getResourceAsStream(bpmn))
//                .addInputStream(png, this.getClass().getClassLoader().getResourceAsStream(png))
                .deploy();//完成部署
    }


    /**
     * 发布规则文件
     *
     * @param fileName
     */
    public void deploy(InputStream fileInputStream, String fileName) {

        ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
        //使用deploy方法发布流程
        repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name(fileName)
                .deploy();
    }


    @Override
    public ProcessInstance startProcessInstance(String instanceKey, Map variables) {
        /**
         * 启动请假单流程  并获取流程实例
         * 因为该请假单流程可以会启动多个所以每启动一个请假单流程都会在数据库中插入一条新版本的流程数据
         * 通过key启动的流程就是当前key下最新版本的流程
         *
         */
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(instanceKey, variables);
        log.debug(String.format("id:%s,activitiId:%s", processInstance.getId(), processInstance.getActivityId()));
        return processInstance;
    }

    @Override
    public List processDefinitionQuery(String instanceKey) {

        //创建查询对象
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();

        ProcessDefinitionQuery processDefinitionQuery = query.latestVersion();
        if (StringUtils.isBlank(instanceKey)) {
            processDefinitionQuery.list();
        } else {
            query.processDefinitionKey(instanceKey);
        }

        //添加查询条件
        // .processDefinitionName("My process")//通过name获取
        // .orderByProcessDefinitionId()//根据ID排序
        //执行查询获取流程定义明细
        List<ProcessDefinition> pds = query.list();
        log.debug("queryProcdef query list:" + pds);
        if (pds != null && pds.size() > 0) {
            for (ProcessDefinition pd : pds) {
                log.debug("ID:" + pd.getId() + ",NAME:" + pd.getName() + ",KEY:" + pd.getKey() + ",VERSION:" + pd.getVersion() + ",RESOURCE_NAME:" + pd.getResourceName() + ",DGRM_RESOURCE_NAME:" + pd.getDiagramResourceName());
            }
        }


        return listToBeanVo(pds, ProcessDefinitionVo.class);
    }

    @Override
    public List queryTask(String assignee, String candidateUser, String candidateGroup, int firstResult, int maxResults) {

        //获取任务服务对象
        //根据接受人获取该用户的任务

        TaskQuery taskQuery = taskService.createTaskQuery();

        if (!StringUtils.isBlank(assignee)) {
            taskQuery.taskAssignee(assignee);
        }

        if (!StringUtils.isBlank(candidateUser)) {
            taskQuery.taskCandidateUser(candidateUser);
        }
        if (!StringUtils.isBlank(candidateGroup)) {
            taskQuery.taskCandidateGroup(candidateGroup);
        }

        List<Task> tasks = taskQuery.listPage(firstResult, maxResults);


        List<TaskVo> list1 = null;
        if (tasks != null && tasks.size() > 0) {

            list1 = listToBeanVo(tasks, TaskVo.class, "variables");

            for (TaskVo task : list1) {

                Map<String, Object> variables = taskService.getVariables(task.getId());
                task.setVariables(variables);

                log.debug("ID:" + task.getId() + ",姓名:" + task.getName() + ",接收人:" + task.getAssignee() + ",开始时间:" + task.getCreateTime());
            }
        }


        return list1;
    }


    @Override
    public Map<String, Object> queryVariables(String taskId) {
        return taskService.getVariables(taskId);
    }

    @Override
    public List queryTaskHistory(String processInstanceId) {

        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                .processInstanceId(processInstanceId)//
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();


        List<HistoricTaskInstanceVo> list1 = null;
        if (list != null && list.size() > 0) {
            list1 = new ArrayList<>();
            for (HistoricTaskInstance hti : list) {
                log.debug(hti.getId() + "    " + hti.getName() + "    " + hti.getProcessInstanceId() + "   " + hti.getStartTime() + "   " + hti.getEndTime() + "   " + hti.getDurationInMillis());
                log.debug("################################");

                HistoricTaskInstanceVo historicTaskInstanceVo = objToBeanVo(hti, HistoricTaskInstanceVo.class);
                if (historicTaskInstanceVo != null) {

                    List<HistoricVariableInstance> list2 = historyService.createHistoricVariableInstanceQuery().taskId(hti.getId()).list();
                    if (list2 != null && list2.size() > 0) {

                        Map<String, Object> variables = new HashMap<>();
                        for (HistoricVariableInstance historicVariableInstance : list2) {
                            variables.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
                        }
                        historicTaskInstanceVo.setVariables(variables);
                    }


                    list1.add(historicTaskInstanceVo);
                }

            }
        }

        return list1;
    }

    @Override
    public void completeTask(String taskId, String assignee, Map<String, Object> variables, Map<String, Object> param) {

        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        //完成请假申请任务
        taskService.setVariablesLocal(taskId, variables);
        if (!StringUtils.isBlank(assignee)) {
            taskService.setAssignee(taskId, assignee);
        }
        taskService.complete(taskId, variables);

        if (task != null) {
            param.put("isFinish", isFinishProcess(task.getProcessInstanceId()));
        }

    }

    @Override
    public void claimTask(String taskId, String assignee) {
        taskService.claim(taskId, assignee);
    }

    @Override
    public void deleteTask(String taskId) {
        taskService.deleteTask(taskId);
    }


    @Override
    public boolean isFinishProcess(String processInstanceId) {

        /**判断流程是否结束，查询正在执行的执行对象表*/
        ProcessInstance rpi = processEngine.getRuntimeService()//
                .createProcessInstanceQuery()//创建流程实例查询对象
                .processInstanceId(processInstanceId)
                .singleResult();

        return rpi == null;
    }

    @Override
    public List queryWaitTask(int firstResult, int maxResults) {
        List<Task> list = taskService.createTaskQuery().listPage(firstResult, maxResults);
        return listToBeanVo(list, TaskVo.class, "variables");
    }


    @Override
    public void rejectTask(String taskId, String assignee, boolean returnStart) {
        jump(this, taskId, assignee, returnStart);
    }

    //跳转方法
    public void jump(ActivitiServiceImpl activitiService, String taskId, String assignee, boolean returnStart) {
        //当前任务
        Task currentTask = activitiService.taskService.createTaskQuery().taskId(taskId).singleResult();
        //获取流程定义
//        Process process = activitiService.repositoryService.getBpmnModel(currentTask.getProcessDefinitionId()).getMainProcess();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(currentTask.getProcessDefinitionId());


        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery().processInstanceId(currentTask.getProcessInstanceId()).activityType("userTask").finished().orderByHistoricActivityInstanceEndTime().asc().list();
        if (list == null || list.size() == 0) {
            throw new ActivitiException("操作历史流程不存在");
        }

        //获取目标节点定义
        FlowNode targetNode = null;

        if (returnStart) {//驳回到发起点

            targetNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(list.get(0).getActivityId());
        } else {//驳回到上一个节点

            FlowNode currNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentTask.getTaskDefinitionKey());

            for (int i = 0; i < list.size(); i++) {//倒序审核任务列表，最后一个不与当前节点相同的节点设置为目标节点
                FlowNode lastNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(list.get(i).getActivityId());
                if (list.size() > 0 && currNode.getId().equals(lastNode.getId())) {
                    targetNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(list.get(i - 1).getActivityId());
                    break;
                }
            }

            if (targetNode == null && list.size() > 0) {
                targetNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(list.get(list.size() - 1).getActivityId());
            }


//            Map<String, Object> flowElementMap = new TreeMap<>();
//            Collection<FlowElement> flowElements = bpmnModel.getMainProcess().getFlowElements();
//            for (FlowElement flowElement : flowElements) {
//
//                flowElementMap.put(flowElement.getId(), flowElement);
//            }
//
//
//
//            targetNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(tmplist.get(tmplist.size() - 1).getActivityId());

        }

        if (targetNode == null) {
            throw new ActivitiException("开始节点不存在");
        }


        //删除当前运行任务
        String executionEntityId = activitiService.managementService.executeCommand(activitiService.new DeleteTaskCmd(currentTask.getId()));
        //流程执行到来源节点
        activitiService.managementService.executeCommand(activitiService.new SetFLowNodeAndGoCmd(targetNode, executionEntityId));
    }


    //删除当前运行时任务命令，并返回当前任务的执行对象id
//这里继承了NeedsActiveTaskCmd，主要时很多跳转业务场景下，要求不能时挂起任务。可以直接继承Command即可
    public class DeleteTaskCmd extends NeedsActiveTaskCmd<String> {
        public DeleteTaskCmd(String taskId) {
            super(taskId);
        }

        public String execute(CommandContext commandContext, TaskEntity currentTask) {
            //获取所需服务
            TaskEntityManagerImpl taskEntityManager = (TaskEntityManagerImpl) commandContext.getTaskEntityManager();
            //获取当前任务的来源任务及来源节点信息
            ExecutionEntity executionEntity = currentTask.getExecution();
            //删除当前任务,来源任务
            taskEntityManager.deleteTask(currentTask, "jumpReason", false, false);
            return executionEntity.getId();
        }

        public String getSuspendedTaskException() {
            return "挂起的任务不能跳转";
        }
    }

    //根据提供节点和执行对象id，进行跳转命令
    public class SetFLowNodeAndGoCmd implements Command<Void> {
        private FlowNode flowElement;
        private String executionId;

        public SetFLowNodeAndGoCmd(FlowNode flowElement, String executionId) {
            this.flowElement = flowElement;
            this.executionId = executionId;
        }

        public Void execute(CommandContext commandContext) {

            ExecutionEntity executionEntity = commandContext.getExecutionEntityManager().findById(executionId);

            //获取目标节点的来源连线
            List<SequenceFlow> flows = flowElement.getIncomingFlows();
            if (flows == null || flows.size() < 1) {

                executionEntity.setCurrentFlowElement(flowElement);
                commandContext.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);

            } else {
                //随便选一条连线来执行，时当前执行计划为，从连线流转到目标节点，实现跳转
                executionEntity.setCurrentFlowElement(flows.get(0));
            }

            commandContext.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);

            return null;
        }
    }


    /***
     * 转化显示Bean
     * @param list 待转化列表
     * @param clazz 显示类
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> List<T> listToBeanVo(List list, Class<T> clazz, String... ignoreProperties) {
        if (list == null)
            return null;

        List<T> rlist = new ArrayList<>();
        try {
            for (Object obj : list) {
                T t = objToBeanVo(obj, clazz, ignoreProperties);
                rlist.add(t);
            }
        } catch (Exception e) {
            log.error("listToBeanVo error:" + e.getMessage());
            e.printStackTrace();
        }
        return rlist;
    }

    /**
     * 复制源对象属性到目标对象
     *
     * @param obj
     * @param clazz
     * @param ignoreProperties
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> T objToBeanVo(Object obj, Class<T> clazz, String... ignoreProperties) {
        try {
            T t = (T) Class.forName(clazz.getName()).newInstance();
            BeanUtils.copyProperties(obj, t, ignoreProperties);
            return t;
        } catch (Exception e) {
            log.error("objToBeanVo error:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}