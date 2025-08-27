package org.mino.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mino.model.User;

import java.util.List;

/**
 * 用户数据访问接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 插入用户
     * @param user 用户信息
     * @return 影响行数
     */
    int insert(User user);
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户信息
     */
    User selectById(@Param("id") Long id);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    User selectByUsername(@Param("username") String username);
    
    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return 用户信息
     */
    User selectByEmail(@Param("email") String email);
    
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> selectAll();
    
    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 影响行数
     */
    int update(User user);
    
    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 存在数量
     */
    int countByUsername(@Param("username") String username);
    
    /**
     * 检查邮箱是否存在
     * @param email 邮箱
     * @return 存在数量
     */
    int countByEmail(@Param("email") String email);
    
    /**
     * 分页查询用户
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 用户列表
     */
    List<User> selectByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 统计用户总数
     * @return 用户总数
     */
    int countTotal();
}
