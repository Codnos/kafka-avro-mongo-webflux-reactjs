package com.codnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.resources;

@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }


    @Bean
    public RouterFunction<ServerResponse> routingFunction() {
        return resources("/webjars/**", new ClassPathResource("classpath:/META-INF/resources/webjars/"))
                .and(resources("/**", new ClassPathResource("public/")));
    }
}
