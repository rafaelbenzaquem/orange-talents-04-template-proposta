package br.com.zup.academy.benzaquem.bloqueio;

import br.com.zup.academy.benzaquem.cartao.Cartao;

import javax.persistence.*;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ipCliente;
    private String userAgent;

    @OneToOne
    @JoinColumn(name="cartao_id")
    private Cartao cartao;

    @Deprecated
    public Bloqueio() {
    }

    public Bloqueio(Long id, String ipCliente, String userAgent, Cartao cartao) {
        this.id = id;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getIpCliente() {
        return ipCliente;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public Cartao getCartao() {
        return cartao;
    }
}
