package br.com.zup.academy.benzaquem.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${solicitacao.url}", name = "avaliacao-financeira")
public interface AnaliseFinanceiraExternalService {

    @PostMapping(consumes = "application/json")
    AnalisePropostaResponse solicitarAnaliseExternaViaHttp(AnalisePropostaRequest request);

}
