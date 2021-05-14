package br.com.zup.academy.benzaquem.biometria;

import br.com.zup.academy.benzaquem.cartao.Cartao;
import br.com.zup.academy.benzaquem.cartao.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/biometrias")
public class BiometriasController {

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @PostMapping("/{idCartao}/cartao")
    @Transactional
    public ResponseEntity<?> cadastrarBiometria(@PathVariable String idCartao, @RequestBody String dadosBiometricosBase64) {
        Optional<Cartao> opt = cartaoRepository.findById(idCartao);
        if (dadosBiometricosBase64 == null || dadosBiometricosBase64.isBlank())
            return ResponseEntity.badRequest().body("Dados biometricos inválidos.");
        if (opt.isPresent()) {
            Cartao cartao = opt.get();
            Biometria biometria = biometriaRepository.save(new Biometria(null, dadosBiometricosBase64,cartao));
            URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                    .path("/{id}").buildAndExpand(biometria.getId()).toUri();
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cartão com ID = " + idCartao + ", não foi encontrado!");
        }
    }


}
