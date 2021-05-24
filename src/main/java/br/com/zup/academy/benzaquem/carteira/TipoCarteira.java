package br.com.zup.academy.benzaquem.carteira;

public enum TipoCarteira {
    PAYPAL("PAYPAL"),
    SAMSUNG_PAY("SAMSUNG_PAY");
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
        else if (value.equalsIgnoreCase("SAMSUNG_PAY"))
            return TipoCarteira.SAMSUNG_PAY;
        throw new IllegalArgumentException();
    }

}
