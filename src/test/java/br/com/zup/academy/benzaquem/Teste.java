package br.com.zup.academy.benzaquem;


import org.junit.jupiter.api.Test;

import java.util.Base64;

public class Teste {

    @Test
    public void teste(){
        String teste = "1234-1234-1234";
        System.out.println(teste.matches("[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}"));
    }

}
