# Mino Spring Boot 学习项目

这是一个使用Spring Boot 3.2.0的完整RESTful API项目，采用标准的分层架构和接口设计模式，集成了完整的日志管理系统和数据存储功能。

## 🏗️ 项目架构

项目采用标准的三层架构设计：

```
src/main/java/org/mino/
├── MinoStuApplication.java          # Spring Boot主启动类
├── config/                          # 配置层
│   └── WebConfig.java              # Web配置类
├── controller/                      # 控制层
│   ├── HelloController.java        # 基础接口控制器
│   └── UserController.java         # 用户管理控制器
├── service/                         # 服务层
│   ├── UserService.java            # 用户服务接口
│   ├── LogService.java             # 日志服务接口
│   └── impl/
│       └── UserServiceImpl.java    # 用户服务实现类
├── mapper/                          # 数据访问层
│   └── UserMapper.java             # 用户数据访问接口
├── model/                           # 模型层
│   ├── User.java                   # 用户模型类
│   ├── OperationLog.java           # 操作日志模型类
│   └── ApiResponse.java            # 通用API响应模型
├── interceptor/                     # 拦截器层
│   └── LogInterceptor.java         # 日志拦截器
└── exception/                       # 异常处理层
    └── GlobalExceptionHandler.java # 全局异常处理器
```

## ✨ 项目特性

- **Spring Boot 3.2.0** - 最新稳定版本
- **Java 17** - 支持现代Java特性
- **分层架构** - 标准的MVC架构设计
- **接口设计** - 所有服务层都采用接口形式
- **RESTful API** - 遵循REST设计原则
- **统一响应格式** - 标准化的API响应模型
- **全局异常处理** - 统一的错误处理机制
- **跨域支持** - 配置了CORS支持
- **MyBatis集成** - 完整的数据访问层
- **Druid连接池** - 高性能数据库连接池
- **SLF4J2日志管理** - 分级别日志输出和文件管理
- **操作日志记录** - 自动记录所有API操作
- **多环境配置** - 支持开发、生产环境配置

## 🚀 快速开始

### 1. 环境准备

- **JDK 17+**
- **MySQL 8.0+**
- **Maven 3.6+**

### 2. 数据库初始化

```sql
-- 执行 src/main/resources/db/init.sql 脚本
-- 创建数据库和表结构
```

### 3. 配置数据库连接

修改 `src/main/resources/application-dev.yml` 中的数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mino_spring_stu?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: your_username
    password: your_password
```

### 4. 运行项目

```bash
# 编译项目
mvn clean compile

# 运行项目（开发环境）
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 或者指定环境
java -jar target/mino-spring-stu-1.0-SNAPSHOT.jar --spring.profiles.active=dev
```

## 📊 数据存储功能

### 数据库设计

- **用户表 (user)** - 存储用户基本信息
- **操作日志表 (operation_log)** - 记录所有API操作
- **系统配置表 (system_config)** - 存储系统配置信息

### MyBatis特性

- **XML映射文件** - 清晰的SQL管理
- **类型别名** - 自动类型转换
- **驼峰命名** - 自动字段映射
- **二级缓存** - 提升查询性能
- **延迟加载** - 优化关联查询

### 连接池优化

- **Druid连接池** - 阿里巴巴开源高性能连接池
- **连接监控** - 实时监控连接池状态
- **SQL监控** - 慢SQL检测和优化
- **防火墙** - SQL注入防护

## 📝 日志管理系统

### 日志配置

- **logback-spring.xml** - 完整的日志配置文件
- **分级别输出** - DEBUG、INFO、WARN、ERROR级别分离
- **文件滚动** - 按日期自动分割日志文件
- **多环境支持** - 开发、生产环境不同日志级别

### 日志分类

- **业务日志** - 记录业务操作信息
- **SQL日志** - 记录数据库操作
- **错误日志** - 单独记录错误信息
- **访问日志** - 记录API访问情况

### 操作日志

- **自动记录** - 拦截器自动记录所有API操作
- **详细信息** - 记录操作类型、参数、IP地址等
- **性能监控** - 记录请求耗时
- **异步处理** - 不影响API响应性能

## 🔧 技术实现

### 接口设计模式
- **Service接口**: 定义业务逻辑契约
- **Service实现类**: 实现具体业务逻辑
- **Mapper接口**: 定义数据访问方法
- **Controller**: 处理HTTP请求和响应
- **Model**: 数据模型和传输对象

### 统一响应格式
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {...},
  "timestamp": 1640995200000
}
```

### 异常处理
- 全局异常处理器统一处理各种异常
- 标准化的错误响应格式
- HTTP状态码与业务状态码分离

### 性能优化
- **连接池管理** - 复用数据库连接
- **SQL优化** - 索引优化和查询优化
- **缓存策略** - MyBatis二级缓存
- **异步日志** - 不阻塞主业务流程

## 📁 项目结构详解

### 1. 模型层 (Model)
- **User.java**: 用户实体类，包含基本用户信息
- **OperationLog.java**: 操作日志实体类
- **ApiResponse.java**: 通用API响应包装类，支持泛型

### 2. 服务层 (Service)
- **UserService.java**: 用户服务接口，定义业务方法
- **LogService.java**: 日志服务接口，定义日志管理方法
- **UserServiceImpl.java**: 用户服务实现类，实现具体业务逻辑

### 3. 数据访问层 (Mapper)
- **UserMapper.java**: 用户数据访问接口，定义数据库操作方法

### 4. 控制层 (Controller)
- **UserController.java**: 用户管理REST接口
- **HelloController.java**: 基础功能接口

### 5. 配置层 (Config)
- **WebConfig.java**: Web相关配置，包括CORS和拦截器

### 6. 拦截器层 (Interceptor)
- **LogInterceptor.java**: 日志拦截器，自动记录操作日志

### 7. 异常处理层 (Exception)
- **GlobalExceptionHandler.java**: 全局异常处理器

## 🛠️ 开发环境要求

- **JDK**: 17+
- **Maven**: 3.6+
- **MySQL**: 8.0+
- **IDE**: IntelliJ IDEA 或 Eclipse
- **Spring Boot**: 3.2.0

## 🔮 扩展建议

1. **数据库集成**: 可以集成MySQL、PostgreSQL等数据库
2. **缓存**: 添加Redis缓存支持
3. **安全**: 集成Spring Security进行身份认证
4. **监控**: 添加Actuator和Micrometer监控
5. **文档**: 集成Swagger/OpenAPI文档
6. **测试**: 添加单元测试和集成测试
7. **消息队列**: 集成RabbitMQ或Kafka
8. **分布式**: 支持微服务架构

## 📝 开发规范

- 所有Service层必须定义接口
- Controller层只处理HTTP请求，不包含业务逻辑
- 使用统一的异常处理机制
- 遵循RESTful API设计规范
- 代码注释完整，方法命名清晰
- 所有数据库操作都要记录日志
- 使用事务管理确保数据一致性

## 🚀 部署说明

### 生产环境配置

```bash
# 使用生产环境配置
java -jar target/mino-spring-stu-1.0-SNAPSHOT.jar --spring.profiles.active=prod
```

### 监控访问

- **Druid监控**: http://localhost:8080/druid/
- **用户名**: admin
- **密码**: admin

### 日志文件位置

- **业务日志**: `logs/business.log`
- **SQL日志**: `logs/sql.log`
- **错误日志**: `logs/error.log`
- **综合日志**: `logs/mino-spring-stu.log`
