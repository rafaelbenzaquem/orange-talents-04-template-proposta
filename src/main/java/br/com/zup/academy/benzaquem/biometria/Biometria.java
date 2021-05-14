package br.com.zup.academy.benzaquem.biometria;

import br.com.zup.academy.benzaquem.cartao.Cartao;

import javax.persistence.*;

@Entity
public class Biometria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String dadosBiometricosBase64;

    @ManyToOne
    private Cartao cartao;

    public Biometria(){}
    public Biometria(Long id, String dadosBiometricosBase64, Cartao cartao) {
        this.id = id;
        this.dadosBiometricosBase64 = dadosBiometricosBase64;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getDadosBiometricosBase64() {
        return dadosBiometricosBase64;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
