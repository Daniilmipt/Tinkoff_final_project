package com.example.unittest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SecurityTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void auth() throws Exception {
        String body = "{\"name\":\"misha\", \"password\":\"q\"}";
        this.mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void auth_unAuthorize() throws Exception {
        String body = "{\"name\":\"misha\", \"password\":\"q\"}";
        this.mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void logout() throws Exception {
        this.mockMvc.perform(delete("/out"))
                .andExpect(status().isOk());
    }

    @Test
    public void logout_unAuthorize() throws Exception {
        this.mockMvc.perform(delete("/out"))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void registration() throws Exception {
        String body = "{\"name\":\"anonim\", \"password\":\"q\"}";
        this.mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @WithMockUser(roles = {"USER", "ADMIN"})
    public void registration_unAuthorize() throws Exception {
        String body = "{\"name\":\"petya\", \"password\":\"q\"}";
        this.mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    public void registration_alreadyExists() throws Exception {
        String body = "{\"name\":\"misha\", \"password\":\"q\"}";
        this.mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }
}
