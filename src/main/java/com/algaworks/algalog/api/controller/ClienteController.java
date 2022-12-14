package com.algaworks.algalog.api.controller;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;

    @GetMapping
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Cliente> buscar(@PathVariable Long clientId){
        Optional<Cliente> clienteOptional = clienteRepository.findById(clientId);
        if(clienteOptional.isPresent())
        {
            return ResponseEntity.ok().body(clienteOptional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente adicionar(@Valid @RequestBody Cliente cliente){
        return catalogoClienteService.salvar(cliente);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long clientId, @Valid @RequestBody Cliente cliente){
        if(!clienteRepository.existsById(clientId)){
            return ResponseEntity.notFound().build();
        }
        cliente.setId(clientId);
        cliente = catalogoClienteService.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deletar(@PathVariable Long clientId){
        if(!clienteRepository.existsById(clientId)){
            return ResponseEntity.notFound().build();
        }else{
            catalogoClienteService.excluir(clientId);
            return ResponseEntity.noContent().build();
        }
    }

}
