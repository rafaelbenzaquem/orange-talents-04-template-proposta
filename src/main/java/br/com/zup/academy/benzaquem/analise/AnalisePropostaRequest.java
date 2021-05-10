package br.com.zup.academy.benzaquem.analise;

import br.com.zup.academy.benzaquem.proposta.Proposta;

public class AnalisePropostaRequest {

    private String documento;
    private String nome;
    private String idProposta;

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
}
