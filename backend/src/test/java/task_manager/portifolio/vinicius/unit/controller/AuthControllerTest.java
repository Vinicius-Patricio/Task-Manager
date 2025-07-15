package task_manager.portifolio.vinicius.unit.controller;


import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import task_manager.portifolio.vinicius.controller.LoginRequest;
import task_manager.portifolio.vinicius.security.JwtTokenUtil;

//Carrega o contexto do Spring
//Random
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//Configura o MockMvc para testes HTTP
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
})
@ActiveProfiles("test")
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    //@MockBean cria mocks das dependencias do controller, substituindo por testes
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @Test
    void login_shouldReturnToken() throws Exception {
        // Preparação
        String email = "teste@email.com";
        String password = "senha123";
        String token = "jwt.token.test";
        
        LoginRequest loginRequest = new LoginRequest(email, password);
        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(email);
        when(jwtTokenUtil.generateToken(email)).thenReturn(token);
        when(jwtTokenUtil.extractUsername(token)).thenReturn(email);

        // Verificação e ação
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value(token));
    }

    @Test
    void login_shouldReturnUnauthorized_whenCredentialsAreInvalid() throws Exception {
        // Verificação
        LoginRequest loginRequest = new LoginRequest("teste@email.com", "senha_errada");
        
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        // Verificação e ação
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
