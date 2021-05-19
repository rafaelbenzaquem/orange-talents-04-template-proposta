package br.com.zup.academy.benzaquem.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${cartoes.url}", name = "cartoes")
public interface CartaoExternalService {

    @GetMapping(value = "/{idProposta}", consumes = "application/json")
    IdCartaoResponse recuperarDadosCartao(@PathVariable Long idProposta);

    @PostMapping(value = "/{idCartao}/bloqueios", consumes = "application/json")
    BloquearResponse bloquearCartao(@PathVariable String idCartao, @RequestBody BloquearRequest request);

}
