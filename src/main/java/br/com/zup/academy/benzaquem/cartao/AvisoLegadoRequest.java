package br.com.zup.academy.benzaquem.cartao;

import br.com.zup.academy.benzaquem.aviso.Aviso;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class AvisoLegadoRequest {
    @NotBlank(message = "O campo 'destino' não pode ser vazio ou nulo")
    private String destino;

    @NotNull(message = "O campo 'dataTermino' não pode ser nulo")
    @Future(message = "O campo 'dataTermino' deve ser posterior a data atual")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate dataTermino;

    public AvisoLegadoRequest(@JsonProperty(value = "destino") String destino, @JsonProperty(value = "dataTermino")  LocalDate dataTermino) {
        this.destino = destino;
        this.dataTermino = dataTermino;
    }

    public Aviso toModel(String ipCliente, String userAgent, Cartao cartao) {
        return new Aviso(null, this.destino, this.dataTermino, ipCliente, userAgent, cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvisoLegadoRequest that = (AvisoLegadoRequest) o;
        return Objects.equals(destino, that.destino) && Objects.equals(dataTermino, that.dataTermino);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destino, dataTermino);
    }
}
