package br.com.zup.academy.benzaquem.cartao;

import br.com.zup.academy.benzaquem.proposta.Proposta;

public class SolicitanteCartaoRequest {

    private String documento;
    private String nome;
    private String idProposta;

    public SolicitanteCartaoRequest(Proposta proposta){
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
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
