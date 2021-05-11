package br.com.zup.academy.benzaquem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableFeignClients
@EnableAsync
@EnableScheduling
public class PropostaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropostaApplication.class, args);
    }

}
