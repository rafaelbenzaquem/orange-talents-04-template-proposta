package br.com.zup.academy.benzaquem.analise;

public class AnalisePropostaResponse {

    private String documento;
    private String nome;
    private EstadoAnalise resultadoSolicitacao;

    public AnalisePropostaResponse(String documento, String nome, EstadoAnalise resultadoSolicitacao) {
        this.documento = documento;
        this.nome = nome;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public EstadoAnalise getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
