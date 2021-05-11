package br.com.zup.academy.benzaquem.cartao;

import br.com.zup.academy.benzaquem.proposta.EstadoProposta;
import br.com.zup.academy.benzaquem.proposta.Proposta;
import br.com.zup.academy.benzaquem.proposta.PropostaRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscaPeriodicaDoCartao {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoExternalService cartaoExternalService;

    private Logger logger = LoggerFactory.getLogger(BuscaPeriodicaDoCartao.class);


    @Scheduled(fixedDelay = 2000)
    public void requisitarCartoesDasPropostaasElegiveis() {
        List<Proposta> propostasElegiveisSemCartao = propostaRepository.findAllByEstadoAndIdCartaoIsNull(EstadoProposta.ELEGIVEL);
        propostasElegiveisSemCartao.forEach(proposta -> {
            try {
                IdCartaoResponse idCartaoResponse = cartaoExternalService.recuperarDadosCartao(new SolicitanteCartaoRequest(proposta));
                proposta.associarIdCartao(idCartaoResponse);
                propostaRepository.save(proposta);
                logger.info("Cartão " + idCartaoResponse.getId().substring(0, 4) + "-****-****-**** foi associado a proposta " + proposta.getId());
            } catch (FeignException ex) {
                logger.warn("Pedido de cartão não foi processado, aguardar a proxima interação!", ex);
            }
        });

    }
}
