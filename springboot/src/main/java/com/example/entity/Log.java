package com.example.entity;

public class Log {
    private Integer id;
    private String operatorName;
    private String operatorRole;
    private String operation;
    private String time;

    public Log(String operatorName, String operatorRole, String operation) {
        this.operatorName = operatorName;
        this.operatorRole = operatorRole;
        this.operation = operation;
    }

    public Log() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorRole() {
        return operatorRole;
    }

    public void setOperatorRole(String operatorRole) {
        this.operatorRole = operatorRole;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
