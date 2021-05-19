package br.com.zup.academy.benzaquem.analise;

import br.com.zup.academy.benzaquem.proposta.Proposta;

import java.util.Objects;

public class AnalisePropostaRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public AnalisePropostaRequest(String documento, String nome, Long idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta.toString();
    }

    public AnalisePropostaRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId() == null ? null : proposta.getId().toString();
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnalisePropostaRequest that = (AnalisePropostaRequest) o;
        return Objects.equals(documento, that.documento) && Objects.equals(nome, that.nome) && Objects.equals(idProposta, that.idProposta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento, nome, idProposta);
    }
}
