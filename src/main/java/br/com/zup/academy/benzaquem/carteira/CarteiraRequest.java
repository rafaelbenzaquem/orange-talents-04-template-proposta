package br.com.zup.academy.benzaquem.carteira;

import br.com.zup.academy.benzaquem.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class CarteiraRequest {

    @NotBlank
    @Email
    private String email;

    public CarteiraRequest(@JsonProperty(value = "email") String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public Carteira toModel(TipoCarteira tipoCarteira, Cartao cartao) {
        if (TipoCarteira.PAYPAL.equals(tipoCarteira))
            return new Paypal(null, this.email, cartao);
        else if (TipoCarteira.SAMSUNG_PAY.equals(tipoCarteira))
            return new SamsungPay(null, this.email, cartao);
        return null;
    }
}
