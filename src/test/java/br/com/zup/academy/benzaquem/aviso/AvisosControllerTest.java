package br.com.zup.academy.benzaquem.aviso;

import br.com.zup.academy.benzaquem.cartao.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class AvisosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartaoRepository cartaoRepository;

    @MockBean
    private CartaoExternalService cartaoExternalService;

    @BeforeEach
    public void setup() {
        cartaoRepository.save(new Cartao("1234-1234-1234-7777", null));
    }

    @Test
    public void criarAvisoViagemComSucessoRetorna200() throws Exception {
        String requestBody = "{" +
                "\"destino\":\"Uberlândia\"," +
                "\"dataTermino\":\"01/06/2028\"" +
                "}";

        String idCartao = "1234-1234-1234-7777";

        Mockito.when(cartaoExternalService.avisarViagem(idCartao,
                new AvisoLegadoRequest("Uberlândia", LocalDate.parse("01/06/2028",
                        DateTimeFormatter.ofPattern("dd/MM/yyyy")))))
                .thenReturn(new IdCartaoResponse(idCartao));

        URI uri = new URI("/viagens/" + idCartao + "/cartoes");
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }

    @Test
    public void criarAvisoDeViagemComDestinoInvalidoRetorna400() throws Exception {
        String requestBody = "{" +
                "\"destino\":\"\"," +
                "\"dataTermino\":\"01/06/2023\"" +
                "}";

        String idCartao = "1234-1234-1234-7777";

        URI uri = new URI("/viagens/" + idCartao + "/cartoes");
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void criarAvisoDeViagemComDataNoPassadoRetorna400() throws Exception {
        String requestBody = "{" +
                "\"destino\":\"Uberlândia\"," +
                "\"dataTermino\":\"01/06/2020\"" +
                "}";

        String idCartao = "1234-1234-1234-7777";

        URI uri = new URI("/viagens/" + idCartao + "/cartoes");
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void criarAvisoDeViagemComCartaoInvalidoRetorna400() throws Exception {
        String requestBody = "{" +
                "\"destino\":\"Uberlândia\"," +
                "\"dataTermino\":\"01/06/2021\"" +
                "}";

        String idCartao = "1234-1234-1234-0000&";

        URI uri = new URI("/viagens/" + idCartao + "/cartoes");
        mockMvc.perform(MockMvcRequestBuilders.put(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }


}
