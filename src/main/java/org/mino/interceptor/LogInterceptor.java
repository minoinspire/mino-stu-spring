package org.mino.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mino.model.OperationLog;
import org.mino.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 日志拦截器
 */
@Component
public class LogInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    
    private final LogService logService;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public LogInterceptor(LogService logService, ObjectMapper objectMapper) {
        this.logService = logService;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            // 获取请求信息
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String ip = getClientIpAddress(request);
            String userAgent = request.getHeader("User-Agent");
            
            // 构建操作描述
            String operation = buildOperationDescription(method, uri);
            
            // 获取请求参数
            String params = getRequestParams(request);
            
            // 获取用户ID（这里简化处理，实际项目中应该从认证信息中获取）
            Long userId = getUserIdFromRequest(request);
            
            // 创建操作日志
            OperationLog operationLog = new OperationLog(userId, operation, method + " " + uri, params, ip);
            
            // 异步记录日志
            logService.recordOperationLog(operationLog);
            
            // 记录请求耗时
            long startTime = (Long) request.getAttribute("startTime");
            long duration = System.currentTimeMillis() - startTime;
            
            logger.info("Request completed: {} {} - {}ms", method, uri, duration);
            
        } catch (Exception e) {
            logger.error("Error recording operation log", e);
        }
    }
    
    /**
     * 获取客户端IP地址
     */
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedFor)) {
            return xForwardedFor.split(",")[0];
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty() && !"unknown".equalsIgnoreCase(xRealIp)) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
    
    /**
     * 构建操作描述
     */
    private String buildOperationDescription(String method, String uri) {
        if (uri.startsWith("/api/users")) {
            switch (method) {
                case "GET":
                    if (uri.matches("/api/users/\\d+")) {
                        return "查询用户详情";
                    } else if (uri.matches("/api/users/username/.*")) {
                        return "根据用户名查询用户";
                    } else if (uri.matches("/api/users/exists/.*")) {
                        return "检查用户名是否存在";
                    } else {
                        return "查询用户列表";
                    }
                case "POST":
                    return "创建用户";
                case "PUT":
                    return "更新用户";
                case "DELETE":
                    return "删除用户";
                default:
                    return "用户管理操作";
            }
        } else if (uri.startsWith("/api/hello")) {
            return "问候接口调用";
        } else if (uri.startsWith("/api/info")) {
            return "获取系统信息";
        } else {
            return "未知操作";
        }
    }
    
    /**
     * 获取请求参数
     */
    private String getRequestParams(HttpServletRequest request) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap.isEmpty()) {
                return "{}";
            }
            return objectMapper.writeValueAsString(parameterMap);
        } catch (Exception e) {
            logger.warn("Error serializing request parameters", e);
            return "{}";
        }
    }
    
    /**
     * 从请求中获取用户ID（简化实现）
     */
    private Long getUserIdFromRequest(HttpServletRequest request) {
        // 这里简化处理，实际项目中应该从JWT token或session中获取
        // 可以从请求头、cookie或session中获取用户信息
        String userIdHeader = request.getHeader("X-User-Id");
        if (userIdHeader != null && !userIdHeader.isEmpty()) {
            try {
                return Long.parseLong(userIdHeader);
            } catch (NumberFormatException e) {
                logger.warn("Invalid user ID in header: {}", userIdHeader);
            }
        }
        return null;
    }
}
