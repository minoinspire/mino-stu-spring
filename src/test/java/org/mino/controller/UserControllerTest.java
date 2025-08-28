package org.mino.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mino.model.User;
import org.mino.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/users 返回列表")
    void testGetAllUsers() throws Exception {
        when(userService.findAllUsers()).thenReturn(List.of(new User(1L, "alice", "alice@example.com", "1")));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    @DisplayName("GET /api/users/{id} 返回详情")
    void testGetUserById() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(new User(1L, "alice", "alice@example.com", "1")));
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.username").value("alice"));
    }

    @Test
    @DisplayName("POST /api/users 创建用户")
    void testCreateUser() throws Exception {
        User req = new User(null, "bob", "bob@example.com", "2");
        when(userService.createUser(any(User.class))).thenReturn(new User(2L, "bob", "bob@example.com", "2"));
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.username").value("bob"));
    }

    @Test
    @DisplayName("PUT /api/users/{id} 更新用户")
    void testUpdateUser() throws Exception {
        User req = new User(1L, "alice", "alice@example.com", "9");
        when(userService.updateUser(any(User.class))).thenReturn(req);
        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.phone").value("9"));
    }

    @Test
    @DisplayName("DELETE /api/users/{id} 删除用户")
    void testDeleteUser() throws Exception {
        when(userService.deleteUser(1L)).thenReturn(true);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(true));
    }
}
