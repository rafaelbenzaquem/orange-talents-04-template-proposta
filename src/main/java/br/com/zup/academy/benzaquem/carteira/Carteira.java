package br.com.zup.academy.benzaquem.carteira;

import br.com.zup.academy.benzaquem.cartao.Cartao;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private TipoCarteira tipoCarteira;

    @Deprecated
    public Carteira() {
    }

    public Carteira(Long id, String email, TipoCarteira tipoCarteira) {
        this.id = id;
        this.email = email;
        this.tipoCarteira = tipoCarteira;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public TipoCarteira getTipoCarteira() {
        return tipoCarteira;
    }

}
