package br.com.zup.academy.benzaquem.analise;

import br.com.zup.academy.benzaquem.proposta.EstadoProposta;
import br.com.zup.academy.benzaquem.proposta.Proposta;
import br.com.zup.academy.benzaquem.proposta.PropostaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
public class AvaliacaoPropostaIntegrationTest {

    @Autowired
    private PropostaRepository propostaRepository;

    @MockBean
    private AnaliseFinanceiraExternalService analiseSimuladaSemRestricao;



    @Test
    @Transactional
    public void processarPropostaComSolicitacaoDeAvaliacaoFinanceira() {

        Proposta proposta = propostaRepository.save(new Proposta
                (null,
                        "Andressa Saraiva",
                        "andreca2@email.com",
                        "92255765012",
                        "Tv Frei Ambrosio, N 925",
                        new BigDecimal(1800), null));
        Mockito.when(analiseSimuladaSemRestricao.solicitarAnaliseExternaViaHttp(new AnalisePropostaRequest(proposta)))
                .thenReturn(new AnalisePropostaResponse(proposta.getDocumento(), proposta.getNome(), EstadoAnalise.SEM_RESTRICAO));

        AnalisePropostaResponse avaliacaoResponse = analiseSimuladaSemRestricao.solicitarAnaliseExternaViaHttp(new AnalisePropostaRequest(proposta));
        Assertions.assertNotNull(avaliacaoResponse);
        proposta.atualizarAposAnalise(avaliacaoResponse);
        proposta = propostaRepository.findById(proposta.getId()).orElse(null);
        Assertions.assertNotNull(proposta);
        Assertions.assertNotSame(proposta.getEstado(), EstadoProposta.NAO_VERIFICADO);
    }

}
