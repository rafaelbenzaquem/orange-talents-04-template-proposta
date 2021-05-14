package br.com.zup.academy.benzaquem.proposta;

import java.math.BigDecimal;

public class DetalhesPropostaResponse {
    private Long id;
    private String nome;
    private String email;
    private String documento;
    private String endereco;
    private BigDecimal salario;
    private EstadoProposta estado;
    private String idCartao;

    public DetalhesPropostaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.nome = proposta.getNome();
        this.email = proposta.getEmail();
        this.documento = proposta.getDocumento();
        this.endereco = proposta.getEndereco();
        this.salario = proposta.getSalario();
        this.estado = proposta.getEstado();
        this.idCartao = proposta.getCartao() == null ? null : proposta.getCartao().getId();
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

    public String getIdCartao() {
        return idCartao;//== null ? null : idCartao.substring(0, 4) + "-****-****-****";
    }
}
