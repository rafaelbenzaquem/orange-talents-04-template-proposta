package br.com.zup.academy.benzaquem.proposta;

import br.com.zup.academy.benzaquem.shared.CampoUnico;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class NovaPropostaRequest {

    @NotBlank
    private String nome;
    @Email
    @NotBlank
    private String email;
    @CampoUnico(domainClass = Proposta.class,fieldName = "documento")
    @NotBlank
    @CpfOuCnpj
    private String documento;
    @NotBlank
    private String endereco;
    @NotNull
    @Positive
    private BigDecimal salario;

    public NovaPropostaRequest(String nome, String email, String documento, String endereco, BigDecimal salario) {
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Proposta toModel(){
        return new Proposta(null,this.nome,this.email,this.documento,this.endereco,this.salario);
    }
}
