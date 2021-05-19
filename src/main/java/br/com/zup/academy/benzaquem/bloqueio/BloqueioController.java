package br.com.zup.academy.benzaquem.bloqueio;

import br.com.zup.academy.benzaquem.cartao.*;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

import static br.com.zup.academy.benzaquem.shared.util.RegexUtil.*;
import static br.com.zup.academy.benzaquem.shared.util.OfuscadorUtil.*;

@RestController
@RequestMapping("/bloqueios")
public class BloqueioController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private CartaoExternalService cartaoExternalService;

    private Logger log = LoggerFactory.getLogger(BloqueioController.class);

    @PutMapping(path = "/{idCartao}/cartao")
    @Transactional
    public ResponseEntity<?> bloquearCartao(HttpServletRequest servletRequest, @PathVariable String idCartao) {
        if (idCartao == null || idCartao.isEmpty() || isNotIdCartaoValido(idCartao)) {
            log.warn("Cartão com ID = " + idCartao + " é inválido!");
            return ResponseEntity.badRequest().build();
        }
        Optional<Cartao> optCartao = cartaoRepository.findById(idCartao);
        if (optCartao.isPresent()) {
            Optional<Bloqueio> optBloqueio = bloqueioRepository.findByCartao_Id(idCartao);
            if (optBloqueio.isPresent()) {
                log.warn("Cartão com ID = " + ofuscarCartao(idCartao) + " é inválido!");
                return ResponseEntity.unprocessableEntity().body("O cartao de id:" + idCartao + " já está bloqueado!");
            }
            Cartao cartao = optCartao.get();
            try {
                BloquearResponse response = cartaoExternalService.bloquearCartao(idCartao, new BloquearRequest("benzaquem\\proposta"));
                bloqueioRepository.save(new Bloqueio(null,
                        servletRequest.getRemoteAddr(),
                        servletRequest.getHeader("user-agent"),
                        cartao));
                cartao.bloquear();

            } catch (FeignException ex) {
                log.warn("Não foi possível bloquear o cartao no sistema externo ", ex);
            }
            return ResponseEntity.ok().build();
        } else {
            log.warn("Cartão com ID = " + ofuscarCartao(idCartao) + " é inválido!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão com ID = " + idCartao + " não foi encontrado!");
        }
    }

}
