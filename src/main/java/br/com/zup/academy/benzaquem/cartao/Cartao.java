package br.com.zup.academy.benzaquem.cartao;

import br.com.zup.academy.benzaquem.biometria.Biometria;
import br.com.zup.academy.benzaquem.bloqueio.Bloqueio;
import br.com.zup.academy.benzaquem.proposta.Proposta;
import br.com.zup.academy.benzaquem.aviso.Aviso;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartao {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private EstadoCartao estadoCartao = EstadoCartao.DESBLOQUEADO;

    @OneToOne
    @JoinColumn(name = "proposta_id")
    private Proposta proposta;

    @OneToOne
    @JoinColumn(name = "bloqueio_id")
    private Bloqueio bloqueio;

    @OneToMany(mappedBy = "cartao")
    private List<Biometria> biometrias = new ArrayList<>();

    @OneToMany(mappedBy = "cartao")
    private List<Aviso> avisos = new ArrayList<>();

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id, Proposta proposta) {
        this.id = id;
        this.proposta = proposta;
    }

    public Bloqueio getBloqueio() {
        return bloqueio;
    }

    public void setBloqueio(Bloqueio bloqueio) {
        this.bloqueio = bloqueio;
    }

    public List<Biometria> getBiometrias() {
        return biometrias;
    }

    public Boolean addBiometria(Biometria biometria) {
       return this.biometrias.add(biometria);
    }

    public List<Aviso> getAvisos() {
        return avisos;
    }

    public Boolean addAviso(Aviso viagem){
        return this.avisos.add(viagem);
    }

    public String getId() {
        return id;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public EstadoCartao getEstadoCartao() {
        return estadoCartao;
    }

    public void bloquear() {
        this.estadoCartao = EstadoCartao.BLOQUEADO;
    }

    public void desbloquear() {
        this.estadoCartao = EstadoCartao.DESBLOQUEADO;
    }
}
