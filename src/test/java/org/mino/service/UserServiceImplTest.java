package org.mino.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mino.mapper.UserMapper;
import org.mino.model.User;
import org.mino.service.impl.UserServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User sample;

    @BeforeEach
    void setUp() {
        sample = new User(1L, "alice", "alice@example.com", "13000000000");
        sample.setCreateTime(LocalDateTime.now());
        sample.setUpdateTime(LocalDateTime.now());
    }

    @Test
    @DisplayName("createUser: 正常创建")
    void testCreateUser() {
        when(userMapper.countByUsername("alice")).thenReturn(0);
        when(userMapper.countByEmail("alice@example.com")).thenReturn(0);
        when(userMapper.insert(any(User.class))).thenReturn(1);

        User created = userService.createUser(new User(null, "alice", "alice@example.com", "13000000000"));
        assertNotNull(created);
        assertEquals("alice", created.getUsername());
        verify(userMapper).insert(any(User.class));
    }

    @Test
    @DisplayName("createUser: 用户名冲突抛出异常")
    void testCreateUserUsernameExists() {
        when(userMapper.countByUsername("alice")).thenReturn(1);
        assertThrows(RuntimeException.class, () -> userService.createUser(new User(null, "alice", "a@a.com", "1")));
    }

    @Test
    @DisplayName("findById: 查询存在用户")
    void testFindById() {
        when(userMapper.selectById(1L)).thenReturn(sample);
        Optional<User> res = userService.findById(1L);
        assertTrue(res.isPresent());
        assertEquals("alice", res.get().getUsername());
    }

    @Test
    @DisplayName("findAllUsers: 返回用户列表")
    void testFindAllUsers() {
        when(userMapper.selectAll()).thenReturn(Collections.singletonList(sample));
        List<User> list = userService.findAllUsers();
        assertEquals(1, list.size());
    }

    @Test
    @DisplayName("updateUser: 正常更新")
    void testUpdateUser() {
        when(userMapper.selectById(1L)).thenReturn(sample);
        when(userMapper.update(any(User.class))).thenReturn(1);

        User toUpdate = new User(1L, "alice", "alice@example.com", "13999999999");
        User updated = userService.updateUser(toUpdate);
        assertEquals("13999999999", updated.getPhone());
        verify(userMapper).update(any(User.class));
    }

    @Test
    @DisplayName("deleteUser: 正常删除")
    void testDeleteUser() {
        when(userMapper.deleteById(1L)).thenReturn(1);
        assertTrue(userService.deleteUser(1L));
    }

    @Test
    @DisplayName("existsByUsername: 存在性检查")
    void testExistsByUsername() {
        when(userMapper.countByUsername("alice")).thenReturn(1);
        assertTrue(userService.existsByUsername("alice"));
    }
}
