package br.com.zup.academy.benzaquem.shared.util;

public class OfuscadorUtil {

    public static String ofuscar(String arg){
        return arg.length()<=4?arg.charAt(0)+"***":arg.substring(0,3)+"****";
    }

    public static String ofuscarCartao(String idCartao){
        if(RegexUtil.isNotIdCartaoValido(idCartao))
            return idCartao;
        return idCartao.substring(0,4)+"-****-****-****";
    }

    public static String ofuscarCpf(String cpf){
        return cpf.substring(0,4)+".***.***-**";
    }

}
