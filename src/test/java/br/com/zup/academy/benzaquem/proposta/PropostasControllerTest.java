package br.com.zup.academy.benzaquem.proposta;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PropostasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void propostaPessoaFisicaSalvaComSucessoRetorna201() throws Exception {
        String requestBody = "{" +
                "\"documento\":\"99014013051\"," +
                "\"nome\":\"Rafael Benzaquem neto\"," +
                "\"email\":\"rafael.neto@zup.com.br\"," +
                "\"endereco\":\"Travessa frei Ambrósio , n° 925, casa B\"," +
                "\"salario\":2500.00" +
                "}";

        System.out.println(requestBody);

        URI uri = new URI("/propostas");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.header()
                        .string("Location", "http://localhost/propostas/1"));
    }

    @Test
    @Order(2)
    public void propostaPessoaJuridicaSalvaComSucessoRetorna201() throws Exception {
        String requestBody = "{" +
                "\"documento\":\"69445807000134\"," +
                "\"nome\":\"Flynow\"," +
                "\"email\":\"admin@flynow.com\"," +
                "\"endereco\":\"Travessa frei Ambrósio , n° 925, casa B\"," +
                "\"salario\":100000.00" +
                "}";

        URI uri = new URI("/propostas");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.header()
                        .string("Location", "http://localhost/propostas/2"));
    }

    @Test
    @Order(3)
    public void propostaComDocumentoDuplicadoRetorna422() throws Exception {
        String requestBody = "{" +
                "\"documento\":\"69445807000134\"," +
                "\"nome\":\"Flynow\"," +
                "\"email\":\"admin@flynow.com\"," +
                "\"endereco\":\"Travessa frei Ambrósio , n° 925, casa B\"," +
                "\"salario\":100000.00" +
                "}";

        URI uri = new URI("/propostas");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()));
    }

    @Test
    public void propostaComEmailInvalidoRetorna400() throws Exception {
        String requestBody = "{" +
                "\"documento\":\"38348296093\"," +
                "\"nome\":\"Rafael Benzaquem neto\"," +
                "\"email\":\"laçskdjaçsldfj\"," +
                "\"endereco\":\"Travessa frei Ambrósio , n° 925, casa B\"," +
                "\"salario\":2500.00" +
                "}";

        URI uri = new URI("/propostas");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void propostaComEmailNulloOuVazioRetorna400() throws Exception {
        String requestBody = "{" +
                "\"documento\":\"38348296093\"," +
                "\"nome\":\"Rafael Benzaquem neto\"," +
                "\"email\":\"\"," +
                "\"endereco\":\"Travessa frei Ambrósio , n° 925, casa B\"," +
                "\"salario\":2500.00" +
                "}";

        URI uri = new URI("/propostas");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void propostaComSalarioNegativoRetorna400() throws Exception {
        String requestBody = "{" +
                "\"documento\":\"38348296093\"," +
                "\"nome\":\"Rafael Benzaquem neto\"," +
                "\"email\":\"rafael.neto@outlook.com\"," +
                "\"endereco\":\"Travessa frei Ambrósio , n° 925, casa B\"," +
                "\"salario\":-2500.00" +
                "}";

        URI uri = new URI("/propostas");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void propostaComDocumentoInvalidoRetorna400() throws Exception {
        String requestBody = "{" +
                "\"documento\":\"12345678900\"," +
                "\"nome\":\"Flynow\"," +
                "\"email\":\"admin@flynow.com\"," +
                "\"endereco\":\"Travessa frei Ambrósio , n° 925, casa B\"," +
                "\"salario\":2500.00" +
                "}";

        URI uri = new URI("/propostas");
        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
    }

}
