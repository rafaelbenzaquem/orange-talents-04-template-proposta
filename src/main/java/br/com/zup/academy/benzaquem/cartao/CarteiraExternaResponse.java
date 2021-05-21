package br.com.zup.academy.benzaquem.cartao;

public class CarteiraExternaResponse {

    private String resultado;
    private String id;

    public CarteiraExternaResponse(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
