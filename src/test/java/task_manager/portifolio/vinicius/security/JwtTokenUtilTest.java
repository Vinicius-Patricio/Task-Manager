package task_manager.portifolio.vinicius.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

//Habilita o mockito para Junit 5
@ExtendWith(MockitoExtension.class)
public class JwtTokenUtilTest {

    //Injeta os mocks no JwtTokenUtil
    @InjectMocks
    private JwtTokenUtil jwtTokenUtil;

    // Gera uma chave HMAC-SHA temporária apenas para testes
    @BeforeEach
    void setUp() {
        String testSecret = "testSecretKeyWithMinimum32CharactersLength123";
        ReflectionTestUtils.setField(jwtTokenUtil, "jwtSecretKey", testSecret);
        ReflectionTestUtils.invokeMethod(jwtTokenUtil, "init");
    }

    //Testa se o token é gerado corretamente
    @Test
    void shouldGenerateValidToken(){
        String username = "testuser";
        String token = jwtTokenUtil.generateToken(username);

        assertNotNull(token);
        assertEquals(username, jwtTokenUtil.extractUsername(token));
    }

    //Testa se o token é valido
    @Test
    void shouldValidateTokenCorrectly(){

        String username = "testuser";
        String token = jwtTokenUtil.generateToken(username);

        assertTrue(jwtTokenUtil.isTokenValid(token, mockUserDetails(username)));
    }

    //Cria um mock do UserDetails
    private UserDetails mockUserDetails(String username){
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        return userDetails;
    }
}
