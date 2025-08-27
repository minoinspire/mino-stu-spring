package org.mino.controller;

import org.mino.model.ApiResponse;
import org.mino.model.User;
import org.mino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * 创建用户
     */
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ApiResponse.success("用户创建成功", createdUser);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    /**
     * 根据ID获取用户
     */
    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ApiResponse.success("用户查询成功", user))
                .orElse(ApiResponse.error(404, "用户不存在"));
    }
    
    /**
     * 根据用户名获取用户
     */
    @GetMapping("/username/{username}")
    public ApiResponse<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(user -> ApiResponse.success("用户查询成功", user))
                .orElse(ApiResponse.error(404, "用户不存在"));
    }
    
    /**
     * 获取所有用户
     */
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ApiResponse.success("用户列表查询成功", users);
    }
    
    /**
     * 更新用户
     */
    @PutMapping("/{id}")
    public ApiResponse<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            User updatedUser = userService.updateUser(user);
            return ApiResponse.success("用户更新成功", updatedUser);
        } catch (Exception e) {
            return ApiResponse.error(e.getMessage());
        }
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            return ApiResponse.success("用户删除成功", true);
        } else {
            return ApiResponse.error(404, "用户不存在");
        }
    }
    
    /**
     * 检查用户名是否存在
     */
    @GetMapping("/exists/{username}")
    public ApiResponse<Boolean> checkUsernameExists(@PathVariable String username) {
        boolean exists = userService.existsByUsername(username);
        return ApiResponse.success("用户名检查完成", exists);
    }
}
