package br.com.zup.academy.benzaquem.bloqueio;

import br.com.zup.academy.benzaquem.cartao.Cartao;
import br.com.zup.academy.benzaquem.cartao.CartaoRepository;
import org.junit.jupiter.api.*;
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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BloqueiosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartaoRepository cartaoRepository;

    @BeforeEach
    public void setup() {
        cartaoRepository.save(new Cartao("1232-1234-1234-8888", null));
    }


    @Test
    @Order(1)
    public void bloquearCartaoSucessoRetorna200() throws Exception{
        String idCartao = "1232-1234-1234-8888";
        URI uri = new URI("/bloqueios/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
//                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/bloqueios/1"));
    }

    @Test
    @Order(2)
    public void bloquearCartaoJaBloqueadoRetorna422() throws Exception{
        String idCartao = "1232-1234-1234-8888";
        URI uri = new URI("/bloqueios/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
//                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/bloqueios/1"));
    }

    @Test
    public void bloquearCartaoInvalidoRetorna404() throws Exception{
        String idCartao = "1232-1234-1234-1234";
        URI uri = new URI("/bloqueios/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().string("Cartão com ID = " + idCartao + " não foi encontrado!"));
    }
    @Test
    public void requisicaoIdCartaoInvalidaRetorna400() throws Exception{
        String idCartao = "***";
        URI uri = new URI("/bloqueios/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }


}
