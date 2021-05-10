package br.com.zup.academy.benzaquem.proposta;

import br.com.zup.academy.benzaquem.analise.AnalisePropostaResponse;
import br.com.zup.academy.benzaquem.analise.EstadoAnalise;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String documento;
    private String endereco;
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private EstadoProposta estado;


    @Deprecated
    public Proposta() {
    }

    public Proposta(Long id, String nome, String email, String documento, String endereco, BigDecimal salario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EstadoProposta getEstado() {
        return estado;
    }

    public void atualizarAposAnalise(AnalisePropostaResponse analiseResponse) {
        if (analiseResponse.getDocumento().equals(documento))
            this.estado = analiseResponse.getResultadoSolicitacao().equals(EstadoAnalise.SEM_RESTRICAO) ? EstadoProposta.ELEGIVEL : EstadoProposta.NAO_ELEGIVEL;

    }
}
