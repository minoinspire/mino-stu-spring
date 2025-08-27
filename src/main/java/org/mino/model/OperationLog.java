package org.mino.model;

import java.time.LocalDateTime;

/**
 * 操作日志模型类
 */
public class OperationLog {
    private Long id;
    private Long userId;
    private String operation;
    private String method;
    private String params;
    private String ip;
    private LocalDateTime createTime;
    
    // 构造函数
    public OperationLog() {}
    
    public OperationLog(Long userId, String operation, String method, String params, String ip) {
        this.userId = userId;
        this.operation = operation;
        this.method = method;
        this.params = params;
        this.ip = ip;
        this.createTime = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getParams() {
        return params;
    }
    
    public void setParams(String params) {
        this.params = params;
    }
    
    public String getIp() {
        return ip;
    }
    
    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public LocalDateTime getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
    
    @Override
    public String toString() {
        return "OperationLog{" +
                "id=" + id +
                ", userId=" + userId +
                ", operation='" + operation + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", ip='" + ip + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
