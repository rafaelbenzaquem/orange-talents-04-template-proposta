package br.com.zup.academy.benzaquem.carteira;

import br.com.zup.academy.benzaquem.cartao.Cartao;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class SamsungPay extends Carteira {

    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public SamsungPay(){}

    public SamsungPay(Long id, String email, Cartao cartao) {
        super(id, email, TipoCarteira.SAMSUNG_PAY);
        this.cartao = cartao;
    }
}
