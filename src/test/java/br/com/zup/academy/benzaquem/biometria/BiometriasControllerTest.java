package br.com.zup.academy.benzaquem.biometria;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Base64;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class BiometriasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void cadastrarBiometriaComCartaoInvalidoRetorna404() throws Exception {
        String fakeDigital = Base64.getEncoder().encodeToString("Minha digital".getBytes());
        String idCartao = "1232-1234-1234-1234";
        URI uri = new URI("/biometrias/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.TEXT_PLAIN)
                .content(fakeDigital))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().string("Cartão com ID = " + idCartao + ", não foi encontrado!"));
    }

    @Test
    public void cadastrarBiometriaComDadosBiometricosInvalidosRetorna400() throws Exception {
        String fakeDigital = " ";
        String idCartao = "1232-1234-1234-1234";
        URI uri = new URI("/biometrias/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.TEXT_PLAIN)
                .content(fakeDigital))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(MockMvcResultMatchers.content().string("Dados biometricos inválidos."));
    }
}
