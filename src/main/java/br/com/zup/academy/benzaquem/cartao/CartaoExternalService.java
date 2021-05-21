package br.com.zup.academy.benzaquem.cartao;

import br.com.zup.academy.benzaquem.aviso.AvisoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(url = "${cartoes.url}", name = "cartoes")
public interface CartaoExternalService {

    @GetMapping(consumes = "application/json")
    IdCartaoResponse recuperarDadosCartao(@RequestParam Long idProposta);

    @PostMapping(value = "/{idCartao}/bloqueios", consumes = "application/json")
    BloquearResponse bloquearCartao(@PathVariable String idCartao, @RequestBody BloquearRequest request);

    @PostMapping(value = "/{idCartao}/avisos", consumes = "application/json")
    IdCartaoResponse avisarViagem(@PathVariable String idCartao, @RequestBody @Valid AvisoLegadoRequest request);

    @PostMapping(value = "/{idCartao}/carteiras", consumes = "application/json")
    CarteiraExternaResponse associarCarteira(@PathVariable String idCartao, CarteiraExternaRequest request);

}
