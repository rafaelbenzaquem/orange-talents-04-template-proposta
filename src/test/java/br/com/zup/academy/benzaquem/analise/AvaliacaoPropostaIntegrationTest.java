package br.com.zup.academy.benzaquem.analise;

import br.com.zup.academy.benzaquem.proposta.EstadoProposta;
import br.com.zup.academy.benzaquem.proposta.Proposta;
import br.com.zup.academy.benzaquem.proposta.PropostaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
public class AvaliacaoPropostaIntegrationTest {

    @Autowired
    private PropostaRepository propostaRepository;

    private AnaliseFinanceiraExternalService analiseSimuladaSemRestricao;

    static class AnaliseFinaceiraSemRestricaoMock implements AnaliseFinanceiraExternalService {
        @Override
        public AnalisePropostaResponse solicitarAnaliseExternaViaHttp(AnalisePropostaRequest request) {
            return new AnalisePropostaResponse(request.getDocumento(), request.getNome(), EstadoAnalise.SEM_RESTRICAO);
        }
    }

    @BeforeEach
    public void setup() {
        analiseSimuladaSemRestricao = new AnaliseFinaceiraSemRestricaoMock();
    }


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

        System.out.println(proposta.getId());
        AnalisePropostaResponse avaliacaoResponse = analiseSimuladaSemRestricao.solicitarAnaliseExternaViaHttp(new AnalisePropostaRequest(proposta));
        Assertions.assertNotNull(avaliacaoResponse);
        proposta.atualizarAposAnalise(avaliacaoResponse);
        proposta = propostaRepository.findById(proposta.getId()).orElse(null);
        Assertions.assertNotNull(proposta);
        Assertions.assertNotSame(proposta.getEstado(), EstadoProposta.NAO_VERIFICADO);
    }

}
