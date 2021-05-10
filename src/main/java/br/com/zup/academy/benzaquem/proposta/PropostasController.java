package br.com.zup.academy.benzaquem.proposta;

import br.com.zup.academy.benzaquem.analise.AnaliseFinanceiraExternalService;
import br.com.zup.academy.benzaquem.analise.AnalisePropostaRequest;
import br.com.zup.academy.benzaquem.analise.AnalisePropostaResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostasController {

    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private AnaliseFinanceiraExternalService analiseFinanceiraExternalService;

    Logger logger = LoggerFactory.getLogger(PropostasController.class);

    @PostMapping
    @Transactional
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid NovaPropostaRequest novaPropostaRequest) {
        Proposta proposta = novaPropostaRequest.toModel();
        try {
            AnalisePropostaResponse analisePropostaResponse = analiseFinanceiraExternalService.
                    solicitarAnaliseExternaViaHttp(new AnalisePropostaRequest(proposta));
            proposta.atualizarAposAnalise(analisePropostaResponse);
        } catch (FeignException ex) {
            logger.warn("Não foi possível completar o processamento do serviço externo de analise financeira", ex);
        }
        propostaRepository.save(proposta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}").buildAndExpand(proposta.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


}
