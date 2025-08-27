package org.mino.service;

import org.mino.model.User;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 创建用户
     * @param user 用户信息
     * @return 创建后的用户
     */
    User createUser(User user);
    
    /**
     * 根据ID查找用户
     * @param id 用户ID
     * @return 用户信息
     */
    Optional<User> findById(Long id);
    
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户信息
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 获取所有用户
     * @return 用户列表
     */
    List<User> findAllUsers();
    
    /**
     * 更新用户信息
     * @param user 用户信息
     * @return 更新后的用户
     */
    User updateUser(User user);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否删除成功
     */
    boolean deleteUser(Long id);
    
    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return 是否存在
     */
    boolean existsByUsername(String username);
}
