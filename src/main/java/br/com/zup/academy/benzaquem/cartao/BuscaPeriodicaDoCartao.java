package br.com.zup.academy.benzaquem.cartao;

import br.com.zup.academy.benzaquem.proposta.EstadoProposta;
import br.com.zup.academy.benzaquem.proposta.Proposta;
import br.com.zup.academy.benzaquem.proposta.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("default")
public class BuscaPeriodicaDoCartao {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoExternalService cartaoExternalService;

    private final Logger logger = LoggerFactory.getLogger(BuscaPeriodicaDoCartao.class);


    @Scheduled(fixedDelay = 2000)
    public void requisitarCartoesDasPropostaasElegiveis() {
        List<Proposta> propostasElegiveisSemCartao = propostaRepository.findAllByEstadoAndCartao_Id(EstadoProposta.ELEGIVEL, null);
        propostasElegiveisSemCartao.forEach(proposta -> {
            var idCartaoResponse = cartaoExternalService.recuperarDadosCartao(proposta.getId());
            var cartao = idCartaoResponse.toModel(proposta);
            cartaoRepository.save(cartao);

            proposta.associarCartao(cartao);
            propostaRepository.save(proposta);
            logger.info(new StringBuilder("Cartão ").append(idCartaoResponse.getId(), 0, 4).append("-****-****-**** foi associado a proposta ").append(proposta.getId()).toString());

        });

    }
}
