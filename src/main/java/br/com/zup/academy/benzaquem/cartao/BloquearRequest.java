package br.com.zup.academy.benzaquem.cartao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class BloquearRequest {

    private String sistemaResponsavel;

    public BloquearRequest(@JsonProperty(value = "sistemaResponsavel") String sistemaResponsavel) {
        this.sistemaResponsavel = sistemaResponsavel;
    }

    public String getSistemaResponsavel() {
        return sistemaResponsavel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BloquearRequest that = (BloquearRequest) o;
        return Objects.equals(sistemaResponsavel, that.sistemaResponsavel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sistemaResponsavel);
    }
}
