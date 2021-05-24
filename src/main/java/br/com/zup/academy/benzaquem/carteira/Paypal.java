package br.com.zup.academy.benzaquem.carteira;

import br.com.zup.academy.benzaquem.cartao.Cartao;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Paypal extends Carteira {

    @OneToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Deprecated
    public Paypal(){}

    public Paypal(Long id, String email, Cartao cartao) {
        super(id, email, TipoCarteira.PAYPAL);
        this.cartao = cartao;
    }
}
