package br.com.zup.academy.benzaquem.carteira;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaypalRepository extends JpaRepository<Paypal,Long> {

    Boolean existsPaypalByCartao_Id(String idCartao);
}
