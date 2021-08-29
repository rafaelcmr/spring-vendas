package dev.rafaelcmr.vendas;

import dev.rafaelcmr.vendas.models.Cliente;
import dev.rafaelcmr.vendas.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VendasApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
    @Bean
    public CommandLineRunner init(@Autowired ClienteRepository clienteRepository)
    {
        return args -> {
            clienteRepository.save(new Cliente(1, "Rafael"));
        };
    }
}
