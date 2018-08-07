package com.activiti6.demo.vo;



import java.util.Date;
import java.util.Map;

/**
 * @author Allen
 * @Date: 2018/6/27 20:18
 * @Description:
 */
public class TaskVo extends BaseVo{

    protected String id;
    protected String owner;
    protected int assigneeUpdatedCount;
    protected String originalAssignee;
    protected String assignee;
    protected String parentTaskId;
    protected String name;
    protected String localizedName;
    protected String description;
    protected String localizedDescription;
    protected int priority = 50;
    protected Date createTime;
    protected Date dueDate;
    protected int suspensionState;
    protected String category;
    protected boolean isIdentityLinksInitialized;
    protected String executionId;
    protected String processInstanceId;
    protected String processDefinitionId;
    protected String taskDefinitionKey;
    protected String formKey;
    protected boolean isDeleted;
    protected boolean isCanceled;
    protected String eventName;
    protected String tenantId;
    protected boolean forcedUpdate;
    protected Date claimTime;
    protected Map<String, Object> variables;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getAssigneeUpdatedCount() {
        return assigneeUpdatedCount;
    }

    public void setAssigneeUpdatedCount(int assigneeUpdatedCount) {
        this.assigneeUpdatedCount = assigneeUpdatedCount;
    }

    public String getOriginalAssignee() {
        return originalAssignee;
    }

    public void setOriginalAssignee(String originalAssignee) {
        this.originalAssignee = originalAssignee;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }


    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public void setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalizedDescription() {
        return localizedDescription;
    }

    public void setLocalizedDescription(String localizedDescription) {
        this.localizedDescription = localizedDescription;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(int suspensionState) {
        this.suspensionState = suspensionState;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isIdentityLinksInitialized() {
        return isIdentityLinksInitialized;
    }

    public void setIdentityLinksInitialized(boolean identityLinksInitialized) {
        isIdentityLinksInitialized = identityLinksInitialized;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }


    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public boolean isForcedUpdate() {
        return forcedUpdate;
    }

    public void setForcedUpdate(boolean forcedUpdate) {
        this.forcedUpdate = forcedUpdate;
    }

    public Date getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
