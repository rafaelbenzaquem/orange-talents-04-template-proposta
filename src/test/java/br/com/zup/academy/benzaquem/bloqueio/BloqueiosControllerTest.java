package br.com.zup.academy.benzaquem.bloqueio;

import br.com.zup.academy.benzaquem.cartao.*;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
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

    @MockBean
    private CartaoExternalService cartaoExternalService;



    @BeforeEach
    public void setup() {
        cartaoRepository.save(new Cartao("1232-1234-1234-8888", null));
    }


    @Test
    @Order(1)
    public void bloquearCartaoSucessoRetorna200() throws Exception{
        String idCartao = "1232-1234-1234-8888";
        URI uri = new URI("/bloqueios/" + idCartao + "/cartao");

        Mockito.when(cartaoExternalService.bloquearCartao(idCartao,new BloquearRequest("benzaquem\\proposta")))
                .thenReturn(new BloquearResponse("BLOQUEADO"));

        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    @Order(2)
    public void bloquearCartaoJaBloqueadoRetorna422() throws Exception{
        String idCartao = "1232-1234-1234-8888";
        URI uri = new URI("/bloqueios/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @Test
    public void bloquearCartaoInexistenteRetorna404() throws Exception{
        String idCartao = "1232-1234-1234-1234";
        URI uri = new URI("/bloqueios/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().string("Cartão com ID = " + idCartao + " não foi encontrado!"));
    }
    @Test
    public void bloquearCartaoInvalidoRetorna400() throws Exception{
        String idCartao = "***";
        URI uri = new URI("/bloqueios/" + idCartao + "/cartao");

        mockMvc.perform(MockMvcRequestBuilders.put(uri))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

}
