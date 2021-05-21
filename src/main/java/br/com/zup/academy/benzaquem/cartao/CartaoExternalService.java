package br.com.zup.academy.benzaquem.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(url = "${cartoes.url}", name = "cartoes")
public interface CartaoExternalService {

    @GetMapping( consumes = "application/json")
    IdCartaoResponse recuperarDadosCartao(@RequestParam Long idProposta);

    @PostMapping(value = "/{idCartao}/bloqueios", consumes = "application/json")
    BloquearResponse bloquearCartao(@PathVariable String idCartao, @RequestBody BloquearRequest request);

}
