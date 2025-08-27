package org.mino.service;

import org.mino.model.OperationLog;

import java.util.List;

/**
 * 日志服务接口
 */
public interface LogService {
    
    /**
     * 记录操作日志
     * @param operationLog 操作日志
     */
    void recordOperationLog(OperationLog operationLog);
    
    /**
     * 根据用户ID查询操作日志
     * @param userId 用户ID
     * @return 操作日志列表
     */
    List<OperationLog> getOperationLogsByUserId(Long userId);
    
    /**
     * 分页查询操作日志
     * @param page 页码
     * @param size 每页大小
     * @return 操作日志列表
     */
    List<OperationLog> getOperationLogsByPage(int page, int size);
    
    /**
     * 统计操作日志总数
     * @return 总数
     */
    long getOperationLogsCount();
    
    /**
     * 清理指定天数之前的日志
     * @param days 天数
     * @return 清理的日志数量
     */
    int cleanOldLogs(int days);
}
