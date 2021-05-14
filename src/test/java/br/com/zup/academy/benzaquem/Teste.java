package br.com.zup.academy.benzaquem;


import org.junit.jupiter.api.Test;

import java.util.Base64;

public class Teste {

    @Test
    public void teste(){
        String originalInput = "minha digital";
        System.out.println(originalInput);
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
        System.out.println(encodedString);

        System.out.println(new String(Base64.getDecoder().decode(encodedString)));
    }

}
