package br.com.zup.academy.benzaquem.carteira;

import br.com.zup.academy.benzaquem.cartao.*;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class CarteirasSamsungPayControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartaoRepository cartaoRepository;

    @MockBean
    private CartaoExternalService cartaoExternalService;

    @BeforeEach
    public void setup() {
        cartaoRepository.save(new Cartao("1234-1234-0000-4949", null));
        cartaoRepository.save(new Cartao("1234-1234-0000-0503", null));
    }

    @Test
    public void cadastrarCarteiraSamsungPaySucessoRetorna201() throws Exception {
        String requestBody = "{" +
                "\"email\":\"rafael.neto@zup.com.br\"" +
                "}";
        String idCartao = "1234-1234-0000-4949";
        URI uri = new URI("/carteiras/samsungpay/" + idCartao + "/cartoes");

        Mockito.when(cartaoExternalService.associarCarteira(idCartao,
                new CarteiraExternaRequest("rafael.neto@zup.com.br", "SAMSUNG_PAY")))
                .thenReturn(new CarteiraExternaResponse("ASSOCIADA", "1234-1234-0000-4949"));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/carteiras/samsungpay/1"));

    }

    @Test
    public void cadastrarCarteiraSamsungPayComServicoExternoIndisponivelRetorna503() throws Exception {
        String requestBody = "{" +
                "\"email\":\"rafael.neto@zup.com.br\"" +
                "}";
        String idCartao = "1234-1234-0000-0503";
        URI uri = new URI("/carteiras/samsungpay/" + idCartao + "/cartoes");

        Mockito.when(cartaoExternalService.associarCarteira(idCartao,
                new CarteiraExternaRequest("rafael.neto@zup.com.br", "SAMSUNG_PAY")))
                .thenThrow(FeignException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.SERVICE_UNAVAILABLE.value()));

    }

    @Test
    public void cadastrarCarteiraSamsungPayComEmailBlankRetorna400() throws Exception {
        String requestBody = "{" +
                "\"email\":\" \"" +
                "}";
        String idCartao = "1234-1234-0000-4949";
        URI uri = new URI("/carteiras/samsungpay/" + idCartao + "/cartoes");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void cadastrarCarteiraSamsungPayComEmailInvalidoRetorna400() throws Exception {
        String requestBody = "{" +
                "\"email\":\"rafael...asdasd\"" +
                "}";
        String idCartao = "1234-1234-0000-4949";
        URI uri = new URI("/carteiras/samsungpay/" + idCartao + "/cartoes");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

    }
}
