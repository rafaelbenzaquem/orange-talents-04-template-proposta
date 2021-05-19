package br.com.zup.academy.benzaquem.cartao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BloquearResponse {

    private String resultado;

    public BloquearResponse(@JsonProperty(value = "resultado") String resultado) {
        this.resultado = resultado;
    }

    public String getResultado() {
        return resultado;
    }

    @Override
    public String toString() {
        return "BloquearResponse{" +
                "resultado='" + resultado + '\'' +
                '}';
    }
}
