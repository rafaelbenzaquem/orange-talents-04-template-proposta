package br.com.zup.academy.benzaquem.cartao;

import java.util.Objects;

public class CarteiraExternaRequest {

    private String email;
    private String carteira;

    public CarteiraExternaRequest(String email, String carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public String getCarteira() {
        return carteira;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarteiraExternaRequest that = (CarteiraExternaRequest) o;
        return Objects.equals(email, that.email) && Objects.equals(carteira, that.carteira);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, carteira);
    }
}
