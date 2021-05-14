package br.com.zup.academy.benzaquem.cartao;

import br.com.zup.academy.benzaquem.proposta.Proposta;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdCartaoResponse {

    private String id;

    public IdCartaoResponse(@JsonProperty(value = "id") String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public Cartao toModel(Proposta proposta) {
        return new Cartao(this.id, proposta);
    }

}
