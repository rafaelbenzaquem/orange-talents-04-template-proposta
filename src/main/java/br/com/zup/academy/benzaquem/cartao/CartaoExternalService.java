package br.com.zup.academy.benzaquem.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "${cartoes.url}", name = "cartoes")
public interface CartaoExternalService {

    @GetMapping(consumes = "application/json")
    IdCartaoResponse recuperarDadosCartao(@PathVariable Long idProposta);

}
