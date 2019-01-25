package br.com.api.admin.resource;

import br.com.api.admin.entity.User;
import br.com.api.admin.enumeration.ProfileEnum;
import br.com.api.admin.exception.BusinessException;
import br.com.api.admin.service.UserService;
import br.com.api.admin.utils.JwtUtil;
import io.jsonwebtoken.Header;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserResourceTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("rafaalberto");
        user.setPassword("123456");
        user.setProfile(ProfileEnum.ROLE_ADMIN);
    }

    @Test
    public void shouldReturnValidUserData() throws Exception {
        BDDMockito.given(userService.findById(Mockito.anyLong())).willReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + fetchToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("rafaalberto"));
    }

    @Test
    public void shouldReturnUserNotFound() throws Exception {
        BDDMockito.given(userService.findById(Mockito.anyLong())).willThrow(new BusinessException("error-user-9", HttpStatus.BAD_REQUEST));
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + fetchToken())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].code").value("error-user-9"));

    }

    private String fetchToken() {
        BDDMockito.given(userService.findByUsername(Mockito.anyString())).willReturn(user);
        return jwtUtil.generateToken(user.getUsername());
    }

}
