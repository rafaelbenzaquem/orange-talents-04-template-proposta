package br.com.zup.academy.benzaquem.analise;

import br.com.zup.academy.benzaquem.proposta.EstadoProposta;
import br.com.zup.academy.benzaquem.proposta.Proposta;
import br.com.zup.academy.benzaquem.proposta.PropostaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
public class AvaliacaoPropostaIntegrationTest {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private AnaliseFinanceiraExternalService externalService;

    @Test
    public void processarPropostaComSolicitacaoDeAvaliacaoFinanceira() {

        Proposta proposta = propostaRepository.save(new Proposta
                (null,
                        "Andressa Saraiva",
                        "andreca2@email.com",
                        "92255765012",
                        "Tv Frei Ambrosio, N 925",
                        new BigDecimal(1800)));
        AnalisePropostaResponse avaliacaoResponse = externalService.solicitarAnaliseExternaViaHttp(new AnalisePropostaRequest(proposta));

        Assertions.assertNotNull(avaliacaoResponse);

        proposta.atualizarAposAnalise(avaliacaoResponse);
        propostaRepository.save(proposta);
        proposta = propostaRepository.findById(proposta.getId()).orElse(null);
        Assertions.assertTrue(proposta.getEstado() != EstadoProposta.NAO_VERIFICADO);
    }

}
