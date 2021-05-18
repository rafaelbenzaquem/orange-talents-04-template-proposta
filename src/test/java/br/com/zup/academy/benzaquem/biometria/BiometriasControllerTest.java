package br.com.zup.academy.benzaquem.biometria;

import br.com.zup.academy.benzaquem.cartao.Cartao;
import br.com.zup.academy.benzaquem.cartao.CartaoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.util.Base64;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class BiometriasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartaoRepository cartaoRepository;

    @BeforeEach
    public void setup() {
        cartaoRepository.save(new Cartao("1232-1234-1234-9999", null));
    }

    @Test
    public void cadastrarBiometriaComSucessoRetorna201() throws Exception {

        String fakeDigital = Base64.getEncoder().encodeToString("Minha digital".getBytes());
        String idCartao = "1232-1234-1234-9999";

        URI uri = new URI("/biometrias/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.TEXT_PLAIN)
                .content(fakeDigital))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/biometrias/1"));
    }

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
