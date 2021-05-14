package br.com.zup.academy.benzaquem.cartao;

import br.com.zup.academy.benzaquem.biometria.Biometria;
import br.com.zup.academy.benzaquem.proposta.Proposta;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class Cartao {
    @Id
    private String id;

    @OneToOne @JoinColumn(name = "proposta_id")
    private Proposta proposta;

    @OneToMany(mappedBy = "cartao")
    private List<Biometria> biometrias = new ArrayList<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id, Proposta proposta) {
        this.id = id;
        this.proposta = proposta;
    }

    public void addBiometria(Biometria biometria){
        this.biometrias.add(biometria);
    }

    public List<Biometria> getBiometrias() {
        return biometrias;
    }

    public String getId() {
        return id;
    }

    public Proposta getProposta() {
        return proposta;
    }
}
