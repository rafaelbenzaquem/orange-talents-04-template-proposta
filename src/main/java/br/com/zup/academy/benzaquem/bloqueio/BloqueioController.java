package br.com.zup.academy.benzaquem.bloqueio;

import br.com.zup.academy.benzaquem.cartao.Cartao;
import br.com.zup.academy.benzaquem.cartao.CartaoRepository;
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

@RestController
@RequestMapping("/bloqueios")
public class BloqueioController {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @PutMapping(path = "/{idCartao}/cartao")
    @Transactional
    public ResponseEntity<?> bloquearCartao(HttpServletRequest servletRequest, @PathVariable String idCartao) {
        if (idCartao == null || idCartao.isEmpty() || isNotIdCartaoValido(idCartao))
            return ResponseEntity.badRequest().build();
        Optional<Cartao> optCartao = cartaoRepository.findById(idCartao);
        if (optCartao.isPresent()) {
            Optional<Bloqueio> optBloqueio = bloqueioRepository.findByCartao_Id(idCartao);
            if (optBloqueio.isPresent())
                return ResponseEntity.unprocessableEntity().body("O cartao de id:" + idCartao + " já está bloqueado!");
            bloqueioRepository.save(new Bloqueio(null,
                    servletRequest.getRemoteAddr(),
                    servletRequest.getHeader("user-agent"),
                    optCartao.get()));
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão com ID = " + idCartao + " não foi encontrado!");
        }
    }

    private Boolean isNotIdCartaoValido(String idCartao) {
        return !idCartao.matches("[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}");
    }

}
