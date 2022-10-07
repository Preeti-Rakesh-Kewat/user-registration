package com.pnc.registration.controlller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void registerUser() throws Exception {

        mvc.perform(post("/user-management/v1/register")
                        .content("{\n" +
                                "  \"userName\": \"testUserName\",\n" +
                                "  \"password\": \"IbmTest$1357\",\n" +
                                "  \"ipAddress\": \"24.67.0.1\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registrationNumber").isNotEmpty())
                .andExpect(jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void registerUser_UserNameFieldErrors() throws Exception {
        mvc.perform(post("/user-management/v1/register")
                        .content("{\n" +
                                "  \"password\": \"IbmTest$1357\",\n" +
                                "  \"ipAddress\": \"24.67.0.1\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void registerUser_AllFieldErrors() throws Exception {
        mvc.perform(post("/user-management/v1/register")
                        .content("{\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    public void registerUser_PasswordFieldErrors() throws Exception {
        mvc.perform(post("/user-management/v1/register")
                        .content("{\n" +
                                "  \"userName\": \"testUserName\",\n" +
                                "  \"password\": \"IbmTest\",\n" +
                                "  \"ipAddress\": \"24.67.0.1\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(2));
    }

}
