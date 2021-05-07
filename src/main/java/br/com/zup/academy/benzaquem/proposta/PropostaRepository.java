package br.com.zup.academy.benzaquem.proposta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta,Long> {
    Optional<Proposta> findByDocumento(String documento);
}
