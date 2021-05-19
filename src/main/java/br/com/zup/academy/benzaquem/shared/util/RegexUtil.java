package br.com.zup.academy.benzaquem.shared.util;

public class RegexUtil {

    public static Boolean isNotIdCartaoValido(String idCartao) {
        return !idCartao.matches("[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}");
    }
}
