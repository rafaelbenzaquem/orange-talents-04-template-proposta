package br.com.zup.academy.benzaquem.proposta;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class PropostaRepositoryTest {

    @Autowired
    private PropostaRepository propostaRepository;

    @Test
    public void buscarPropostaPorDocumento(){
        propostaRepository.save(new Proposta(null,"Andressa","andreca@email.com","92255765012","Tv Frei Ambrosio, N 925", new BigDecimal(1800)));
        Proposta proposta = propostaRepository.findByDocumento("92255765012").orElse(null);
        Assertions.assertNotNull(proposta);
    }

}
