package br.com.zup.academy.benzaquem.carteira;

import br.com.zup.academy.benzaquem.cartao.*;
import feign.FeignException;
import org.junit.jupiter.api.*;
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

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarteirasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartaoRepository cartaoRepository;

    @MockBean
    private CartaoExternalService cartaoExternalService;

    @BeforeEach
    public void setup() {
        cartaoRepository.save(new Cartao("1234-1234-1234-4949", null));
        cartaoRepository.save(new Cartao("1234-1234-1234-0503", null));
    }


    @Test
    @Order(1)
    public void cadastrarCarteiraPaypalSucessoRetorna201() throws Exception {
        String requestBody = "{" +
                "\"email\":\"rafael.neto@zup.com.br\"" +
                "}";
        String idCartao = "1234-1234-1234-4949";
        URI uri = new URI("/carteiras/paypal/" + idCartao + "/cartoes");

        Mockito.when(cartaoExternalService.associarCarteira(idCartao,
                new CarteiraExternaRequest("rafael.neto@zup.com.br", "PAYPAL")))
                .thenReturn(new CarteiraExternaResponse("ASSOCIADA", "1234-1234-1234-4949"));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/carteiras/paypal/1"));

    }

    @Test
    @Order(2)
    public void cadastrarCarteiraPaypalCartaoJaCadastradoRetorna422() throws Exception {
        String requestBody = "{" +
                "\"email\":\"rafael.neto@zup.com.br\"" +
                "}";
        String idCartao = "1234-1234-1234-4949";
        URI uri = new URI("/carteiras/paypal/" + idCartao + "/cartoes");

        Mockito.when(cartaoExternalService.associarCarteira(idCartao,
                new CarteiraExternaRequest("rafael.neto@zup.com.br", "PAYPAL")))
                .thenReturn(new CarteiraExternaResponse("ASSOCIADA", "1234-1234-1234-4949"));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));

    }

    @Test
    public void cadastrarCarteiraPaypalComServicoExternoIndisponivelRetorna503() throws Exception {
        String requestBody = "{" +
                "\"email\":\"rafael.neto@zup.com.br\"" +
                "}";
        String idCartao = "1234-1234-1234-0503";
        URI uri = new URI("/carteiras/paypal/" + idCartao + "/cartoes");

        Mockito.when(cartaoExternalService.associarCarteira(idCartao,
                new CarteiraExternaRequest("rafael.neto@zup.com.br", "PAYPAL")))
                .thenThrow(FeignException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.SERVICE_UNAVAILABLE.value()));

    }

    @Test
    public void cadastrarCarteiraPaypalComEmailBlankRetorna400() throws Exception {
        String requestBody = "{" +
                "\"email\":\" \"" +
                "}";
        String idCartao = "1234-1234-1234-4949";
        URI uri = new URI("/carteiras/paypal/" + idCartao + "/cartoes");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

    }

    @Test
    public void cadastrarCarteiraPaypalComEmailInvalidoRetorna400() throws Exception {
        String requestBody = "{" +
                "\"email\":\"rafael...asdasd\"" +
                "}";
        String idCartao = "1234-1234-1234-4949";
        URI uri = new URI("/carteiras/paypal/" + idCartao + "/cartoes");

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));

    }
}
