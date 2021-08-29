package dev.rafaelcmr.vendas.controllers;

import dev.rafaelcmr.vendas.models.Cliente;
import dev.rafaelcmr.vendas.repositories.ClienteRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable Integer id)
    {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<Cliente>( cliente.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getCLientes()
    {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Cliente> postCliente(@RequestBody Cliente cliente)
    {
        return new ResponseEntity<>(clienteRepository.save(cliente), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer id)
    {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.delete(cliente.get());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> putCliente(@PathVariable Integer id, @RequestBody Cliente cliente)
    {
          return clienteRepository.findById(id)
                .map(c -> {
                    cliente.setId(c.getId());
                    clienteRepository.save(cliente);
                    return new ResponseEntity<Void>(HttpStatus.OK);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }
}
