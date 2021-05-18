package br.com.zup.academy.benzaquem.bloqueio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloqueioRepository extends JpaRepository<Bloqueio,Long> {
    Optional<Bloqueio> findByCartao_Id(String idCartao);
}
