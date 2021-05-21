package br.com.zup.academy.benzaquem.viagem;

import br.com.zup.academy.benzaquem.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ViagemRequest {

    @NotBlank(message = "O campo 'destino' não pode ser vazio ou nulo")
    private String destino;

    @NotNull(message = "O campo 'dataTermino' não pode ser nulo")
    @Future(message = "O campo 'dataTermino' deve ser posterior a data atual")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dataTermino;

    public ViagemRequest(@JsonProperty(value = "destino") String destino,@JsonProperty(value = "dataTermino")  LocalDate dataTermino) {
        this.destino = destino;
        this.dataTermino = dataTermino;
    }

    public Viagem toModel(String ipCliente, String userAgent, Cartao cartao) {
        return new Viagem(null, this.destino, this.dataTermino, ipCliente, userAgent, cartao);
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }
}
