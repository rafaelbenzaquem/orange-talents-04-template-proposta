package br.com.zup.academy.benzaquem.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:8888/api/cartoes", name = "cartoes")
public interface CartaoExternalService {

    @PostMapping(consumes = "application/json")
    IdCartaoResponse recuperarDadosCartao(SolicitanteCartaoRequest request);

}
