package br.com.zup.academy.benzaquem.proposta;

import br.com.zup.academy.benzaquem.analise.AnalisePropostaResponse;
import br.com.zup.academy.benzaquem.analise.EstadoAnalise;
import br.com.zup.academy.benzaquem.cartao.Cartao;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String documento;
    private String endereco;
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private EstadoProposta estado;

    @OneToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;


    @Deprecated
    public Proposta() {
    }

    public Proposta(Long id, String nome, String email, String documento, String endereco, BigDecimal salario, Cartao cartao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.documento = documento;
        this.endereco = endereco;
        this.salario = salario;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public EstadoProposta getEstado() {
        return estado;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void atualizarAposAnalise(AnalisePropostaResponse analiseResponse) {
        if (analiseResponse.getDocumento().equals(documento))
            this.estado = analiseResponse.getResultadoSolicitacao().equals(EstadoAnalise.SEM_RESTRICAO) ? EstadoProposta.ELEGIVEL : EstadoProposta.NAO_ELEGIVEL;
    }

    public void associarCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public void encriptarDocumento(TextEncryptor textEncryptor) {
        this.documento = textEncryptor.encrypt(this.documento);
    }
}
