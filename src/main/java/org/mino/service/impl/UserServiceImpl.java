package org.mino.service.impl;

import org.mino.mapper.UserMapper;
import org.mino.model.User;
import org.mino.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 用户服务实现类
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    
    private final UserMapper userMapper;
    
    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
        logger.info("UserServiceImpl initialized with UserMapper");
    }
    
    @Override
    public User createUser(User user) {
        logger.debug("Creating user with username: {}", user.getUsername());
        
        if (user == null) {
            logger.error("User object is null");
            throw new IllegalArgumentException("用户信息不能为空");
        }
        
        if (existsByUsername(user.getUsername())) {
            logger.warn("Username already exists: {}", user.getUsername());
            throw new RuntimeException("用户名已存在");
        }
        
        if (userMapper.countByEmail(user.getEmail()) > 0) {
            logger.warn("Email already exists: {}", user.getEmail());
            throw new RuntimeException("邮箱已存在");
        }
        
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        user.setUpdateTime(now);
        
        try {
            int result = userMapper.insert(user);
            if (result > 0) {
                logger.info("User created successfully: {}", user.getUsername());
                return user;
            } else {
                logger.error("Failed to create user: {}", user.getUsername());
                throw new RuntimeException("用户创建失败");
            }
        } catch (Exception e) {
            logger.error("Error creating user: {}", user.getUsername(), e);
            throw new RuntimeException("用户创建失败: " + e.getMessage());
        }
    }
    
    @Override
    public Optional<User> findById(Long id) {
        logger.debug("Finding user by ID: {}", id);
        
        if (id == null) {
            logger.warn("User ID is null");
            return Optional.empty();
        }
        
        try {
            User user = userMapper.selectById(id);
            if (user != null) {
                logger.debug("User found by ID: {}", id);
            } else {
                logger.debug("No user found with ID: {}", id);
            }
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Error finding user by ID: {}", id, e);
            throw new RuntimeException("查询用户失败: " + e.getMessage());
        }
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        logger.debug("Finding user by username: {}", username);
        
        if (username == null || username.trim().isEmpty()) {
            logger.warn("Username is null or empty");
            return Optional.empty();
        }
        
        try {
            User user = userMapper.selectByUsername(username);
            if (user != null) {
                logger.debug("User found by username: {}", username);
            } else {
                logger.debug("No user found with username: {}", username);
            }
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Error finding user by username: {}", username, e);
            throw new RuntimeException("查询用户失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<User> findAllUsers() {
        logger.debug("Finding all users");
        
        try {
            List<User> users = userMapper.selectAll();
            logger.info("Found {} users", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error finding all users", e);
            throw new RuntimeException("查询所有用户失败: " + e.getMessage());
        }
    }
    
    @Override
    public User updateUser(User user) {
        logger.debug("Updating user with ID: {}", user.getId());
        
        if (user == null || user.getId() == null) {
            logger.error("User or user ID is null");
            throw new IllegalArgumentException("用户信息或ID不能为空");
        }
        
        // 检查用户是否存在
        Optional<User> existingUser = findById(user.getId());
        if (existingUser.isEmpty()) {
            logger.warn("User not found with ID: {}", user.getId());
            throw new RuntimeException("用户不存在");
        }
        
        // 检查用户名是否重复（排除当前用户）
        User existing = existingUser.get();
        if (!existing.getUsername().equals(user.getUsername()) && existsByUsername(user.getUsername())) {
            logger.warn("Username already exists: {}", user.getUsername());
            throw new RuntimeException("用户名已存在");
        }
        
        // 检查邮箱是否重复（排除当前用户）
        if (!existing.getEmail().equals(user.getEmail()) && userMapper.countByEmail(user.getEmail()) > 0) {
            logger.warn("Email already exists: {}", user.getEmail());
            throw new RuntimeException("邮箱已存在");
        }
        
        // 设置更新时间
        user.setUpdateTime(LocalDateTime.now());
        
        try {
            int result = userMapper.update(user);
            if (result > 0) {
                logger.info("User updated successfully: {}", user.getUsername());
                return user;
            } else {
                logger.error("Failed to update user: {}", user.getUsername());
                throw new RuntimeException("用户更新失败");
            }
        } catch (Exception e) {
            logger.error("Error updating user: {}", user.getUsername(), e);
            throw new RuntimeException("用户更新失败: " + e.getMessage());
        }
    }
    
    @Override
    public boolean deleteUser(Long id) {
        logger.debug("Deleting user with ID: {}", id);
        
        if (id == null) {
            logger.warn("User ID is null");
            return false;
        }
        
        try {
            int result = userMapper.deleteById(id);
            if (result > 0) {
                logger.info("User deleted successfully: {}", id);
                return true;
            } else {
                logger.warn("No user found to delete with ID: {}", id);
                return false;
            }
        } catch (Exception e) {
            logger.error("Error deleting user: {}", id, e);
            throw new RuntimeException("用户删除失败: " + e.getMessage());
        }
    }
    
    @Override
    public boolean existsByUsername(String username) {
        logger.debug("Checking if username exists: {}", username);
        
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        
        try {
            int count = userMapper.countByUsername(username);
            boolean exists = count > 0;
            logger.debug("Username '{}' exists: {}", username, exists);
            return exists;
        } catch (Exception e) {
            logger.error("Error checking username existence: {}", username, e);
            throw new RuntimeException("检查用户名失败: " + e.getMessage());
        }
    }
}

