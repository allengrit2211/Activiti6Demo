package com.activiti6.demo.vo;

import java.util.Map;

/**
 * @author Allen
 * @Date: 2018/6/27 19:38
 * @Description:
 */
public class ProcessDefinitionVo extends BaseVo {

    protected String id;
    protected String name;
    protected String description;
    protected String key;
    protected int version;
    protected String category;
    protected String deploymentId;
    protected String resourceName;
    protected String tenantId = "";
    protected Integer historyLevel;
    protected String diagramResourceName;
    protected boolean isGraphicalNotationDefined;
    protected Map<String, Object> variables;
    protected boolean hasStartFormKey;
    protected int suspensionState;
    protected boolean isIdentityLinksInitialized;
    protected String engineVersion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getHistoryLevel() {
        return historyLevel;
    }

    public void setHistoryLevel(Integer historyLevel) {
        this.historyLevel = historyLevel;
    }

    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    public boolean isGraphicalNotationDefined() {
        return isGraphicalNotationDefined;
    }

    public void setGraphicalNotationDefined(boolean graphicalNotationDefined) {
        isGraphicalNotationDefined = graphicalNotationDefined;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public boolean isHasStartFormKey() {
        return hasStartFormKey;
    }

    public void setHasStartFormKey(boolean hasStartFormKey) {
        this.hasStartFormKey = hasStartFormKey;
    }

    public int getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(int suspensionState) {
        this.suspensionState = suspensionState;
    }

    public boolean isIdentityLinksInitialized() {
        return isIdentityLinksInitialized;
    }

    public void setIdentityLinksInitialized(boolean identityLinksInitialized) {
        isIdentityLinksInitialized = identityLinksInitialized;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }
}
