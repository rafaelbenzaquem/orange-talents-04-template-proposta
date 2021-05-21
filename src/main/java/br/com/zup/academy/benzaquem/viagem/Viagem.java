package br.com.zup.academy.benzaquem.viagem;

import br.com.zup.academy.benzaquem.cartao.Cartao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String destino;
    private LocalDate dataTermino;
    private String ipCliente;
    private String userAgent;
    @CreationTimestamp
    private Timestamp instante;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Deprecated
    public Viagem() {
    }

    public Viagem(Long id, String destino, LocalDate dataTermino, String ipCliente, String userAgent, Cartao cartao) {
        this.id = id;
        this.destino = destino;
        this.dataTermino = dataTermino;
        this.ipCliente = ipCliente;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }
}
