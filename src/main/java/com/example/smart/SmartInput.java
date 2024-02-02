package com.example.smart;

import java.util.Date;

public class SmartInput {
    private Integer id;
    private String moduleName;
    private String className;
    private String jobName;
    private String inputString;
    private String apiDocument;

    private Integer status;

    private String localModuleName;

    private Date date;


    public SmartInput(Integer id, String moduleName, String className, String jobName, String inputString, String apiDocument, Integer status, String localModuleName, Date date) {
        this.id = id;
        this.moduleName = moduleName;
        this.className = className;
        this.jobName = jobName;
        this.inputString = inputString;
        this.apiDocument = apiDocument;
        this.status = status;
        this.localModuleName = localModuleName;
        this.date = date;
    }

    public SmartInput(Integer id, String moduleName, String className, String jobName, String inputString, String apiDocument, String localModuleName, Date date) {
        this.id = id;
        this.moduleName = moduleName;
        this.className = className;
        this.jobName = jobName;
        this.inputString = inputString;
        this.apiDocument = apiDocument;
        this.localModuleName = localModuleName;
        this.date = date;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public void setApiDocument(String apiDocument) {
        this.apiDocument = apiDocument;
    }

    public int getId() {
        return id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getClassName() {
        return className;
    }

    public String getJobName() {
        return jobName;
    }

    public String getInputString() {
        return inputString;
    }

    public String getApiDocument() {
        return apiDocument;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLocalModuleName() {
        return localModuleName;
    }

    public void setLocalModuleName(String localModuleName) {
        this.localModuleName = localModuleName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SmartInput{" +
                "id=" + id +
                ", moduleString='" + moduleName + '\'' +
                ", className='" + className + '\'' +
                ", jobName='" + jobName + '\'' +
                ", inputString='" + inputString + '\'' +
                ", apiDocument='" + apiDocument + '\'' +
                ", status='" + status + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
