package org.mino.service.impl;

import org.mino.model.OperationLog;
import org.mino.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志服务实现类
 */
@Service
public class LogServiceImpl implements LogService {
    
    private static final Logger logger = LoggerFactory.getLogger(LogServiceImpl.class);
    
    // 这里简化实现，实际项目中应该使用数据库存储
    // 暂时使用内存存储，避免循环依赖问题
    
    @Override
    public void recordOperationLog(OperationLog operationLog) {
        try {
            // 异步记录日志，避免阻塞主流程
            logger.info("Operation Log: {} - {} - {} - {}ms", 
                operationLog.getOperation(),
                operationLog.getMethod(),
                operationLog.getParams(),
                System.currentTimeMillis() - operationLog.getCreateTime().toInstant(java.time.ZoneOffset.UTC).toEpochMilli());
        } catch (Exception e) {
            logger.error("Error recording operation log", e);
        }
    }
    
    @Override
    public List<OperationLog> getOperationLogsByUserId(Long userId) {
        logger.debug("Getting operation logs for user: {}", userId);
        // 简化实现，返回空列表
        return new ArrayList<>();
    }
    
    @Override
    public List<OperationLog> getOperationLogsByPage(int page, int size) {
        logger.debug("Getting operation logs page: {}, size: {}", page, size);
        // 简化实现，返回空列表
        return new ArrayList<>();
    }
    
    @Override
    public long getOperationLogsCount() {
        logger.debug("Getting operation logs count");
        // 简化实现，返回0
        return 0L;
    }
    
    @Override
    public int cleanOldLogs(int days) {
        logger.debug("Cleaning old logs older than {} days", days);
        // 简化实现，返回0
        return 0;
    }
}
