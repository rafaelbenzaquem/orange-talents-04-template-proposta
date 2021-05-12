package br.com.zup.academy.benzaquem.proposta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/propostas")
public class PropostasReadController {

    @Autowired
    private PropostaRepository propostaRepository;

    Logger logger = LoggerFactory.getLogger(PropostasReadController.class);

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable long id) {
        Optional<Proposta> opt = propostaRepository.findById(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(new DetalhesPropostaResponse(opt.get()));
        }
        logger.warn("Proposta de id=" + id + " não foi encontrada!");
        return ResponseEntity.notFound().build();
    }

}
