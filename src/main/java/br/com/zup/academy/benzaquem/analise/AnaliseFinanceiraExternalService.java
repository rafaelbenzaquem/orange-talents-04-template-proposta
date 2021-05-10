package br.com.zup.academy.benzaquem.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "http://localhost:9999/api/solicitacao", name = "avaliacao-financeira")
public interface AnaliseFinanceiraExternalService {

    @PostMapping(consumes = "application/json")
    AnalisePropostaResponse solicitarAnaliseExternaViaHttp(AnalisePropostaRequest request);

}
