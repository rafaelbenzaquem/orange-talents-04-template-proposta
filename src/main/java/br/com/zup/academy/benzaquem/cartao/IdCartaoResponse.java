package br.com.zup.academy.benzaquem.cartao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdCartaoResponse {

    private String id;

    public IdCartaoResponse(@JsonProperty(value = "id") String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
