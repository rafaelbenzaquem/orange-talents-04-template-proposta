package br.com.zup.academy.benzaquem.aviso;

import br.com.zup.academy.benzaquem.cartao.AvisoLegadoRequest;
import br.com.zup.academy.benzaquem.cartao.Cartao;
import br.com.zup.academy.benzaquem.cartao.CartaoExternalService;
import br.com.zup.academy.benzaquem.cartao.CartaoRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

import static br.com.zup.academy.benzaquem.shared.util.OfuscadorUtil.ofuscarCartao;
import static br.com.zup.academy.benzaquem.shared.util.RegexUtil.isNotIdCartaoValido;

@RestController
@RequestMapping("/viagens")
public class AvisosController {

    @Autowired
    private AvisoRepository viagemRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CartaoExternalService cartaoExternalService;

    private Logger log = LoggerFactory.getLogger(AvisosController.class);

    @PutMapping("/{idCartao}/cartoes")
    public ResponseEntity<?> cadastrar(@PathVariable String idCartao,
                                       @RequestBody @Valid AvisoRequest viagemRequest,
                                       HttpServletRequest request) {
        if (idCartao == null || idCartao.isEmpty() || isNotIdCartaoValido(idCartao)) {
            log.warn("Cartão com ID = " + idCartao + " é inválido!");
            return ResponseEntity.badRequest().build();
        }
        Optional<Cartao> optCartao = cartaoRepository.findById(idCartao);
        if (optCartao.isPresent()) {
            Cartao cartao = optCartao.get();
            Aviso viagem = viagemRequest.toModel(request.getRemoteAddr(),
                    request.getHeader("user-agent"),
                    cartao);
            try {
                cartaoExternalService.avisarViagem(idCartao, new AvisoLegadoRequest(viagemRequest.getDestino(), viagemRequest.getDataTermino()));
                viagemRepository.save(viagem);
            } catch (FeignException ex) {

            }

            return ResponseEntity.ok().build();
        } else {
            log.warn("Cartão com ID = " + ofuscarCartao(idCartao) + " não foi encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão com ID = " + idCartao + " não foi encontrado!");
        }
    }

}
