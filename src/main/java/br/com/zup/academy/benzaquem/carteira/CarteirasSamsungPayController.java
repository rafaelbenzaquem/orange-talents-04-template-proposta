package br.com.zup.academy.benzaquem.carteira;

import br.com.zup.academy.benzaquem.cartao.Cartao;
import br.com.zup.academy.benzaquem.cartao.CartaoExternalService;
import br.com.zup.academy.benzaquem.cartao.CartaoRepository;
import br.com.zup.academy.benzaquem.cartao.CarteiraExternaRequest;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static br.com.zup.academy.benzaquem.shared.util.OfuscadorUtil.ofuscarCartao;
import static br.com.zup.academy.benzaquem.shared.util.RegexUtil.isNotIdCartaoValido;

@RestController
@RequestMapping("/carteiras/samsungpay")
public class CarteirasSamsungPayController {


    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private CarteiraRepository carteiraRepository;

    @Autowired
    private CartaoExternalService cartaoExternalService;

    private Logger logger = LoggerFactory.getLogger(CarteirasSamsungPayController.class);

    @PostMapping("/{idCartao}/cartoes")
    public ResponseEntity<?> createCarteira(@PathVariable String idCartao,
                                            @RequestBody @Valid CarteiraRequest request) {

        if (idCartao == null || idCartao.isEmpty() || isNotIdCartaoValido(idCartao)) {
            logger.warn("Cartão com ID = " + idCartao + " é inválido!");
            return ResponseEntity.badRequest().build();
        }

        Optional<Cartao> optCartao = cartaoRepository.findById(idCartao);
        if (optCartao.isPresent()) {
            Cartao cartao = optCartao.get();

            Carteira carteira = request.toModel(TipoCarteira.SAMSUNG_PAY, cartao);
            try {
                cartaoExternalService.associarCarteira(idCartao,
                        new CarteiraExternaRequest(request.getEmail(),
                                TipoCarteira.SAMSUNG_PAY.getValue()));
                carteira = carteiraRepository.save(carteira);

                URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/carteiras/samsungpay/{id}").buildAndExpand(carteira.getId()).toUri();
                return ResponseEntity.created(uri).build();
            } catch (FeignException ex) {
                return ResponseEntity.status(503).build();
            }
        } else {
            logger.warn("Cartão com ID = " + ofuscarCartao(idCartao) + " não foi encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão com ID = " + idCartao + " não foi encontrado!");
        }
    }

}
