package br.com.zup.academy.benzaquem.carteira;

public enum TipoCarteira {
    PAYPAL("PAYPAL");
    private String value;

    private TipoCarteira(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TipoCarteira of(String value) {
        if (value.equalsIgnoreCase("PAYPAL"))
            return TipoCarteira.PAYPAL;
        throw new IllegalArgumentException();
    }

}
